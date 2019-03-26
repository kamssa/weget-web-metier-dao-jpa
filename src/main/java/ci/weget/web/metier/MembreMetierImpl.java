package ci.weget.web.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.VerificationTokenRepository;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.VerificationToken;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.PathImage;

@Service
public class MembreMetierImpl implements IMembreMetier {

	public static final String TOKEN_INVALID = "INVALID";
	public static final String TOKEN_EXPIRED = "EXPIRED";
	public static final String TOKEN_VALID = "VALID";
	
	
	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	
////////////chemin ou sera sauvegarder les photos
////////////////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;


	/////// creer un utilisateur sur firebase///////////////////////////////////////
	public UserRecord createUser(Personne personne) throws FirebaseAuthException {
		CreateRequest request = new CreateRequest().setEmail(personne.getLogin()).setPassword(personne.getPassword());

		com.google.firebase.auth.UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

		System.out.println("utilisateur creer avec success: " + userRecord.getUid());
		System.out.println(
				"utilisateur creer avec success: " + FirebaseAuth.getInstance().getUserAsync(userRecord.getUid()));

		return userRecord;

	}

//  mettre a jour un utilisateur sur firebase
	@Override
	public UserRecord updateUser(Personne personne) throws Exception {

		UpdateRequest request = new UpdateRequest(personne.getLogin()).setEmail(personne.getLogin())
				.setPassword(personne.getPassword());
		com.google.firebase.auth.UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);

		return userRecord;
	}

	// creer un utilisateur dans la base
	@Override
	public Personne creer(Personne p) throws InvalideTogetException {
		if ((p.getLogin().equals(null)) || (p.getLogin() == "")) {
			throw new InvalideTogetException("Le login ne peut etre null");
		}
		if (!p.getPassword().equals(p.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		}

		Optional<Personne> pers = null;

		pers = personnesRepository.findByLogin(p.getLogin());
		if (pers.isPresent()) {
			throw new InvalideTogetException("ce login est deja utilise");
		}

		p.setCode(System.currentTimeMillis());
		p.setPassword(passwordEncoder.encode(p.getPassword()));
		p.setRepassword(passwordEncoder.encode(p.getRepassword()));
		return personnesRepository.save(p);
	}

// modifier un utilisateur dans la base
	@Override
	public Personne modifier(Personne modif) throws InvalideTogetException {

		Optional<Personne> p = personnesRepository.findById(modif.getId());
		if (p.isPresent()) {

			if (p.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		modif.setPassword(p.get().getPassword());
		modif.setRepassword(p.get().getRepassword());
		modif.setActived(true);
		return personnesRepository.save(modif);
	}

	// reset le password si on l'a oublie
	@Override
	public Personne modifierPassword(String login, String password, String repassword) throws InvalideTogetException {
		Personne personne = null;
		Optional<Personne> pers1 = personnesRepository.findByLogin(login);
		Personne p = pers1.get();
		if (pers1.isPresent()) {
			if (p.isActived() == true) {
				p.setPassword(passwordEncoder.encode(password));
				p.setRepassword(passwordEncoder.encode(repassword));
				new UpdateRequest(p.getLogin()).setEmail(p.getLogin()).setPassword(password);
			}

			personne = personnesRepository.save(p);
		}

		return personne;
	}

	// recupere un utlisateur a partir de son login
	@Override
	public Personne findByLogin(String login) throws InvalideTogetException {
		Personne p = null;
		p = personnesRepository.findByLogin(login).get();

		if (p.isActived() == false) {
			throw new RuntimeException("Vous n'etes pas active");
		}
		return p;
	}

// supprimer un utilisateur dans la base et sur firebase
	@Override
	public boolean supprimer(Long id) {
		Personne p = personnesRepository.findById(id).get();
		UserRecord userRecord = null;

		try {
			userRecord = FirebaseAuth.getInstance().getUserByEmailAsync(p.getLogin()).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		try {
			FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		personnesRepository.deleteById(id);

		return true;
	}

// supprimer une liste d'utilisateur
	@Override
	public boolean supprimer(List<Personne> entites) {

		personnesRepository.deleteAll(entites);
		return true;
	}

	// verifier si un utilisateur existe
	@Override
	public boolean existe(Long id) {

		return personnesRepository.existsById(id);
	}
	// ramener un membre a partir de sa cle de verification

	@Override
	public Membre getMembre(String verificationToken) {
		Membre membre = tokenRepository.findByToken(verificationToken).getMembre();
		if (membre.isActived() == false) {
			throw new RuntimeException("vous etes desactive");
		}
		return membre;
	}

// creer une cle de verification pour un membre
	@Override
	public void createVerificationToken(Membre membre, String token) {
		VerificationToken myToken = new VerificationToken(token, membre);
		tokenRepository.save(myToken);

	}

	// verifier la date d'expiration de la cle du membre
	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);

		LocalDateTime dateActuelle = LocalDateTime.now();

		if (dateActuelle.isAfter(verificationToken.getExpiryDate())) {
			return TOKEN_EXPIRED;
		} else {

			Membre user = verificationToken.getMembre();

			// user.setEnabled(true);

			personnesRepository.save(user);

		}
		return TOKEN_VALID;
	}

// generer une nouvelle cle pour un membre
	@Override
	public VerificationToken generateNewVerificationToken(String existingVerificationToken) {

		VerificationToken vToken = null;
		vToken = tokenRepository.findByToken(existingVerificationToken);
		tokenRepository.deleteById(vToken.getId());
		return vToken;
	}

// recupere une cle par id du membre
	@Override
	public VerificationToken getVericationTokenParMembre(Long id) {

		return tokenRepository.rechercherByMembre(id);
	}

// ramener les membre par type et actifs
	@Override
	public List<Personne> findAll() {
		List<Personne> pers = personnesRepository.findAll();
		List<Personne> personne = pers.stream().filter(p -> p.getType().equals("ME")).collect(Collectors.toList());
		return personne;

	}
	// modifier la date d'expiration de la cle

	@Override
	public VerificationToken modifDateExpire(Long id) {
		final VerificationToken verificationToken = tokenRepository.findByTokenById(id);
		verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
		return tokenRepository.save(verificationToken);
	}
	// creer un abonne

	
// modifier le password du compte
	@Override
	public Personne changerPasswordCompte(Long id, String password, String nouveauPassword, String confirmePassword)
			throws InvalideTogetException {
		com.google.firebase.auth.UserRecord userRecord = null;
		Personne personne = personnesRepository.findById(id).get();

		boolean b = matches(id, password);
		System.out.println(" retour des mots de passe comparrer" + b);
		if (b == true) {
			if (!nouveauPassword.equals(confirmePassword)) {
				throw new RuntimeException("Votre mot de passe ne correspond pas à la confirmation");
			}
			personne.setPassword(passwordEncoder.encode(nouveauPassword));
			personne.setRepassword(passwordEncoder.encode(confirmePassword));

		} else {
			throw new RuntimeException("Vous n'êtes pas autorisé!");
		}
		Personne pers = personnesRepository.save(personne);
		try {
			userRecord = FirebaseAuth.getInstance().getUserByEmailAsync(pers.getLogin()).get();
			System.out
					.println("recuperer userRecord***************" + userRecord.getUid() + "/" + userRecord.getEmail());

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		UpdateRequest request = new UpdateRequest(userRecord.getUid()).setPassword(nouveauPassword);
		try {
			userRecord = FirebaseAuth.getInstance().updateUser(request);
		} catch (FirebaseAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successfully updated user: " + userRecord.getUid());

		return pers;
	}

// verifier les passwords encode 
	@Override
	public boolean matches(Long id, String oldPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Personne personne = personnesRepository.findById(id).get();
		return encoder.matches(oldPassword, personne.getPassword());
	}

// mis a jour du membre  sur firebase 
	@Override
	public UserRecord updateUser(String login, String password) throws Exception {
		UpdateRequest request = new UpdateRequest(login).setEmail(login).setPassword(password);
		com.google.firebase.auth.UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);

		return userRecord;
	}

	@Override
	public Personne findById(Long id) {
		return personnesRepository.findById(id).get();
	}

	@Override
	public Boolean existsByLogin(String login) {
		personnesRepository.existsByLogin(login);
		return true;
	}

	@Override
	public Personne createImageMembre(MultipartFile file, Long id) throws IllegalStateException, IOException {
		Personne p = null;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		String libelleSEspace = libelle.trim();
		String libelleS = libelleSEspace.replaceAll("\\s", "");
		p = personnesRepository.findById(id).get();
		System.out.println(p);

		String path = PathImage.getLienRetourImageMembre() + p.getVersion() + "/"+id+"/" + libelleS;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageMembre() + "/"+id+"/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			p.setPathPhoto(path);
			System.out.println(path);
			file.transferTo(new File(dossier + libelleS));

		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}

		return p;

	}

	@Override
	public Personne createImageCouvertureMembre(MultipartFile file, Long id) throws IllegalStateException, IOException {
		// recuperer le libelle à partir du nom de la photo
		Personne p = null;
		String libelle = file.getOriginalFilename();
		String libelleSEspace = libelle.trim();
		String libelleS = libelleSEspace.replaceAll("\\s", "");
		p = personnesRepository.findById(id).get();
		System.out.println(p);

		String path = PathImage.getLienRetourImageCouvertureMembre() + p.getVersion() + "/" + id+ "/" + libelleS;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageCouverureMembre() +"/"+id+"/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			p.setPathPhotoCouveture(path);
			System.out.println(path);
			file.transferTo(new File(dossier + libelleS));
            personnesRepository.save(p);
		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
		return p;
	}

	@Override
	public byte[] getImageMembre(Long version, Long id,String libelle) throws FileNotFoundException, IOException {
		String dossier = togetImage + "/" + PathImage.getLienImageMembre() +"/"+id+"/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	@Override
	public byte[] getImageCouvertureMembre(Long version, Long id,String libelle) throws FileNotFoundException, IOException {
		String dossier = togetImage + "/" + PathImage.getLienImageCouverureMembre() +"/"+id+"/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	
}
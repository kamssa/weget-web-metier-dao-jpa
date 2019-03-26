package ci.weget.web.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.CvAbonnementRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.CvAbonnement;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modele.metier.CreerAbonne;
import ci.weget.web.modeles.PathImage;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@Service
public class AbonnementMetierImpl implements IAbonnementMetier {

	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private CreerAbonne creerAbonne;
	@Autowired
	private CvAbonnementRepository cvAbonnementRepository;
////////////chemin ou sera sauvegarder les photos
//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	@Override
	public Abonnement creer(Abonnement entity) throws InvalideTogetException {

		return abonnementRepository.save(entity);
	}

	@Override
	public Abonnement modifier(Abonnement modif) throws InvalideTogetException {
		Optional<Abonnement> ab = abonnementRepository.findById(modif.getId());

		if (ab.isPresent()) {

			if (ab.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		return abonnementRepository.save(modif);

	}
	@Override
	public boolean creerAbonne(Personne personne) throws InvalideTogetException {
		try {
			creerAbonne.creerUnAbonne(personne);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Abonnement updateVue(long idPersonne, long idEspace) throws InvalideTogetException {

		Abonnement db = abonnementRepository.findAbonnementByIdPersonneAndIdEspace(idPersonne, idEspace);
		Abonnement db1 = abonnementRepository.findById(db.getId()).get();
		int nombreVue = db1.getNombreVue();
		nombreVue++;
		db1.setNombreVue(nombreVue);

		return abonnementRepository.save(db1);
	}

	@Override
	public List<Abonnement> findAll() {

		List<Abonnement> abonneParBlock = null;
		List<Abonnement> abonnes = abonnementRepository.findAll();

		abonneParBlock = abonnes.stream().filter(p -> p.isActive() == true).collect(Collectors.toList());

		return abonneParBlock;

	}

	@Override
	public Abonnement findById(Long id) {

		return abonnementRepository.findById(id).get();

	}

	// la liste des personne par block
	@Override
	public List<Abonnement> getAbonnementByIdEspace(long id) {
		List<Abonnement> abonneParBlock = null;
		List<Abonnement> abonnes = abonnementRepository.findAbonnementByIdEspace(id);

		abonneParBlock = abonnes.stream().filter(p -> p.isActive() == true).collect(Collectors.toList());

		return abonneParBlock;

	}

	@Override
	public List<Abonnement> getAbonnementByIdPersonne(long id) {

		List<Abonnement> abonneParBlock = null;
		List<Abonnement> abonnes = abonnementRepository.findAbonnementByIdPersonne(id);

		abonneParBlock = abonnes.stream().filter(p -> p.isActive() == true).collect(Collectors.toList());

		return abonneParBlock;
	}

	@Override
	public Abonnement statutAbonnement(long id) {
		Abonnement abonne = null;
		return abonne;

	}

// recuperer un abonnemnt par le login de la personne
	@Override
	public List<Abonnement> getAbonnementByLogin(String login) {

		List<Abonnement> abonneParEspace = null;

		List<Abonnement> abonnes = abonnementRepository.findAbonnementByLogin(login);

		abonneParEspace = abonnes.stream().filter(p -> p.isActive() == true).collect(Collectors.toList());

		return abonneParEspace;
	}

	@Override
	public boolean supprimer(Long id) {

		abonnementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Abonnement> entites) {
		abonnementRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {

		return abonnementRepository.existsById(id);
	}

	@Override
	public List<Abonnement> getAbonnementByVille(String ville) {

		return abonnementRepository.findAbonnementByVille(ville);
	}

	@Override
	public List<Abonnement> abonnementSpecial() {
		List<Abonnement> abonneSpecial = null;
		List<Abonnement> abonnes = abonnementRepository.findAll();
		abonneSpecial = abonnes.stream().filter(p -> p.isAbonneSpecial() == true).filter(p -> p.isActive() == true)
				.filter(p -> p.getEspace().getTypeEspace().equals("competence")).limit(50).collect(Collectors.toList());

		return abonneSpecial;

	}

	@Override
	public List<Personne> uneOcurrenceAbonne() {
		List<Abonnement> abonnes = abonnementRepository.findAll();

		Personne p = null;
		List<Personne> personnes = Lists.newArrayList();
		for (Abonnement ab : abonnes) {

			Long id = ab.getIdPersone();
			p = personnesRepository.findById(id).get();

			personnes.add(p);

		}
		personnes = personnes.stream().distinct().collect(Collectors.toList());

		return personnes;
	}

	@Override
	public List<Abonnement> abonnementByMois(LocalDate mois) {

		return abonnementRepository.findAbonnementByMois(mois);
	}

	@Override
	public List<Abonnement> abonnementBani() {

		List<Abonnement> abonneParBlock = null;
		List<Abonnement> abonnes = abonnementRepository.findAll();

		abonneParBlock = abonnes.stream().filter(p -> p.isUtlisateurBanni() == true).filter(p -> p.isActive() == false)
				.collect(Collectors.toList());

		return abonneParBlock;
	}

	@Override
	public List<Abonnement> getAbonnementByParam(String libelle, String specialite, String ville, double latitude,
			double longitude, boolean proximite) {
		List<Abonnement> abs = abonnementRepository.findAll();
		List<Abonnement> abonneTrouve = null;

		double r = 5;
		double R = 6371;

		double maxLat = latitude + Math.toDegrees(r / R);
		double maxLongi = longitude + Math.toDegrees(Math.asin(r / R) + Math.cos(Math.toRadians(latitude)));
		double minLat = latitude - Math.toDegrees(r / R);
		double minLongi = longitude - Math.toDegrees(Math.asin(r / R) + Math.cos(Math.toRadians(latitude)));

		abonneTrouve = abs;
		if (proximite == false) {
			if (!ville.equals(null) && ville != "") {

				abonneTrouve = getAbonnementByVille(ville);
			}
		} else {
			// filtrer en fonction de la position (latitude, longitude)
			abonneTrouve = abonneTrouve.stream().filter(a -> a.getLatitude() >= minLat && a.getLatitude() <= maxLat)
					.filter(a -> a.getLongitude() >= minLongi && a.getLongitude() <= maxLongi)
					.collect(Collectors.toList());
		}
		if (!libelle.equals(null) && libelle != "") {
			// filtrer en fonction de libelle
			abonneTrouve = abonneTrouve.stream().filter(a -> a.getEspace().getLibelle().equals(libelle))
					.collect(Collectors.toList());
		}
		if (!specialite.equals(null) && specialite != "") {
			// filtrer en fonction de specialite
			CvAbonnement cvAb = cvAbonnementRepository.findCvAbonnementParSpecialite(specialite);
			Long idAb = cvAb.getIdAbonnement();
			abonneTrouve = abonneTrouve.stream().filter(abonne -> abonne.getId().equals(idAb))
					.collect(Collectors.toList());

		}

		return abonneTrouve;

	}

	@Override
	public Abonnement createImageAbonnement(MultipartFile file, Long id) throws IllegalStateException, IOException {
		Abonnement ab=null;
		String libelle = file.getOriginalFilename();
		String libelleSEspace = libelle.trim();
		String libelleS = libelleSEspace.replaceAll("\\s", "");
		 ab = abonnementRepository.findById(id).get();

		String path = PathImage.getLienRetourImageAbonnement() + ab.getVersion() + "/"+id+"/" + libelleS;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageAbonnement() + "/" +id;
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			ab.setPathPhoto(path);
			System.out.println(path);
			file.transferTo(new File(dossier + libelleS));
		    abonnementRepository.save(ab);

		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
           return ab;
	}

	@Override
	public byte[] getImageAbonnement(Long version, Long id, String libelle) throws FileNotFoundException, IOException {
		System.out.println(version);
		String dossier = togetImage + "/" + PathImage.getLienImageAbonnement() + "/" + id+"/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

}

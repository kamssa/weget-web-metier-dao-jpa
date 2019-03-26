package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class AdminMetierImpl implements IAdminMetier {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	private RoleRepository roleRepository;

	/*
	 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	 */
/////// creer un utilisateur sur firebase///////////////////////////////////////
	public com.google.firebase.auth.UserRecord createUser(Personne personne) throws FirebaseAuthException {
		CreateRequest request = new CreateRequest().setEmail(personne.getLogin()).setPassword(personne.getPassword());

		com.google.firebase.auth.UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

		System.out.println("utilisateur creer avec success: " + userRecord.getUid());
		System.out.println(
				"utilisateur creer avec success: " + FirebaseAuth.getInstance().getUserAsync(userRecord.getUid()));

		return userRecord;

	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Personne> findAllPersonnesParMc(String type, String mc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		personnesRepository.deleteById(id);
		return true;
	}

	@Override
	public Personne creer(Personne entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return personnesRepository.save(entity);
	}

	@Override
	public Personne modifier(Personne entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return personnesRepository.save(entity);
	}

	@Override
	public List<Personne> findAll() {
		// TODO Auto-generated method stub
		return personnesRepository.findAll();
	}

	@Override
	public Personne findById(Long id) {
		// TODO Auto-generated method stub
		return personnesRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(List<Personne> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Personne> findByAdminSup(Long id) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne updatePassword(Personne administrateur) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

}

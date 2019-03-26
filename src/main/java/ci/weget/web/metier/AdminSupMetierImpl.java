package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Adresse;
import ci.weget.web.entites.personne.AdminSup;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class AdminSupMetierImpl implements IAdminSupMetier{
@Autowired
private PersonnesRepository personnesRepository;
@Autowired
PasswordEncoder passwordEncoder;
@Override
public com.google.firebase.auth.UserRecord createUser(Personne personne) throws FirebaseAuthException {
	CreateRequest request = new CreateRequest().setEmail(personne.getLogin()).setPassword(personne.getPassword());

	com.google.firebase.auth.UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

	System.out.println("utilisateur creer avec success: " + userRecord.getUid());
	System.out.println(
			"utilisateur creer avec success: " + FirebaseAuth.getInstance().getUserAsync(userRecord.getUid()));

	return userRecord;

}

@Override
public Personne creer(Personne p) throws InvalideTogetException {
	

	Optional<Personne> pers = null;

	pers = personnesRepository.findByLogin(p.getLogin());
	if (pers.isPresent()) {
		throw new InvalideTogetException("ce login est deja utilise");
	}
	Adresse adresse = new Adresse();
	adresse.setLatitude(0);
	adresse.setLongitude(0);
	p.setAdresse(adresse);
	p.setCode(System.currentTimeMillis());
	p.setPassword(passwordEncoder.encode(p.getPassword()));
	p.setRepassword(passwordEncoder.encode(p.getRepassword()));
	
	 return personnesRepository.save(p);
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
public boolean supprimer(Long id) {
	personnesRepository.deleteById(id);
	return true;
}

@Override
public boolean supprimer(List<Personne> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public Personne findAdminSupByLogin(String login){
	// TODO Auto-generated method stub
	return personnesRepository.findByLogin(login).get();
}
	
@Override
public Personne findByTypeAdmin(String type) {
	// TODO Auto-generated method stub
	return null;
}
	
}

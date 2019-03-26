package ci.weget.web.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.VerificationToken;
import ci.weget.web.exception.InvalideTogetException;

public interface IMembreMetier extends Imetier<Personne, Long> {

	public Personne findByLogin(String login) throws InvalideTogetException;
    Membre getMembre(String verificationToken);

	void createVerificationToken(Membre user, String token);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String token);

	VerificationToken modifDateExpire(Long id);


	public UserRecord createUser(Personne personne) throws Exception;

	public UserRecord updateUser(Personne personne) throws Exception;

	VerificationToken getVericationTokenParMembre(Long id);

	Personne modifierPassword(String login, String password, String repassword) throws InvalideTogetException;

	public Personne changerPasswordCompte(Long id, String password, String nouveauPassword, String confirmePassword)
			throws InvalideTogetException;

	public boolean matches(Long id, String encodedPassword);

	public UserRecord updateUser(String login, String password) throws Exception;
	Boolean existsByLogin(String login);
	Personne createImageMembre(MultipartFile file, Long id) throws IllegalStateException,IOException;
	Personne createImageCouvertureMembre(MultipartFile file, Long id) throws IllegalStateException,IOException;
	byte[] getImageMembre(Long version, Long id,String libelle) throws FileNotFoundException, IOException;
	byte[] getImageCouvertureMembre(Long version, Long id, String libelle) throws FileNotFoundException, IOException;
}

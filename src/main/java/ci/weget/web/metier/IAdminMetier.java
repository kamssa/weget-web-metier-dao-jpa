package ci.weget.web.metier;

import java.util.List;

import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.personne.Administrateur;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.exception.InvalideTogetException;


public interface IAdminMetier extends Imetier<Personne, Long> {

	
	public UserRecord createUser(Personne personne) throws Exception;
	List<Personne> findAllPersonnesParMc(String type, String mc);
    List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet);
    public List<Personne> findByAdminSup(Long id)throws InvalideTogetException;
    public Personne updatePassword(Personne administrateur) throws InvalideTogetException;
}

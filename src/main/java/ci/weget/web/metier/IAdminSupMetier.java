package ci.weget.web.metier;

import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.personne.AdminSup;
import ci.weget.web.entites.personne.Personne;

public interface IAdminSupMetier extends Imetier<Personne, Long> {
public Personne findByTypeAdmin(String type);
public Personne findAdminSupByLogin(String login);
public UserRecord createUser(Personne personne) throws Exception;

}

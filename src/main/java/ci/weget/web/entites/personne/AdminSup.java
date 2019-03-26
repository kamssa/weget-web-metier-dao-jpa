package ci.weget.web.entites.personne;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.Adresse;

@Entity
@Table(name = "T_AdminSup")
@DiscriminatorValue("SA")
public class AdminSup extends Personne {

	
	private static final long serialVersionUID = 1L;

	public AdminSup() {
		super();
	}

	

	public AdminSup(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
	}

	public AdminSup(String login, String password, String repassword, String type) {
		super(login, password, repassword, type);
	}

	@Override
	public String toString() {
		return "AdminSup []";
	}

}

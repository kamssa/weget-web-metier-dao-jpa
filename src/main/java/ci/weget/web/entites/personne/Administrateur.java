package ci.weget.web.entites;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Administration")
@DiscriminatorValue("AD")
public class Administrateur extends Personne {

	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Id_AdminSup")
    private Administrateur adminSup;
	public Administrateur() {
		super();
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresse adresse, List<Telephone> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		
	}

	public Administrateur(String login, String password, String type) {
		super(login, password, type);
		
	}

	public Administrateur(String login, String password) {
		super(login, password);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((adminSup == null) ? 0 : adminSup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrateur other = (Administrateur) obj;
		if (adminSup == null) {
			if (other.adminSup != null)
				return false;
		} else if (!adminSup.equals(other.adminSup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Administrateur [adminSup=" + adminSup + "]";
	}

	
}

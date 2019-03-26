package ci.weget.web.entites;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Membre")
@DiscriminatorValue("ME")
public class Membre extends Personne {

	
	private static final long serialVersionUID = 1L;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	
	public Membre() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto, String type,
			Adresse adresse, List<Telephone> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		// TODO Auto-generated constructor stub
	}
	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		// TODO Auto-generated constructor stub
	}
	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		// TODO Auto-generated constructor stub
	}
	public Membre(String nom, String prenom, String password, String repassword, String login) {
		super(nom, prenom, password, repassword, login);
		// TODO Auto-generated constructor stub
	}
	public Membre(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);
		// TODO Auto-generated constructor stub
	}
	public Membre(String password, String repassword, String login) {
		super(password, repassword, login);
		// TODO Auto-generated constructor stub
	}
	public Membre(String login, String password) {
		super(login, password);
		// TODO Auto-generated constructor stub
	}
	public Membre(String login) {
		super(login);
		// TODO Auto-generated constructor stub
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Membre other = (Membre) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Membre [description=" + description + "]";
	}
	
	
}

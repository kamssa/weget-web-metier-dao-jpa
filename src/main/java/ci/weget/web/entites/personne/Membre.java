package ci.weget.web.entites.personne;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.Adresse;

@Entity
@Table(name = "T_Membre")
@DiscriminatorValue("ME")
public class Membre extends Personne  {

	private static final long serialVersionUID = 1L;
	@Column(columnDefinition = "TEXT")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;
	
	
	public Membre() {
		super();
	}

	
	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
	}

	public Membre(String nom, String prenom, String password, String repassword, String login) {
		super(nom, prenom, password, repassword, login);
	}

	public Membre(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);
	}

	public Membre(String password, String repassword, String login) {
		super(password, repassword, login);
	}

	public Membre(String login, String password) {
		super(login, password);
	}

	public Membre(String login) {
		super(login);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
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

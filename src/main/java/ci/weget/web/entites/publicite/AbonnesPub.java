package ci.weget.web.entites.publicite;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.personne.Entreprise;
import ci.weget.web.entites.personne.Personne;

@Entity
@Table(name = "T_AbonnesPub")
public class AbonnesPub extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition="TEXT")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	
	@Column(name = "id_Entreprise", insertable = false, updatable = false)
	private long idEntreprise;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersone;
	
	
	
	
	public AbonnesPub() {
		super();
		
	}
	
	public AbonnesPub(String titre, String description, Entreprise entreprise, Personne personne, long idEntreprise,
			long idPersone) {
		super();
		this.titre = titre;
		this.description = description;
		this.entreprise = entreprise;
		this.personne = personne;
		this.idEntreprise = idEntreprise;
		this.idPersone = idPersone;
	}

	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
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
	public Personne getPersonne() {
		return personne;
	}
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	public long getIdEntreprise() {
		return idEntreprise;
	}
	public void setIdEntreprise(long idEntreprise) {
		this.idEntreprise = idEntreprise;
	}
	public long getIdPersone() {
		return idPersone;
	}
	public void setIdPersone(long idPersone) {
		this.idPersone = idPersone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((entreprise == null) ? 0 : entreprise.hashCode());
		result = prime * result + (int) (idEntreprise ^ (idEntreprise >>> 32));
		result = prime * result + (int) (idPersone ^ (idPersone >>> 32));
		result = prime * result + ((personne == null) ? 0 : personne.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		AbonnesPub other = (AbonnesPub) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (entreprise == null) {
			if (other.entreprise != null)
				return false;
		} else if (!entreprise.equals(other.entreprise))
			return false;
		if (idEntreprise != other.idEntreprise)
			return false;
		if (idPersone != other.idPersone)
			return false;
		if (personne == null) {
			if (other.personne != null)
				return false;
		} else if (!personne.equals(other.personne))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbonnesPub [titre=" + titre + ", description=" + description + ", entreprise=" + entreprise
				+ ", personne=" + personne + ", idEntreprise=" + idEntreprise + ", idPersone=" + idPersone + "]";
	}
	

}

package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_DetailAbonnement")
public class DetailAbonnement extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String nom;

	private String refDetailAbonnement;
	@Column(columnDefinition = "TEXT")
	private String presentation;
	@Column(columnDefinition = "TEXT")
	private String description;

	@ElementCollection
	private List<String> pathPhotoCouverture = new ArrayList<>();
	private String pathLogo;
	@Embedded
	private Adresse adresse;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Telephones")
	private List<Telephone> telephones = new ArrayList<>();
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Abonnement")
	private Abonnement abonnement;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Abonnement")
	private List<Chiffre> chiffre = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Abonnement")
	private List<Partenaire> partenaire = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Abonnement")
	private List<Temoignage> temoignage = new ArrayList<>();
	private Long idBlock;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_TypeEtablissement")
	private TypeEtablissement typeEtablissement;

	public DetailAbonnement() {
		super();

	}

	public TypeEtablissement getTypeEtablissement() {
		return typeEtablissement;
	}

	public void setTypeEtablissement(TypeEtablissement typeEtablissement) {
		this.typeEtablissement = typeEtablissement;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefDetailAbonnement() {
		return refDetailAbonnement;
	}

	public void setRefDetailAbonnement(String refDetailAbonnement) {
		this.refDetailAbonnement = refDetailAbonnement;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public String getPathLogo() {
		return pathLogo;
	}

	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}

	public List<Chiffre> getChiffre() {
		return chiffre;
	}

	public void setChiffre(List<Chiffre> chiffre) {
		this.chiffre = chiffre;
	}

	public List<Partenaire> getPartenaire() {
		return partenaire;
	}

	public void setPartenaire(List<Partenaire> partenaire) {
		this.partenaire = partenaire;
	}

	public List<Temoignage> getTemoignage() {
		return temoignage;
	}

	public void setTemoignage(List<Temoignage> temoignage) {
		this.temoignage = temoignage;
	}

	public Long getIdBlock() {
		return idBlock;
	}

	public void setIdBlock(Long idBlock) {
		this.idBlock = idBlock;
	}

	public List<String> getPathPhotoCouverture() {
		return pathPhotoCouverture;
	}

	public void setPathPhotoCouverture(List<String> pathPhotoCouverture) {
		this.pathPhotoCouverture = pathPhotoCouverture;
	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((abonnement == null) ? 0 : abonnement.hashCode());
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((chiffre == null) ? 0 : chiffre.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idBlock == null) ? 0 : idBlock.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((partenaire == null) ? 0 : partenaire.hashCode());
		result = prime * result + ((pathLogo == null) ? 0 : pathLogo.hashCode());
		result = prime * result + ((pathPhotoCouverture == null) ? 0 : pathPhotoCouverture.hashCode());
		result = prime * result + ((presentation == null) ? 0 : presentation.hashCode());
		result = prime * result + ((refDetailAbonnement == null) ? 0 : refDetailAbonnement.hashCode());
		result = prime * result + ((telephones == null) ? 0 : telephones.hashCode());
		result = prime * result + ((temoignage == null) ? 0 : temoignage.hashCode());
		result = prime * result + ((typeEtablissement == null) ? 0 : typeEtablissement.hashCode());
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
		DetailAbonnement other = (DetailAbonnement) obj;
		if (abonnement == null) {
			if (other.abonnement != null)
				return false;
		} else if (!abonnement.equals(other.abonnement))
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (chiffre == null) {
			if (other.chiffre != null)
				return false;
		} else if (!chiffre.equals(other.chiffre))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (idBlock == null) {
			if (other.idBlock != null)
				return false;
		} else if (!idBlock.equals(other.idBlock))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (partenaire == null) {
			if (other.partenaire != null)
				return false;
		} else if (!partenaire.equals(other.partenaire))
			return false;
		if (pathLogo == null) {
			if (other.pathLogo != null)
				return false;
		} else if (!pathLogo.equals(other.pathLogo))
			return false;
		if (pathPhotoCouverture == null) {
			if (other.pathPhotoCouverture != null)
				return false;
		} else if (!pathPhotoCouverture.equals(other.pathPhotoCouverture))
			return false;
		if (presentation == null) {
			if (other.presentation != null)
				return false;
		} else if (!presentation.equals(other.presentation))
			return false;
		if (refDetailAbonnement == null) {
			if (other.refDetailAbonnement != null)
				return false;
		} else if (!refDetailAbonnement.equals(other.refDetailAbonnement))
			return false;
		if (telephones == null) {
			if (other.telephones != null)
				return false;
		} else if (!telephones.equals(other.telephones))
			return false;
		if (temoignage == null) {
			if (other.temoignage != null)
				return false;
		} else if (!temoignage.equals(other.temoignage))
			return false;
		if (typeEtablissement == null) {
			if (other.typeEtablissement != null)
				return false;
		} else if (!typeEtablissement.equals(other.typeEtablissement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetailAbonnement [nom=" + nom + ", typeEtablissement=" + typeEtablissement + ", refDetailAbonnement="
				+ refDetailAbonnement + ", presentation=" + presentation + ", description=" + description
				+ ", pathPhotoCouverture=" + pathPhotoCouverture + ", pathLogo=" + pathLogo + ", adresse=" + adresse
				+ ", telephones=" + telephones + ", abonnement=" + abonnement + ", chiffre=" + chiffre + ", partenaire="
				+ partenaire + ", temoignage=" + temoignage + ", idBlock=" + idBlock + "]";
	}

}

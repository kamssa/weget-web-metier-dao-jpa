package ci.weget.web.entites.espace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_Tarif")
public class Tarif extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private double prix;
	private double prixSpecial;
	private int dureeTarif;
	private int dureeSpecial;
	private String typeDuree;
	private boolean free;
	@Column(columnDefinition="TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Espace")
	private Espace espace;

	@Column(name = "id_Espace", insertable = false, updatable = false)
	private long idEspace;
	
	public Tarif() {
		super();

	}

	
	public Tarif(String titre, double prix, double prixSpecial, int dureeTarif, int dureeSpecial, String typeDuree,
			boolean free, String description, Espace espace, long idEspace) {
		super();
		this.titre = titre;
		this.prix = prix;
		this.prixSpecial = prixSpecial;
		this.dureeTarif = dureeTarif;
		this.dureeSpecial = dureeSpecial;
		this.typeDuree = typeDuree;
		this.free = free;
		this.description = description;
		this.espace = espace;
		this.idEspace = idEspace;
	}


	public Espace getEspace() {
		return espace;
	}


	public void setEspace(Espace espace) {
		this.espace = espace;
	}


	public long getIdEspace() {
		return idEspace;
	}


	public double getPrixSpecial() {
		return prixSpecial;
	}

	public void setPrixSpecial(double prixSpecial) {
		this.prixSpecial = prixSpecial;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public int getDureeTarif() {
		return dureeTarif;
	}

	public void setDureeTarif(int dureeTarif) {
		this.dureeTarif = dureeTarif;
	}

	public String getTypeDuree() {
		return typeDuree;
	}

	public void setTypeDuree(String typeDuree) {
		this.typeDuree = typeDuree;
	}

	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	
	public int getDureeSpecial() {
		return dureeSpecial;
	}

	public void setDureeSpecial(int dureeSpecial) {
		this.dureeSpecial = dureeSpecial;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + dureeSpecial;
		result = prime * result + dureeTarif;
		result = prime * result + ((espace == null) ? 0 : espace.hashCode());
		result = prime * result + (free ? 1231 : 1237);
		result = prime * result + (int) (idEspace ^ (idEspace >>> 32));
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(prixSpecial);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		result = prime * result + ((typeDuree == null) ? 0 : typeDuree.hashCode());
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
		Tarif other = (Tarif) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dureeSpecial != other.dureeSpecial)
			return false;
		if (dureeTarif != other.dureeTarif)
			return false;
		if (espace == null) {
			if (other.espace != null)
				return false;
		} else if (!espace.equals(other.espace))
			return false;
		if (free != other.free)
			return false;
		if (idEspace != other.idEspace)
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
			return false;
		if (Double.doubleToLongBits(prixSpecial) != Double.doubleToLongBits(other.prixSpecial))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (typeDuree == null) {
			if (other.typeDuree != null)
				return false;
		} else if (!typeDuree.equals(other.typeDuree))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Tarif [titre=" + titre + ", prix=" + prix + ", prixSpecial=" + prixSpecial + ", dureeTarif="
				+ dureeTarif + ", dureeSpecial=" + dureeSpecial + ", typeDuree=" + typeDuree + ", free=" + free
				+ ", description=" + description + ", espace=" + espace + ", idEspace=" + idEspace + "]";
	}

}

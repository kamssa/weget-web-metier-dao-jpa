package ci.weget.web.entites.publicite;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.publicite.Publicite;

@Entity
@Table(name = "T_TarifPub")
public class TarifPub extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private double prix;
	private int dureeTarif;
	private String typeDuree;
	private boolean isFree;
	private boolean pubSpecial;
	private String dimension;
	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Publicite", insertable = false, updatable = false)
	private Publicite publicite;

	@Column(name = "id_Publicite")
	private long idPublicite;

	public TarifPub() {
		super();

	}

	public TarifPub(String titre, double prix, int dureeTarif, String typeDuree, boolean isFree, boolean pubSpecial,
			String description, Publicite publicite, long idPublicite) {
		super();
		this.titre = titre;
		this.prix = prix;
		this.dureeTarif = dureeTarif;
		this.typeDuree = typeDuree;
		this.isFree = isFree;
		this.pubSpecial = pubSpecial;
		this.description = description;
		this.publicite = publicite;
		this.idPublicite = idPublicite;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public boolean isPubSpecial() {
		return pubSpecial;
	}

	public void setPubSpecial(boolean pubSpecial) {
		this.pubSpecial = pubSpecial;
	}

	public Publicite getPublicite() {
		return publicite;
	}

	public void setPublicite(Publicite publicite) {
		this.publicite = publicite;
	}

	public long getIdPublicite() {
		return idPublicite;
	}

	public void setIdPublicite(long idPublicite) {
		this.idPublicite = idPublicite;
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
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + dureeTarif;
		result = prime * result + (int) (idPublicite ^ (idPublicite >>> 32));
		result = prime * result + (isFree ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (pubSpecial ? 1231 : 1237);
		result = prime * result + ((publicite == null) ? 0 : publicite.hashCode());
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
		TarifPub other = (TarifPub) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dureeTarif != other.dureeTarif)
			return false;
		if (idPublicite != other.idPublicite)
			return false;
		if (isFree != other.isFree)
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
			return false;
		if (pubSpecial != other.pubSpecial)
			return false;
		if (publicite == null) {
			if (other.publicite != null)
				return false;
		} else if (!publicite.equals(other.publicite))
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
		return "TarifPub [titre=" + titre + ", prix=" + prix + ", dureeTarif=" + dureeTarif + ", typeDuree=" + typeDuree
				+ ", isFree=" + isFree + ", pubSpecial=" + pubSpecial + ", description=" + description + ", publicite="
				+ publicite + ", idPublicite=" + idPublicite + "]";
	}

}

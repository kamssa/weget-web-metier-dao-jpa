package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.publicite.Publicite;

@Entity
@Table(name = "T_Tarif")
public class Tarif extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private double prix;
	private int dureeTarif;
	private String typeDuree;
	private boolean free;
	private boolean abonneSpecial;
	@Column(columnDefinition="TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Block", insertable = false, updatable = false)
	private Block block;

	@Column(name = "id_Block")
	private long idBlock;
	
	public Tarif() {
		super();

	}

	public Tarif(String titre, double prix, int dureeTarif, String typeDuree, String description, Block block,
			long idBlock) {
		super();
		this.titre = titre;
		this.prix = prix;
		this.dureeTarif = dureeTarif;
		this.typeDuree = typeDuree;
		this.description = description;
		this.block = block;
		this.idBlock = idBlock;
		
	}

	public boolean isAbonneSpecial() {
		return abonneSpecial;
	}

	public void setAbonneSpecial(boolean abonneSpecial) {
		this.abonneSpecial = abonneSpecial;
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

	public void setIdBlock(long idBlock) {
		this.idBlock = idBlock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public long getIdBlock() {
		return idBlock;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (abonneSpecial ? 1231 : 1237);
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + dureeTarif;
		result = prime * result + (free ? 1231 : 1237);
		result = prime * result + (int) (idBlock ^ (idBlock >>> 32));
		long temp;
		temp = Double.doubleToLongBits(prix);
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
		if (abonneSpecial != other.abonneSpecial)
			return false;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dureeTarif != other.dureeTarif)
			return false;
		if (free != other.free)
			return false;
		if (idBlock != other.idBlock)
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
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
		return "Tarif [titre=" + titre + ", prix=" + prix + ", dureeTarif=" + dureeTarif + ", typeDuree=" + typeDuree
				+ ", free=" + free + ", abonneSpecial=" + abonneSpecial + ", description=" + description + ", block="
				+ block + ", idBlock=" + idBlock + "]";
	}

	
}

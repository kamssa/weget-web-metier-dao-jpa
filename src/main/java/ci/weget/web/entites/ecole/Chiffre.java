package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Chiffre")
public class Chiffre extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	private String titre;
	private String chiffre;
	@Column(columnDefinition="TEXT")
	private String description;
	/*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;*/

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getChiffre() {
		return chiffre;
	}

	public void setChiffre(String chiffre) {
		this.chiffre = chiffre;
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
		result = prime * result + ((chiffre == null) ? 0 : chiffre.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Chiffre other = (Chiffre) obj;
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
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chiffre [titre=" + titre + ", chiffre=" + chiffre + ", description=" + description + "]";
	}

	/*public SousBlock getSousBlock() {
		return sousBlock;
	}

	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}*/

}

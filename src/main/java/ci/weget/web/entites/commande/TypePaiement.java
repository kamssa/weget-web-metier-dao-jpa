package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Type_Paiement")
public class TypePaiement extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	@Column(columnDefinition="TEXT")
	private String description;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Entreprise")
public class Entreprise extends AbstractEntity {

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
	public Entreprise() {
		super();
		
	}
	public Entreprise(String libelle) {
		super();
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	}

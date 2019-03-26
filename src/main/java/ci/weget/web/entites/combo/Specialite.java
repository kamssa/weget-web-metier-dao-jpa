package ci.weget.web.entites.combo;

import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_ComboSpecialite")
public class Specialite extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private String libelle;
	private String description;
	
	
	public Specialite() {
		super();
		
	}
	
	public Specialite(String libelle, String description) {
		super();
		this.libelle = libelle;
		this.description = description;
	}

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

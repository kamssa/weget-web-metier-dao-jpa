package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Langue")
public class LangueParle extends AbstractEntity {

	private static final long serialVersionUID = 1L;
    private String libelle;
    private String description;
    
	public LangueParle() {
		super();
		
	}
	
	public LangueParle(String libelle, String description) {
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

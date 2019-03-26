package ci.weget.web.entites.ecole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_TypeEtablissement")
public class TypeEtablissement extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String pathPhoto;

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

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

}

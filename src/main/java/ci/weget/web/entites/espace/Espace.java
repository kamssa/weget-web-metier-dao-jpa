package ci.weget.web.entites.espace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_Espace")
public class Espace extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String libelle;

	private String pathPhoto;

	@Column(columnDefinition="TEXT")
	private String description;
	private String typeEspace;
	private int position;

	

	public Espace() {
		super();
	}

	public Espace(String libelle, String pathPhoto) {
		super();
		this.libelle = libelle;
		this.pathPhoto = pathPhoto;
	}

	public Espace(String libelle) {
		super();
		this.libelle = libelle;
	}

	public Espace(String libelle, String pathPhoto, String description, String typeEspace, int position) {
		super();
		this.libelle = libelle;
		this.pathPhoto = pathPhoto;
		this.description = description;
		this.typeEspace = typeEspace;
		this.position = position;
	}

	public Espace(String libelle, String pathPhoto, String description) {
		super();
		this.libelle = libelle;
		this.pathPhoto = pathPhoto;
		this.description = description;
	}

	public Espace(String libelle, String typeEspace, int position) {
		super();
		this.libelle = libelle;
		this.typeEspace = typeEspace;
		this.position = position;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getTypeEspace() {
		return typeEspace;
	}

	public void setTypeEspace(String typeEspace) {
		this.typeEspace = typeEspace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((pathPhoto == null) ? 0 : pathPhoto.hashCode());
		result = prime * result + position;
		result = prime * result + ((typeEspace == null) ? 0 : typeEspace.hashCode());
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
		Espace other = (Espace) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (pathPhoto == null) {
			if (other.pathPhoto != null)
				return false;
		} else if (!pathPhoto.equals(other.pathPhoto))
			return false;
		if (position != other.position)
			return false;
		if (typeEspace == null) {
			if (other.typeEspace != null)
				return false;
		} else if (!typeEspace.equals(other.typeEspace))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Blocks [libelle=" + libelle + ", pathPhoto=" + pathPhoto + ", description=" + description + "]";
	}

}

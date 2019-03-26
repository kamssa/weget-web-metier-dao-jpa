package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_TypeEtablissement")
public class TypeEtablissement extends AbstractEntity{

private static final long serialVersionUID = 1L;
private String libelle;
@Column(columnDefinition="TEXT")
private String description;
private String phathphoto;



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

public String getPhathphoto() {
	return phathphoto;
}

public void setPhathphoto(String phathphoto) {
	this.phathphoto = phathphoto;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
	result = prime * result + ((phathphoto == null) ? 0 : phathphoto.hashCode());
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
	TypeEtablissement other = (TypeEtablissement) obj;
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
	if (phathphoto == null) {
		if (other.phathphoto != null)
			return false;
	} else if (!phathphoto.equals(other.phathphoto))
		return false;
	return true;
}

@Override
public String toString() {
	return "TypeEtablissement [libelle=" + libelle + ", description=" + description + ", phathphoto=" + phathphoto
			+ "]";
}


}

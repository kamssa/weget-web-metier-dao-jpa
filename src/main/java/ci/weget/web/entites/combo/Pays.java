package ci.weget.web.entites.combo;

import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_ComboPays")
public class Pays extends AbstractEntity {

	private static final long serialVersionUID = 1L;


private int code;
private String alpha2;
private String alpha3;
private String nom_fr_fr;
private String nom_en_gb;
public Pays() {
	super();
	// TODO Auto-generated constructor stub
}
public Pays(int code, String alpha2, String alpha3, String nom_fr_fr, String nom_en_gb) {
	super();
	this.code = code;
	this.alpha2 = alpha2;
	this.alpha3 = alpha3;
	this.nom_fr_fr = nom_fr_fr;
	this.nom_en_gb = nom_en_gb;
}
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getAlpha2() {
	return alpha2;
}
public void setAlpha2(String alpha2) {
	this.alpha2 = alpha2;
}
public String getAlpha3() {
	return alpha3;
}
public void setAlpha3(String alpha3) {
	this.alpha3 = alpha3;
}
public String getNom_fr_fr() {
	return nom_fr_fr;
}
public void setNom_fr_fr(String nom_fr_fr) {
	this.nom_fr_fr = nom_fr_fr;
}
public String getNom_en_gb() {
	return nom_en_gb;
}
public void setNom_en_gb(String nom_en_gb) {
	this.nom_en_gb = nom_en_gb;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((alpha2 == null) ? 0 : alpha2.hashCode());
	result = prime * result + ((alpha3 == null) ? 0 : alpha3.hashCode());
	result = prime * result + code;
	result = prime * result + ((nom_en_gb == null) ? 0 : nom_en_gb.hashCode());
	result = prime * result + ((nom_fr_fr == null) ? 0 : nom_fr_fr.hashCode());
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
	Pays other = (Pays) obj;
	if (alpha2 == null) {
		if (other.alpha2 != null)
			return false;
	} else if (!alpha2.equals(other.alpha2))
		return false;
	if (alpha3 == null) {
		if (other.alpha3 != null)
			return false;
	} else if (!alpha3.equals(other.alpha3))
		return false;
	if (code != other.code)
		return false;
	if (nom_en_gb == null) {
		if (other.nom_en_gb != null)
			return false;
	} else if (!nom_en_gb.equals(other.nom_en_gb))
		return false;
	if (nom_fr_fr == null) {
		if (other.nom_fr_fr != null)
			return false;
	} else if (!nom_fr_fr.equals(other.nom_fr_fr))
		return false;
	return true;
}
@Override
public String toString() {
	return "Pays [code=" + code + ", alpha2=" + alpha2 + ", alpha3=" + alpha3 + ", nom_fr_fr=" + nom_fr_fr
			+ ", nom_en_gb=" + nom_en_gb + "]";
}



}

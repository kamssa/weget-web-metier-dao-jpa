package ci.weget.web.entites.combo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_ComboQuartier")
public class Quartier extends AbstractEntity {

private static final long serialVersionUID = 1L;
private int code;
private String alpha2;
private String alpha3;
private String nom_fr_fr;
private String nom_en_gb;

@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
@JoinColumn(name = "id_Ville")
private Ville ville;

public Quartier() {
	super();
	
}

public Quartier(int code, String alpha2, String alpha3, String nom_fr_fr, String nom_en_gb, Ville ville) {
	super();
	this.code = code;
	this.alpha2 = alpha2;
	this.alpha3 = alpha3;
	this.nom_fr_fr = nom_fr_fr;
	this.nom_en_gb = nom_en_gb;
	this.ville = ville;
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

public Ville getVille() {
	return ville;
}

public void setVille(Ville ville) {
	this.ville = ville;
}

}

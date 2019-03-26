package ci.weget.web.entites.publicite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name="t_Position")
public class Position extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private int numero;
	private int prix;
	private int duree;
	private String typeDuree;
	private String nom;
	private String format;
	@Column(unique=true)
	private String libelle;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getTypeDuree() {
		return typeDuree;
	}
	public void setTypeDuree(String typeDuree) {
		this.typeDuree = typeDuree;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	

}

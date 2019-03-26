package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Expediteur")
public class Expediteur extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String entreprise;
	public Expediteur() {
		super();
		
	}
	public Expediteur(String nom, String prenom, String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
    
}

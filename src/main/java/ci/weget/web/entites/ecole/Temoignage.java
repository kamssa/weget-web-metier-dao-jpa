package ci.weget.web.entites.ecole;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_Temoignage")
public class Temoignage extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
    private String titre;
    @Column(columnDefinition="TEXT")
	private String contenu;
    private String auteur;
    private String PathPhoto;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Ecole")
	private Ecole ecole;
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getPathPhoto() {
		return PathPhoto;
	}
	public void setPathPhoto(String pathPhoto) {
		PathPhoto = pathPhoto;
	}
	public Ecole getEcole() {
		return ecole;
	}
	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}
	
    
}

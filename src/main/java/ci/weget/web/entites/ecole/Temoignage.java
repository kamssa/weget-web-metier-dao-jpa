package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Temoignage")
public class Temoignage extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
    private String titre;
    @Column(columnDefinition="TEXT")
	private String contenu;
    private String auteur;
    private String PathPhoto;
  /*  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;*/
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
	/*public SousBlock getSousBlock() {
		return sousBlock;
	}
	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}
    */
    
}

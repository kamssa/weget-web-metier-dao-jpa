package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Experience")
public class Experiences extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String postOccupe;
	private String entreprise;
	private String dateDebut;
	private String dateFin;
	@Column(columnDefinition="TEXT")
	private String tache;
	private String lieu;
	
	
	
	public String getPostOccupe() {
		return postOccupe;
	}
	public void setPostOccupe(String postOccupe) {
		this.postOccupe = postOccupe;
	}
	public String getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
	
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	public String getTache() {
		return tache;
	}
	public void setTache(String tache) {
		this.tache = tache;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	
	
}

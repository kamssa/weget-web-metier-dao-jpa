package ci.weget.web.entites.abonnement;

import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_CvAbonnement")
public class CvAbonnement extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private Long idCvPersonne ;
	private Long idAbonnement ;
	private String specialite;
	
	
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public Long getIdCvPersonne() {
		return idCvPersonne;
	}
	public void setIdCvPersonne(Long idCvPersonne) {
		this.idCvPersonne = idCvPersonne;
	}
	public Long getIdAbonnement() {
		return idAbonnement;
	}
	public void setIdAbonnement(Long idAbonnement) {
		this.idAbonnement = idAbonnement;
	}
	
	
	
}

package ci.weget.web.entites.abonnement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_CursusColaire")
public class CursusColaire extends AbstractEntity  {

	private static final long serialVersionUID = 1L;
    private String date;
    private String etablissement;
    private String diplome;
    private String formation;
    @Column(columnDefinition="TEXT")
	private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_cvPersonne")
    CvPersonne cvPersonne;
   
  public CursusColaire() {
		super();
		
	}
	
	public CursusColaire(String date, String etablissement, String diplome, String formation) {
		super();
		this.date = date;
		this.etablissement = etablissement;
		this.diplome = diplome;
		this.formation = formation;
	}

	public CvPersonne getCvPersonne() {
		return cvPersonne;
	}

	public void setCvPersonne(CvPersonne cvPersonne) {
		this.cvPersonne = cvPersonne;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getFormation() {
		return formation;
	}
	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}

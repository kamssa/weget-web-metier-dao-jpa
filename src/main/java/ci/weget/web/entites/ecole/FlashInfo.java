package ci.weget.web.entites.ecole;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_FlashInfo")
public class FlashInfo extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
	@Column(columnDefinition="TEXT")
	private String contenu;
    private boolean etat;
    private LocalDate date;
    
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Ecole")
	private Ecole ecole;
	public FlashInfo() {
		super();
		
	}
	public FlashInfo(String contenu, boolean etat, LocalDate date) {
		super();
		this.contenu = contenu;
		this.etat = etat;
		this.date = date;
	}
	
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
   
	@PrePersist
	public void setDate() {
		this.date = LocalDate.now();
	}
	public Ecole getDetailAbonnement() {
		return ecole;
	}
	public void setDetailAbonnement(Ecole detailAbonnement) {
		this.ecole = detailAbonnement;
	}
	
}

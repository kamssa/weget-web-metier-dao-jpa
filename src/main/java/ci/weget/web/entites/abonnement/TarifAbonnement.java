package ci.weget.web.entites.abonnement;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.espace.Tarif;

@Entity
@Table(name = "T_TarifAbonnement")
public class TarifAbonnement extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Abonnement")
	private Abonnement abonnement;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;
	public TarifAbonnement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TarifAbonnement(Abonnement abonnement, Tarif tarif) {
		super();
		this.abonnement = abonnement;
		this.tarif = tarif;
	}
	public Abonnement getAbonnement() {
		return abonnement;
	}
	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}
	public Tarif getTarif() {
		return tarif;
	}
	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}
	
	
	
	
	
}

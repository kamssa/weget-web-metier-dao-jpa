package ci.weget.web.entites.commande;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Personne;

@Entity
@Table(name = "T_Panier")
public class Panier extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private double quantite;
	private double montant;
	private boolean abonneSpecial;
	private double nbreJours;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Espace")
	private Espace espace;
	
	
	  @Column(name = "id_Personne",insertable=false,updatable=false) 
	  private long idPersonne;
	 
	public Panier() {
		super();

	}

	public Panier(Tarif tarif, Personne personne) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		
	}

	public Panier(Tarif tarif, Personne personne, LocalDateTime date, double quantite, double montant) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		
		this.quantite = quantite;
		this.montant = montant;
		this.date = date;
	}

	public Espace getEspace() {
		return espace;
	}

	public void setEspace(Espace espace) {
		this.espace = espace;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public long getIdPersonne() {
		return idPersonne;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public double getNbreJours() {
		return nbreJours;
	}

	public void setNbreJours(double nbreJours) {
		this.nbreJours = nbreJours;
	}

	public LocalDateTime getDate() {
		return date;
	}

	
	@PrePersist
	@PreUpdate
	public void setDate() {
		this.date = LocalDateTime.now();
	}

	

	public boolean isAbonneSpecial() {
		return abonneSpecial;
	}

	public void setAbonneSpecial(boolean abonneSpecial) {
		this.abonneSpecial = abonneSpecial;
	}

	
}

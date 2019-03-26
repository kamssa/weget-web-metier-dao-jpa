package ci.weget.web.entites.commande;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.personne.Personne;

@Entity
@Table(name = "T_LigneCommande")
public class LigneCommande extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private double quantite;
	private double montant;
	private boolean abonneSpecial;
	private double nbreJours;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Commande")
	Commande commande;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersonne;

	public LigneCommande() {
		super();

	}

	public long getIdPersonne() {
		return idPersonne;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@PostPersist
	@PostUpdate
	public void setDate() {
		this.date = LocalDateTime.now();
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public boolean isAbonneSpecial() {
		return abonneSpecial;
	}

	public void setAbonneSpecial(boolean abonneSpecial) {
		this.abonneSpecial = abonneSpecial;
	}

	public double getNbreJours() {
		return nbreJours;
	}

	public void setNbreJours(double nbreJours) {
		this.nbreJours = nbreJours;
	}

}

package ci.weget.web.entites;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "T_Panier")
public class Panier extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private double quantite;
	private double montant;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Block")
	private Block block;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	/*
	 * @Column(name = "id_Personne",insertable=false,updatable=false) private long
	 * idPersonne;
	 */

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;

	public Panier() {
		super();

	}

	public Panier(Block block, Tarif tarif, Personne personne) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		this.block = block;
	}

	public Panier(Block block, Tarif tarif, Personne personne, LocalDateTime date, double quantite, double montant) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		this.block = block;
		this.quantite = quantite;
		this.montant = montant;
		this.date = date;
	}

	/*
	 * public long getIdPersonne() { return idPersonne; }
	 */

	public Tarif getTarif() {
		return tarif;
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

	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
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

	public LocalDateTime getDate() {
		return date;
	}

	@PrePersist
	@PreUpdate
	public void setDate() {
		this.date = LocalDateTime.now();
	}

	

	@Override
	public String toString() {
		return "Panier [block=" + block + ", tarif=" + tarif + ", personne=" + personne + ", quantite=" + quantite
				+ ", total=" + montant + ", date=" + date + "]";
	}

}

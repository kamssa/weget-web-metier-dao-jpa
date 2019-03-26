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
@Table(name = "T_Commande")
public class Commande extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private boolean paye;
	private LocalDateTime date;
    private String motif;
	private double montant;
	private int numero;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Personne")
	private Personne personne;

	public Commande() {
		super();
	}

	public Commande(Boolean paye, String motif, Double montant) {
		super();
		this.paye = paye;
		this.motif = motif;
		this.montant = montant;

	}

	@PrePersist
	@PreUpdate
	public void setDate() {
		this.date = LocalDateTime.now();
	}

	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	

	public boolean isPaye() {
		return paye;
	}

	public void setPaye(boolean paye) {
		this.paye = paye;
	}

	
	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((motif == null) ? 0 : motif.hashCode());
		result = prime * result + (paye ? 1231 : 1237);
		result = prime * result + ((personne == null) ? 0 : personne.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(montant) != Double.doubleToLongBits(other.montant))
			return false;
		if (motif == null) {
			if (other.motif != null)
				return false;
		} else if (!motif.equals(other.motif))
			return false;
		if (paye != other.paye)
			return false;
		if (personne == null) {
			if (other.personne != null)
				return false;
		} else if (!personne.equals(other.personne))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Commande [paye=" + paye + ", date=" + date + ", motif=" + motif + ", montant=" + montant + ", personne="
				+ personne + "]";
	}

}

package ci.weget.web.modeles;

import java.time.LocalDateTime;

public class AjoutPanier {

	private LocalDateTime date;
	private Double quantite;
	private Double total;
	private long idMembre;
	private long idTarif;
	private boolean abonneSpecial;
	private double nbresJours;
	private long idbonnement;


	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public long getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(long idMembre) {
		this.idMembre = idMembre;
	}

	public long getIdTarif() {
		return idTarif;
	}

	public void setIdTarif(long idTarif) {
		this.idTarif = idTarif;
	}

	public boolean isAbonneSpecial() {
		return abonneSpecial;
	}

	public void setAbonneSpecial(boolean abonneSpecial) {
		this.abonneSpecial = abonneSpecial;
	}

	public double getNbresJours() {
		return nbresJours;
	}

	public void setNbresJours(double nbresJours) {
		this.nbresJours = nbresJours;
	}

	public long getIdbonnement() {
		return idbonnement;
	}

	public void setIdbonnement(long idbonnement) {
		this.idbonnement = idbonnement;
	}

}

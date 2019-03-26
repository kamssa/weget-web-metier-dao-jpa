package ci.weget.web.modeles;

import java.time.LocalDateTime;

public class ModifPanier {

	private Long id;

	private LocalDateTime date;
	private Double quantite;
	private Double total;
	private long idBlock;
	private long idMembre;
	private long idTarif;
	private double nbreJours;
	private boolean abonneSpecial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getIdBlock() {
		return idBlock;
	}

	public void setIdBlock(long idBlock) {
		this.idBlock = idBlock;
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

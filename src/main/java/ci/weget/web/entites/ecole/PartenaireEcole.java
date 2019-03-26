package ci.weget.web.entites.ecole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.abonnement.Abonnement;

@Entity
@Table(name = "T_Partenaire")
public class PartenaireEcole extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String raisonSocial;
	private String sieWebPatenaire;
	private String pathLogo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Ecole")
	private Ecole ecole;
	public String getRaisonSocial() {
		return raisonSocial;
	}

	public void setRaisonSocial(String raisonSocial) {
		this.raisonSocial = raisonSocial;
	}

	public String getSieWebPatenaire() {
		return sieWebPatenaire;
	}

	public void setSieWebPatenaire(String sieWebPatenaire) {
		this.sieWebPatenaire = sieWebPatenaire;
	}

	public String getPathLogo() {
		return pathLogo;
	}

	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}

	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	
}

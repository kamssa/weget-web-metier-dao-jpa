package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Partenaire")
public class Partenaire extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String raisonSocial;
	private String sieWebPatenaire;
	private String pathLogo;
	/*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;*/

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

	/*public SousBlock getSousBlock() {
		return sousBlock;
	}

	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}
*/
}

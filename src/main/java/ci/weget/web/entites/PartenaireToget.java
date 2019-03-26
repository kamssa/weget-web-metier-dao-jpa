package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_PartenaireToget")
public class PartenaireToget extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String libelle;
	private String description;
	private String siteInternet;
	private String pathLogo;
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSiteInternet() {
		return siteInternet;
	}
	public void setSiteInternet(String siteInternet) {
		this.siteInternet = siteInternet;
	}
	public String getPathLogo() {
		return pathLogo;
	}
	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}
	
	
	
}

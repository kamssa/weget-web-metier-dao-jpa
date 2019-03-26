package ci.weget.web.entites;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Adresse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String boitePostal;
	private String email;
	private String pays;
	private String ville;
	private String quartier;
	private String adresseGeographique;
	private String siteWeb;
	
	@Column(nullable=true)
	private double longitude;
	
	@Column(nullable=true)
	private double latitude;
	private LocalDate date;

	public Adresse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Adresse(String boitePostal, String email, String pays, String ville, String quartier,
			String adresseGeographique, String siteWeb, double longitude, double latitude, LocalDate date) {
		super();
		this.boitePostal = boitePostal;
		this.email = email;
		this.pays = pays;
		this.ville = ville;
		this.quartier = quartier;
		this.adresseGeographique = adresseGeographique;
		this.siteWeb = siteWeb;
		this.longitude = longitude;
		this.latitude = latitude;
		this.date = date;
	}

	public String getBoitePostal() {
		return boitePostal;
	}

	public void setBoitePostal(String boitePostal) {
		this.boitePostal = boitePostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getAdresseGeographique() {
		return adresseGeographique;
	}

	public void setAdresseGeographique(String adresseGeographique) {
		this.adresseGeographique = adresseGeographique;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude=longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude=latitude;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresseGeographique == null) ? 0 : adresseGeographique.hashCode());
		result = prime * result + ((boitePostal == null) ? 0 : boitePostal.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pays == null) ? 0 : pays.hashCode());
		result = prime * result + ((quartier == null) ? 0 : quartier.hashCode());
		result = prime * result + ((siteWeb == null) ? 0 : siteWeb.hashCode());
		result = prime * result + ((ville == null) ? 0 : ville.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		if (adresseGeographique == null) {
			if (other.adresseGeographique != null)
				return false;
		} else if (!adresseGeographique.equals(other.adresseGeographique))
			return false;
		if (boitePostal == null) {
			if (other.boitePostal != null)
				return false;
		} else if (!boitePostal.equals(other.boitePostal))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (pays == null) {
			if (other.pays != null)
				return false;
		} else if (!pays.equals(other.pays))
			return false;
		if (quartier == null) {
			if (other.quartier != null)
				return false;
		} else if (!quartier.equals(other.quartier))
			return false;
		if (siteWeb == null) {
			if (other.siteWeb != null)
				return false;
		} else if (!siteWeb.equals(other.siteWeb))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adresse [boitePostal=" + boitePostal + ", email=" + email + ", pays=" + pays + ", ville=" + ville
				+ ", quartier=" + quartier + ", adresseGeographique=" + adresseGeographique + ", siteWeb=" + siteWeb
				+ "]";
	}

}

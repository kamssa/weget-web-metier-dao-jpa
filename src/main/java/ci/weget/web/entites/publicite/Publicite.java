package ci.weget.web.entites.publicite;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.personne.Entreprise;
import ci.weget.web.entites.personne.Membre;

@Entity
@Table(name = "t_Publicite")
public class Publicite extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition="TEXT")
	private String contenu;
	private double prix;
	private int dureeTarif;
	private String typeDuree;
	private boolean free;
	private boolean pubSpecial;
	private String pathPhoto;
	private LocalDateTime date;
	private String pathVideo;
	private String page;
	private String lienPub;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Position")
	private Position position;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Membre")
	private Membre membre;

	public Publicite() {
		super();

	}

	public Publicite(String titre, String contenu, String pathPhoto, LocalDateTime date, String pathVideo,
			Entreprise entreprise, Membre membre) {
		super();
		this.titre = titre;
		this.contenu = contenu;
		this.pathPhoto = pathPhoto;
		this.date = date;
		this.pathVideo = pathVideo;

		this.entreprise = entreprise;
		this.membre = membre;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getPathVideo() {
		return pathVideo;
	}

	public void setPathVideo(String pathVideo) {
		this.pathVideo = pathVideo;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getDureeTarif() {
		return dureeTarif;
	}

	public void setDureeTarif(int dureeTarif) {
		this.dureeTarif = dureeTarif;
	}

	public String getTypeDuree() {
		return typeDuree;
	}

	public void setTypeDuree(String typeDuree) {
		this.typeDuree = typeDuree;
	}

	

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public boolean isPubSpecial() {
		return pubSpecial;
	}

	public void setPubSpecial(boolean pubSpecial) {
		this.pubSpecial = pubSpecial;
	}

	public String getLienPub() {
		return lienPub;
	}

	public void setLienPub(String lienPub) {
		this.lienPub = lienPub;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contenu == null) ? 0 : contenu.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((entreprise == null) ? 0 : entreprise.hashCode());
		result = prime * result + ((membre == null) ? 0 : membre.hashCode());
		result = prime * result + ((pathPhoto == null) ? 0 : pathPhoto.hashCode());
		result = prime * result + ((pathVideo == null) ? 0 : pathVideo.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		Publicite other = (Publicite) obj;
		if (contenu == null) {
			if (other.contenu != null)
				return false;
		} else if (!contenu.equals(other.contenu))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (entreprise == null) {
			if (other.entreprise != null)
				return false;
		} else if (!entreprise.equals(other.entreprise))
			return false;
		if (membre == null) {
			if (other.membre != null)
				return false;
		} else if (!membre.equals(other.membre))
			return false;
		if (pathPhoto == null) {
			if (other.pathPhoto != null)
				return false;
		} else if (!pathPhoto.equals(other.pathPhoto))
			return false;
		if (pathVideo == null) {
			if (other.pathVideo != null)
				return false;
		} else if (!pathVideo.equals(other.pathVideo))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

}

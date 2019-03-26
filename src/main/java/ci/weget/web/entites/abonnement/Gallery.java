package ci.weget.web.entites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "T_Gallery")
public class Gallery extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	@Column(columnDefinition="TEXT")
	private String description;
	private LocalDateTime date;
    @ElementCollection
	private List<String> pathPhoto=new ArrayList<>();
    @ElementCollection
	private List<String> pathVideo=new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Membre")
	private Membre membre;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_DetailAbonnement")
	private DetailAbonnement detailAbonnement;
	public Gallery() {
		super();

	}

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

	public LocalDateTime getDate() {
		return date;
	}

	public List<String> getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(List<String> pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	@PrePersist
	@PreUpdate
	private void setDate() {
		this.date = LocalDateTime.now();
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public List<String> getPathVideo() {
		return pathVideo;
	}

	public void setPathVideo(List<String> pathVideo) {
		this.pathVideo = pathVideo;
	}

	public DetailAbonnement getDetailAbonnement() {
		return detailAbonnement;
	}

	public void setDetailAbonnement(DetailAbonnement detailAbonnement) {
		this.detailAbonnement = detailAbonnement;
	}

	
}

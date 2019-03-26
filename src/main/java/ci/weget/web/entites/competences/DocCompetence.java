package ci.weget.web.entites.competences;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.personne.Membre;

@Entity
@Table(name = "t_DocumentCompetence")
public class DocCompetence extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition = "TEXT")
	private String description;

	private String pathDocument;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Abonnement")
	private Abonnement abonnement;

	public DocCompetence() {
		super();

	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(String pathDocument) {
		this.pathDocument = pathDocument;
	}

}

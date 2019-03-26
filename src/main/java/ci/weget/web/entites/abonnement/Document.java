package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_Document")
public class Document extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition="TEXT")
	private String description;
    @ElementCollection
	private List<String> pathDocument= new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_DetailAbonnement")
	private DetailAbonnement detailAbonnement;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   	@JoinColumn(name = "id_Formation")
   	private Formation formation;
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

	public List<String> getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(List<String> pathDocument) {
		this.pathDocument = pathDocument;
	}

	
	public DetailAbonnement getDetailAbonnement() {
		return detailAbonnement;
	}

	public void setDetailAbonnement(DetailAbonnement detailAbonnement) {
		this.detailAbonnement = detailAbonnement;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((detailAbonnement == null) ? 0 : detailAbonnement.hashCode());
		result = prime * result + ((formation == null) ? 0 : formation.hashCode());
		result = prime * result + ((pathDocument == null) ? 0 : pathDocument.hashCode());
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
		Document other = (Document) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (detailAbonnement == null) {
			if (other.detailAbonnement != null)
				return false;
		} else if (!detailAbonnement.equals(other.detailAbonnement))
			return false;
		if (formation == null) {
			if (other.formation != null)
				return false;
		} else if (!formation.equals(other.formation))
			return false;
		if (pathDocument == null) {
			if (other.pathDocument != null)
				return false;
		} else if (!pathDocument.equals(other.pathDocument))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Document [titre=" + titre + ", description=" + description + ", pathDocument=" + pathDocument
				+ ", detailAbonnement=" + detailAbonnement + ", formation=" + formation + "]";
	}

}

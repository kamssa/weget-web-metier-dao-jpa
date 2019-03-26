package ci.weget.web.entites.abonnement;

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

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.Formation;

@Entity
@Table(name = "t_Document")
public class Document extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition="TEXT")
	private String description;
	private String pathDocument;
    
    
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

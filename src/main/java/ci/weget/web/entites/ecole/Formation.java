package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_FORMATION")
public class Formation extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	@Column(columnDefinition="TEXT")
	private String description;
	private String formation_niveau;
	@Column(columnDefinition="TEXT")
	private String contenu;
	private String dureeFormation;
	private String diplome;
	private String formation_prix;
	private String pathPhoto;
	private String pathFormulaire;
	private String pathCatalogue;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "id_Formatiom")
	private List<Programme> programme = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_DetailAbonnement")
	private DetailAbonnement detailAbonnement;
    
	public Formation() {

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

	public String getFormation_niveau() {
		return formation_niveau;
	}

	public void setFormation_niveau(String formation_niveau) {
		this.formation_niveau = formation_niveau;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getDureeFormation() {
		return dureeFormation;
	}

	public void setDureeFormation(String dureeFormation) {
		this.dureeFormation = dureeFormation;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getFormation_prix() {
		return formation_prix;
	}

	public void setFormation_prix(String formation_prix) {
		this.formation_prix = formation_prix;
	}

	
	public DetailAbonnement getDetailAbonnement() {
		return detailAbonnement;
	}

	public void setDetailAbonnement(DetailAbonnement detailAbonnement) {
		this.detailAbonnement = detailAbonnement;
	}

	
	public List<Programme> getProgramme() {
		return programme;
	}

	public void setProgramme(List<Programme> programme) {
		this.programme = programme;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getPathCatalogue() {
		return pathCatalogue;
	}

	public void setPathCatalogue(String pathCatalogue) {
		this.pathCatalogue = pathCatalogue;
	}

	public String getPathFormulaire() {
		return pathFormulaire;
	}

	public void setPathFormulaire(String pathFormulaire) {
		this.pathFormulaire = pathFormulaire;
	}


}

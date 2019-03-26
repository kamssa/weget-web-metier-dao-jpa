package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_Programme")
public class Programme extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
    private String titre;
    @Column(columnDefinition="TEXT")
	private String description;
    private String duree;
	public Programme() {
		super();
	}
	public Programme(String titre, String description) {
		super();
		this.titre = titre;
		this.description = description;
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
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
    
}

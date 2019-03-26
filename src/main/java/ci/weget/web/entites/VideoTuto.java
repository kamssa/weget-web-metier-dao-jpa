package ci.weget.web.entites;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_VideoTuto")
public class VideoTuto extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
	private String libelle;
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	

}

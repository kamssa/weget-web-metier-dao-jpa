package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_Video")
public class Video  extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
	private String titre;
	private String pathVideo;
	public Video() {
		super();
		
	}
	public Video(String titre, String pathVideo) {
		super();
		this.titre = titre;
		this.pathVideo = pathVideo;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getPathVideo() {
		return pathVideo;
	}
	public void setPathVideo(String pathVideo) {
		this.pathVideo = pathVideo;
	}
	

}

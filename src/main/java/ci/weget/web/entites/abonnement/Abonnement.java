package ci.weget.web.entites;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Abonnement")
public class Abonnement extends AbstractEntity {

	@Column(columnDefinition = "TEXT")
	private String description;
	private String pathPhoto;
	private String pathPhotoCouveture;

	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Personne")
	private Membre membre;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Block")
	private Block block;

	private int nombreVue;
	private LocalDateTime dateExpire;
	private boolean active;
	private boolean abonneSpecial;
	private boolean free;
	private LocalDateTime dategratuite;
	private boolean utlisateurBanni;
	private boolean utlisateurSuspendu;
	private boolean utlisateurEnAttente;
	@Column(name = "id_Block", insertable = false, updatable = false)
	private long idBlock;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersone;
	
	public Abonnement() {
		super();

	}

	public Abonnement(Membre membre, Block block) {
		super();
		this.membre = membre;
		this.block = block;
	}

	public Abonnement(String description, String pathPhoto, String pathPhotoCouveture, Membre membre, Block block) {
		super();
		this.description = description;
		this.pathPhoto = pathPhoto;
		this.pathPhotoCouveture = pathPhotoCouveture;
		this.membre = membre;
		this.block = block;
	}

	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlocks(Block block) {
		this.block = block;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getIdBlock() {
		return idBlock;
	}

	public long getIdPersone() {
		return idPersone;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getPathPhotoCouveture() {
		return pathPhotoCouveture;
	}

	public void setPathPhotoCouveture(String pathPhotoCouveture) {
		this.pathPhotoCouveture = pathPhotoCouveture;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public int getNombreVue() {
		return nombreVue;
	}

	public void setNombreVue(int nombreVue) {
		this.nombreVue = nombreVue;
	}

	public LocalDateTime getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(LocalDateTime dateExpire) {
		this.dateExpire = dateExpire;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAbonneSpecial() {
		return abonneSpecial;
	}

	public void setAbonneSpecial(boolean abonneSpecial) {
		this.abonneSpecial = abonneSpecial;
	}

	public LocalDateTime getDategratuite() {
		return dategratuite;
	}

	public void setDategratuite(LocalDateTime dategratuite) {
		this.dategratuite = dategratuite;
	}

	public boolean isUtlisateurBanni() {
		return utlisateurBanni;
	}

	public void setUtlisateurBanni(boolean utlisateurBanni) {
		this.utlisateurBanni = utlisateurBanni;
	}

	public boolean isUtlisateurSuspendu() {
		return utlisateurSuspendu;
	}

	public void setUtlisateurSuspendu(boolean utlisateurSuspendu) {
		this.utlisateurSuspendu = utlisateurSuspendu;
	}

	public boolean isUtlisateurEnAttente() {
		return utlisateurEnAttente;
	}

	public void setUtlisateurEnAttente(boolean utlisateurEnAttente) {
		this.utlisateurEnAttente = utlisateurEnAttente;
	}

	public void setIdBlock(long idBlock) {
		this.idBlock = idBlock;
	}

	public void setIdPersone(long idPersone) {
		this.idPersone = idPersone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (abonneSpecial ? 1231 : 1237);
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + ((dateExpire == null) ? 0 : dateExpire.hashCode());
		result = prime * result + ((dategratuite == null) ? 0 : dategratuite.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((membre == null) ? 0 : membre.hashCode());
		result = prime * result + nombreVue;
		result = prime * result + ((pathPhoto == null) ? 0 : pathPhoto.hashCode());
		result = prime * result + ((pathPhotoCouveture == null) ? 0 : pathPhotoCouveture.hashCode());
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
		Abonnement other = (Abonnement) obj;
		if (abonneSpecial != other.abonneSpecial)
			return false;
		if (active != other.active)
			return false;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (dateExpire == null) {
			if (other.dateExpire != null)
				return false;
		} else if (!dateExpire.equals(other.dateExpire))
			return false;
		if (dategratuite == null) {
			if (other.dategratuite != null)
				return false;
		} else if (!dategratuite.equals(other.dategratuite))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (membre == null) {
			if (other.membre != null)
				return false;
		} else if (!membre.equals(other.membre))
			return false;
		if (nombreVue != other.nombreVue)
			return false;
		if (pathPhoto == null) {
			if (other.pathPhoto != null)
				return false;
		} else if (!pathPhoto.equals(other.pathPhoto))
			return false;
		if (pathPhotoCouveture == null) {
			if (other.pathPhotoCouveture != null)
				return false;
		} else if (!pathPhotoCouveture.equals(other.pathPhotoCouveture))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Abonnement [description=" + description + ", pathPhoto=" + pathPhoto + ", pathPhotoCouveture="
				+ pathPhotoCouveture + ", membre=" + membre + ", block=" + block + ", nombreVue=" + nombreVue
				+ ", dateExpire=" + dateExpire + ", active=" + active + ", abonneSpecial=" + abonneSpecial
				+ ", dategratuite=" + dategratuite + "]";
	}

}

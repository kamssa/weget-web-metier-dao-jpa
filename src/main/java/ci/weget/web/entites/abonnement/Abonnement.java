package ci.weget.web.entites.abonnement;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Membre;

@Entity
@Table(name = "T_Abonnement")
public class Abonnement extends AbstractEntity {

	@Column(columnDefinition = "TEXT")
	private String description;
	private String pathPhoto;
	private String pathPhotoCouveture;
	private LocalDateTime dateAbonnement;
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Membre membre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Espace")
	private Espace espace;
	@OneToOne(mappedBy="abonnement")
	private CvPersonne cvPersonne;

	private int nombreVue;
	private LocalDateTime dateExpire;
	private LocalDateTime dateExpireAbonneSpecial;

	private boolean active;
	private boolean abonneSpecial;
	private boolean free;
	private LocalDateTime dategratuite;
	private boolean utlisateurBanni;
	private boolean utlisateurSuspendu;
	private boolean utlisateurEnAttente;

	@Column(name = "id_Espace", insertable = false, updatable = false)
	private long idEspace;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersone;
	private double longitude;
    private double latitude;

	public Abonnement() {
		super();

	}

	public Abonnement(Membre membre, Espace block) {
		super();
		this.membre = membre;
		this.espace = block;
	}

	
	public Abonnement(String description, LocalDateTime dateAbonnement, Membre membre, Espace espace,
			int nombreVue, LocalDateTime dateExpire, LocalDateTime dateExpireAbonneSpecial, boolean active,
			boolean abonneSpecial, boolean free, LocalDateTime dategratuite, boolean utlisateurBanni,
			boolean utlisateurSuspendu, boolean utlisateurEnAttente, long idEspace, long idPersone, double longitude,
			double latitude) {
		super();
		this.description = description;
		this.dateAbonnement = dateAbonnement;
		this.membre = membre;
		this.espace = espace;
		
		this.nombreVue = nombreVue;
		this.dateExpire = dateExpire;
		this.dateExpireAbonneSpecial = dateExpireAbonneSpecial;
		this.active = active;
		this.abonneSpecial = abonneSpecial;
		this.free = free;
		this.dategratuite = dategratuite;
		this.utlisateurBanni = utlisateurBanni;
		this.utlisateurSuspendu = utlisateurSuspendu;
		this.utlisateurEnAttente = utlisateurEnAttente;
		this.idEspace = idEspace;
		this.idPersone = idPersone;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public LocalDateTime getDateAbonnement() {
		return dateAbonnement;
	}

	public void setDateAbonnement(LocalDateTime dateAbonnement) {
		this.dateAbonnement = dateAbonnement;
	}

	@PostPersist
	@PostUpdate
	public void setDateAbonnement() {
		this.dateAbonnement = LocalDateTime.now();
	}

	public long getIdPersone() {
		return idPersone;
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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	

	public Espace getEspace() {
		return espace;
	}

	public void setEspace(Espace espace) {
		this.espace = espace;
	}

	

	public long getIdEspace() {
		return idEspace;
	}

	public LocalDateTime getDateExpireAbonneSpecial() {
		return dateExpireAbonneSpecial;
	}

	public void setDateExpireAbonneSpecial(LocalDateTime dateExpireAbonneSpecial) {
		this.dateExpireAbonneSpecial = dateExpireAbonneSpecial;
	}

	

	public void setIdEspace(long idEspace) {
		this.idEspace = idEspace;
	}

	public void setIdPersone(long idPersone) {
		this.idPersone = idPersone;
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

	public CvPersonne getCvPersonne() {
		return cvPersonne;
	}

	public void setCvPersonne(CvPersonne cvPersonne) {
		this.cvPersonne = cvPersonne;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (abonneSpecial ? 1231 : 1237);
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((dateAbonnement == null) ? 0 : dateAbonnement.hashCode());
		result = prime * result + ((dateExpire == null) ? 0 : dateExpire.hashCode());
		result = prime * result + ((dateExpireAbonneSpecial == null) ? 0 : dateExpireAbonneSpecial.hashCode());
		result = prime * result + ((dategratuite == null) ? 0 : dategratuite.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((espace == null) ? 0 : espace.hashCode());
		result = prime * result + (free ? 1231 : 1237);
		result = prime * result + (int) (idEspace ^ (idEspace >>> 32));
		result = prime * result + (int) (idPersone ^ (idPersone >>> 32));
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((membre == null) ? 0 : membre.hashCode());
		result = prime * result + nombreVue;
		result = prime * result + (utlisateurBanni ? 1231 : 1237);
		result = prime * result + (utlisateurEnAttente ? 1231 : 1237);
		result = prime * result + (utlisateurSuspendu ? 1231 : 1237);
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
		if (dateAbonnement == null) {
			if (other.dateAbonnement != null)
				return false;
		} else if (!dateAbonnement.equals(other.dateAbonnement))
			return false;
		if (dateExpire == null) {
			if (other.dateExpire != null)
				return false;
		} else if (!dateExpire.equals(other.dateExpire))
			return false;
		if (dateExpireAbonneSpecial == null) {
			if (other.dateExpireAbonneSpecial != null)
				return false;
		} else if (!dateExpireAbonneSpecial.equals(other.dateExpireAbonneSpecial))
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
		if (espace == null) {
			if (other.espace != null)
				return false;
		} else if (!espace.equals(other.espace))
			return false;
		if (free != other.free)
			return false;
		if (idEspace != other.idEspace)
			return false;
		if (idPersone != other.idPersone)
			return false;
		
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (membre == null) {
			if (other.membre != null)
				return false;
		} else if (!membre.equals(other.membre))
			return false;
		if (nombreVue != other.nombreVue)
			return false;
		if (utlisateurBanni != other.utlisateurBanni)
			return false;
		if (utlisateurEnAttente != other.utlisateurEnAttente)
			return false;
		if (utlisateurSuspendu != other.utlisateurSuspendu)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Abonnement [description=" + description + ", dateAbonnement=" + dateAbonnement + ", membre=" + membre
				+ ", espace=" + espace + ", nombreVue=" + nombreVue + ", dateExpire=" + dateExpire
				+ ", dateExpireAbonneSpecial=" + dateExpireAbonneSpecial + ", active=" + active + ", abonneSpecial="
				+ abonneSpecial + ", free=" + free + ", dategratuite=" + dategratuite + ", utlisateurBanni="
				+ utlisateurBanni + ", utlisateurSuspendu=" + utlisateurSuspendu + ", utlisateurEnAttente="
				+ utlisateurEnAttente + ", idEspace=" + idEspace + ", idPersone=" + idPersone + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

	
}

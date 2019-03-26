package ci.weget.web.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "T_Personnes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_PERSONNE", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(name = "ME", value = Membre.class), @Type(name = "AD", value = Administrateur.class) })
public abstract class Personne  extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private String cni;
	private String titre;
	private String nom;
	private String prenom;
	private String password;
	private String repassword;
	@Column(name = "actived")
	private boolean actived;
	private String nomComplet;
	private String pathPhoto;
	private String pathPhotoCouveture;
	private Double nombreVue;
	private String groupSanguin;
	private LocalDate dateNaissance;
	private String genre;
	@Column(name = "TYPE_PERSONNE", insertable = false, updatable = false)
	private String type;

	@Embedded
	private Adresse adresse;

	@Column(unique = true)
	private String login;
	@Column(unique = true)
	private String telephone;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;
// cles etrangres
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "id_Personne")
	private List<Telephone> telephones= new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "id_Personne")
	private List<LangueParle> langues= new ArrayList<>();
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "id_TypeStatut")
	private TypeStatut typestatut;

	private String couleur;
	@Column(name = "enabled")
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean utlisateurBanni;
	private boolean utlisateurSuspendu;
	private boolean utlisateurEnAttente;
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@Transient
	private Integer age;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "personne_roles", 
	joinColumns = @JoinColumn(name = "personne_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Personne() {
		super();
		this.enabled = false;

	}

	public Personne(String login) {
		super();
		this.login = login;
	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.pathPhoto = pathPhoto;
		this.type = type;

	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.type = type;
	}

	public Personne(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public Personne(String password, String repassword, String login) {
		super();
		this.password = password;
		this.repassword = repassword;
		this.login = login;
	}

	public Personne(String nom, String prenom, String login, String password) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.password = password;

	}

	public Personne(String nom, String prenom, String password, String repassword, String login) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.repassword = repassword;
		this.login = login;
	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresse adresse, List<Telephone> telephones) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.pathPhoto = pathPhoto;
		this.type = type;
		this.adresse = adresse;
		this.telephones = telephones;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	public void calculateAge() {
		if (dateOfBirth == null) {
			age = null;
			return;
		}

		Calendar birth = new GregorianCalendar();
		birth.setTime(dateOfBirth);
		Calendar now = new GregorianCalendar();
		now.setTime(new Date());
		int adjust = 0;
		if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
			adjust = -1;
		}
		age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getAge() {
		return age;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCni() {
		return cni;
	}

	public void setCni(String cni) {
		this.cni = cni;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	@PrePersist
	public void setNomComplet() {
		this.nomComplet = nom + " " + prenom;
	}

	public Double getNombreVue() {
		return nombreVue;
	}

	public void setNombreVue(Double nombreVue) {
		this.nombreVue = nombreVue;
	}

	public TypeStatut getTypestatut() {
		return typestatut;
	}

	public void setTypestatut(TypeStatut typestatut) {
		this.typestatut = typestatut;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getRepassword() {
		return repassword;
	}

	@JsonSetter
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public String getGroupSanguin() {
		return groupSanguin;
	}

	public void setGroupSanguin(String groupSanguin) {
		this.groupSanguin = groupSanguin;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<LangueParle> getLangues() {
		return langues;
	}

	public void setLangues(List<LangueParle> langues) {
		this.langues = langues;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getPathPhotoCouveture() {
		return pathPhotoCouveture;
	}

	public void setPathPhotoCouveture(String pathPhotoCouveture) {
		this.pathPhotoCouveture = pathPhotoCouveture;
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

	@Override
	public String toString() {
		return "Personnes [cni=" + cni + ", titre=" + titre + ", nom=" + nom + ", prenom=" + prenom + ", password="
				+ password + ", repassword=" + repassword + ", actived=" + actived + ", nomComplet=" + nomComplet
				+ ", pathPhoto=" + pathPhoto + ", type=" + type + ", adresse=" + adresse + ", login=" + login
				+ ", entreprise=" + entreprise + ", telephones=" + telephones + "]";
	}

}

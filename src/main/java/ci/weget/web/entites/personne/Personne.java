package ci.weget.web.entites.personne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.Adresse;

@Entity
@Table(name = "T_Personnes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_PERSONNE", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(name = "ME", value = Membre.class), @Type(name = "AD", value = Administrateur.class),
		@Type(name = "SA", value = AdminSup.class) })
public class Personne extends AbstractEntity implements UserDetails {

	static final long serialVersionUID = 1L;
	private String cni;
	private String titre;
	private String nom;
	private String prenom;
	private String password;
	private String repassword;
	@Column(name = "actived")
	private boolean actived;
	private String nomComplet;
	private String nomProfile;
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
	@Email
	private String login;
	@Column(unique = true)
	private String telephone;
	private Long code;

// cles etrangres
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Personne")
	private List<Telephone> telephones = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_Personne")
	private List<LangueParle> langues = new ArrayList<>();

	private String couleur;
	/*
	 * @Column(name = "enabled") private boolean enabled;
	 */

	private boolean utlisateurBanni;
	private boolean utlisateurSuspendu;
	private boolean utlisateurEnAttente;
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@Transient
	private Integer age;

	private boolean enabled;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	public boolean credentialsNonExpired;
	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Personne() {
		super();
		// this.enabled = false;

	}

	public Personne(String login) {
		super();
		this.login = login;
	}

	public Personne(Long id, String login, String telephone, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.login = login;
		this.telephone = telephone;
		this.password = password;
		this.authorities = authorities;
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

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String type,
			Adresse adresse, List<Telephone> telephones) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;

		this.type = type;
		this.adresse = adresse;
		this.telephones = telephones;
	}

	public static Personne create(Personne user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new Personne(user.getId(), user.getUsername(), user.getTelephone(), user.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
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

	public Long getId() {
		return id;
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

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getNomProfile() {
		return nomProfile;
	}

	public void setNomProfile(String nomProfile) {
		this.nomProfile = nomProfile;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getAge() {
		return age;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (actived ? 1231 : 1237);
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((cni == null) ? 0 : cni.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((couleur == null) ? 0 : couleur.hashCode());
		result = prime * result + ((dateNaissance == null) ? 0 : dateNaissance.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((groupSanguin == null) ? 0 : groupSanguin.hashCode());
		result = prime * result + ((langues == null) ? 0 : langues.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((nomComplet == null) ? 0 : nomComplet.hashCode());
		result = prime * result + ((nomProfile == null) ? 0 : nomProfile.hashCode());
		result = prime * result + ((nombreVue == null) ? 0 : nombreVue.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((repassword == null) ? 0 : repassword.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((telephones == null) ? 0 : telephones.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Personne other = (Personne) obj;
		if (actived != other.actived)
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (cni == null) {
			if (other.cni != null)
				return false;
		} else if (!cni.equals(other.cni))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (couleur == null) {
			if (other.couleur != null)
				return false;
		} else if (!couleur.equals(other.couleur))
			return false;
		if (dateNaissance == null) {
			if (other.dateNaissance != null)
				return false;
		} else if (!dateNaissance.equals(other.dateNaissance))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;

		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (groupSanguin == null) {
			if (other.groupSanguin != null)
				return false;
		} else if (!groupSanguin.equals(other.groupSanguin))
			return false;
		if (langues == null) {
			if (other.langues != null)
				return false;
		} else if (!langues.equals(other.langues))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (nomComplet == null) {
			if (other.nomComplet != null)
				return false;
		} else if (!nomComplet.equals(other.nomComplet))
			return false;
		if (nomProfile == null) {
			if (other.nomProfile != null)
				return false;
		} else if (!nomProfile.equals(other.nomProfile))
			return false;
		if (nombreVue == null) {
			if (other.nombreVue != null)
				return false;
		} else if (!nombreVue.equals(other.nombreVue))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;

		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (repassword == null) {
			if (other.repassword != null)
				return false;
		} else if (!repassword.equals(other.repassword))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (telephones == null) {
			if (other.telephones != null)
				return false;
		} else if (!telephones.equals(other.telephones))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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
		return "Personne [cni=" + cni + ", titre=" + titre + ", nom=" + nom + ", prenom=" + prenom + ", password="
				+ password + ", repassword=" + repassword + ", actived=" + actived + ", nomComplet=" + nomComplet
				+ ", nomProfile=" + nomProfile + ", pathPhoto=" + ", pathPhotoCouveture=" + ", nombreVue=" + nombreVue
				+ ", groupSanguin=" + groupSanguin + ", dateNaissance=" + dateNaissance + ", genre=" + genre + ", type="
				+ type + ", adresse=" + adresse + ", login=" + login + ", telephone=" + telephone + ", code=" + code
				+ ", telephones=" + telephones + ", langues=" + langues + ", couleur=" + couleur + ", utlisateurBanni="
				+ utlisateurBanni + ", utlisateurSuspendu=" + utlisateurSuspendu + ", utlisateurEnAttente="
				+ utlisateurEnAttente + ", dateOfBirth=" + dateOfBirth + ", age=" + age + ", roles=" + roles + "]";
	}

}

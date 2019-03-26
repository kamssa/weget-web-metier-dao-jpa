package ci.weget.web.entites;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_VerificationToken")
public class VerificationToken extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String token;

	@OneToOne(targetEntity = Membre.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "membre_id")
	private Membre membre;

	private LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);

	public VerificationToken() {
		super();

	}

	public VerificationToken(String token, Membre membre) {
		super();
		this.token = token;
		this.membre = membre;
	}

	public VerificationToken(String token, Membre membre, LocalDateTime expiryDate) {
		super();
		this.token = token;
		this.membre = membre;
		this.expiryDate = expiryDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	
	 public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void updateToken(final String token) {
	        this.token = token;
	        this.expiryDate = LocalDateTime.now().plusDays(1);
	    }
}

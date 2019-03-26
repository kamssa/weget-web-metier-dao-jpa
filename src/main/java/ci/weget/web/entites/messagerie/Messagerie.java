package ci.weget.web.entites.messagerie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.personne.Personne;

@Entity
@Table(name = "T_Messageries")
public class Messagerie extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Personne")
	private Personne personne;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Message")
	private Message message;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Expediteur")
	private Expediteur expediteur;
	@Column(name = "id_Message", insertable = false, updatable = false)
	private long idMessage;
	@Column(name = "id_Expediteur", insertable = false, updatable = false)
	private long idExpediteur;

	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersonne;

	public Messagerie() {
		super();
	}

	public Messagerie(Personne personne, Message message, Expediteur expediteur) {
		super();
		this.personne = personne;
		this.message = message;
		this.expediteur = expediteur;
	}

	public long getIdMessage() {
		return idMessage;
	}

	public long getIdExpediteur() {
		return idExpediteur;
	}

	public long getIdPersonne() {
		return idPersonne;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Expediteur getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(Expediteur expediteur) {
		this.expediteur = expediteur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((expediteur == null) ? 0 : expediteur.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((personne == null) ? 0 : personne.hashCode());
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
		Messagerie other = (Messagerie) obj;
		if (expediteur == null) {
			if (other.expediteur != null)
				return false;
		} else if (!expediteur.equals(other.expediteur))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (personne == null) {
			if (other.personne != null)
				return false;
		} else if (!personne.equals(other.personne))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Messagerie [personne=" + personne + ", message=" + message + ", expediteur=" + expediteur + "]";
	}

}

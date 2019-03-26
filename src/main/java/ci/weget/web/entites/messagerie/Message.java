package ci.weget.web.entites.messagerie;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_Messages")
public class Message extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String sujet;
	@Column(columnDefinition="TEXT")
	private String contenu;
	private LocalDateTime date;
	private boolean statut;
	private boolean typeMessage;

	public Message() {
		super();

	}

	public Message(String sujet, String contenu) {
		super();
		this.sujet = sujet;
		this.contenu = contenu;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getDate() {
		return date;
	}

  public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	
	public boolean isTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(boolean typeMessage) {
		this.typeMessage = typeMessage;
	}

	@PrePersist
	public void setDate() {
		this.date = LocalDateTime.now();
		this.statut = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contenu == null) ? 0 : contenu.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (statut ? 1231 : 1237);
		result = prime * result + ((sujet == null) ? 0 : sujet.hashCode());
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
		Message other = (Message) obj;
		if (contenu == null) {
			if (other.contenu != null)
				return false;
		} else if (!contenu.equals(other.contenu))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (statut != other.statut)
			return false;
		if (sujet == null) {
			if (other.sujet != null)
				return false;
		} else if (!sujet.equals(other.sujet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [sujet=" + sujet + ", contenu=" + contenu + ", date=" + date + ", statut=" + statut + "]";
	}

}

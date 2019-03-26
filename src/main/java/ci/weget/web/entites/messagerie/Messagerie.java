package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Messageries")
public class Messagerie extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Personne")
	private Personne personne;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Messages")
	private Message message;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Expediteur")
	private Expediteur expediteur;
	
	
	
	/*@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersonne;
	@Column(name = "id_Messages", insertable = false, updatable = false)
	private long idMessages;
	@Column(name = "id_Expediteur", insertable = false, updatable = false)
	private long idExpediteur;*/

	public Messagerie() {
		super();

	}

	public Messagerie(Personne personne, Message message, Expediteur expediteur) {
		super();
		this.personne = personne;
		this.message = message;
		this.expediteur = expediteur;
	}

	/*public long getIdPersonne() {
		return idPersonne;
	}

	public long getIdMessages() {
		return idMessages;
	}

	public long getIdExpediteur() {
		return idExpediteur;
	}
*/
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

}

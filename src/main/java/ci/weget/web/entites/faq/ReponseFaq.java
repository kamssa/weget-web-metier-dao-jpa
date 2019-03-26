package ci.weget.web.entites.faq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_ReponseFaq")
public class ReponseFaq  extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String libelle;
	@Column(columnDefinition="TEXT")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_QuestionnaireFaq")
	private QuestionnaireFaq questionnaireFaq;
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public QuestionnaireFaq getQuestionnaireFaq() {
		return questionnaireFaq;
	}
	public void setQuestionnaireFaq(QuestionnaireFaq questionnaireFaq) {
		this.questionnaireFaq = questionnaireFaq;
	}
	
}

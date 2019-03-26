package ci.weget.web.metier.faq;

import java.util.List;

import ci.weget.web.entites.faq.QuestionnaireFaq;
import ci.weget.web.metier.Imetier;

public interface IQuestionnaireFaqMetier extends Imetier<QuestionnaireFaq, Long> {
public List<QuestionnaireFaq> findAllQuestionnaireFaqParCat(Long id);
}

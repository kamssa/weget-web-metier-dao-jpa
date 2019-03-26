package ci.weget.web.metier.faq;

import java.util.List;

import ci.weget.web.entites.faq.ReponseFaq;
import ci.weget.web.metier.Imetier;

public interface IReponseFaqMetier extends Imetier<ReponseFaq, Long> {
public List<ReponseFaq> findAllReponseFaqParQuest(Long id);
}

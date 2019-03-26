package ci.weget.web.dao.faq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.faq.QuestionnaireFaq;

public interface QuestionnaireFaqRepository extends JpaRepository<QuestionnaireFaq, Long> {
	// ramener les questionmpar category d'un block par id
		@Query("select q from QuestionnaireFaq q  where q.categorieFaq.id=?1")
		List<QuestionnaireFaq> findAllQuestionnaireFaqParCat(Long id);
}

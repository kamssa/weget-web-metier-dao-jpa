package ci.weget.web.dao.faq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.faq.ReponseFaq;

public interface ReponseFaqRepository extends JpaRepository<ReponseFaq, Long>{
	// ramener les Reponse par category 
			@Query("select r from ReponseFaq r  where r.questionnaireFaq.id=?1")
			List<ReponseFaq> findAllReponseFaqParQuest(Long id);
}

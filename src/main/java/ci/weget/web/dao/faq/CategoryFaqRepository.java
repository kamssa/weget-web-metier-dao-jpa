package ci.weget.web.dao.faq;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.faq.CategorieFaq;

public interface CategoryFaqRepository extends JpaRepository<CategorieFaq, Long>{

}

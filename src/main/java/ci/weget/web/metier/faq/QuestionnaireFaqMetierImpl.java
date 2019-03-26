package ci.weget.web.metier.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.faq.QuestionnaireFaqRepository;
import ci.weget.web.dao.faq.ReponseFaqRepository;
import ci.weget.web.entites.faq.QuestionnaireFaq;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class QuestionnaireFaqMetierImpl implements IQuestionnaireFaqMetier {
@Autowired
private QuestionnaireFaqRepository questionnaireFaqRepository;
	@Override
	public QuestionnaireFaq creer(QuestionnaireFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return questionnaireFaqRepository.save(entity);
	}

	@Override
	public QuestionnaireFaq modifier(QuestionnaireFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return questionnaireFaqRepository.save(entity);
	}

	@Override
	public List<QuestionnaireFaq> findAll() {
		// TODO Auto-generated method stub
		return questionnaireFaqRepository.findAll();
	}

	@Override
	public QuestionnaireFaq findById(Long id) {
		// TODO Auto-generated method stub
		return questionnaireFaqRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		questionnaireFaqRepository.findById(id).get();
		return true;
	}

	@Override
	public boolean supprimer(List<QuestionnaireFaq> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QuestionnaireFaq> findAllQuestionnaireFaqParCat(Long id) {
		// TODO Auto-generated method stub
		return questionnaireFaqRepository.findAllQuestionnaireFaqParCat(id);
	}

}

package ci.weget.web.metier.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.faq.ReponseFaqRepository;
import ci.weget.web.entites.faq.ReponseFaq;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ReponseFaqMetierImpl implements IReponseFaqMetier {
@Autowired
ReponseFaqRepository reponseFaqRepository;
	@Override
	public ReponseFaq creer(ReponseFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return reponseFaqRepository.save(entity);
	}

	@Override
	public ReponseFaq modifier(ReponseFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return reponseFaqRepository.save(entity);
	}

	@Override
	public List<ReponseFaq> findAll() {
		// TODO Auto-generated method stub
		return reponseFaqRepository.findAll();
	}

	@Override
	public ReponseFaq findById(Long id) {
		// TODO Auto-generated method stub
		return reponseFaqRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<ReponseFaq> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ReponseFaq> findAllReponseFaqParQuest(Long id) {
		// TODO Auto-generated method stub
		return reponseFaqRepository.findAllReponseFaqParQuest(id);
	}

}

package ci.weget.web.metier.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.faq.CategoryFaqRepository;
import ci.weget.web.entites.faq.CategorieFaq;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CategoryFaqMetierImpl implements ICategoryFaqMetier {
@Autowired
CategoryFaqRepository categoryFaqRepository;
	@Override
	public CategorieFaq creer(CategorieFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return categoryFaqRepository.save(entity);
	}

	@Override
	public CategorieFaq modifier(CategorieFaq entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return categoryFaqRepository.save(entity);
	}

	@Override
	public List<CategorieFaq> findAll() {
		// TODO Auto-generated method stub
		return categoryFaqRepository.findAll();
	}

	@Override
	public CategorieFaq findById(Long id) {
		// TODO Auto-generated method stub
		return categoryFaqRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		categoryFaqRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<CategorieFaq> entites) {
		categoryFaqRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		categoryFaqRepository.existsById(id);
		return true;
	}

}

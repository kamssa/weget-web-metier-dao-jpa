package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.InfoEnteteRepository;
import ci.weget.web.entites.InfoEntete;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class InfoEnteteMetierImpl implements IinfoEnteteMetier{
  @Autowired
  InfoEnteteRepository infoEnteteRepository;
	@Override
	public InfoEntete creer(InfoEntete entity) throws InvalideTogetException {
		
		return infoEnteteRepository.save(entity);
	}

	@Override
	public InfoEntete modifier(InfoEntete entity) throws InvalideTogetException {
		
		return infoEnteteRepository.save(entity);
	}

	@Override
	public List<InfoEntete> findAll() {
	
		return infoEnteteRepository.findAll();
	}

	@Override
	public InfoEntete findById(Long id) {
		// TODO Auto-generated method stub
		return infoEnteteRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<InfoEntete> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}

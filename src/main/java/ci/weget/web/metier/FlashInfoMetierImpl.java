package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.FlashInfoRepository;
import ci.weget.web.entites.ecole.FlashInfo;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class FlashInfoMetierImpl implements IFlashInfoMetier {
@Autowired
private FlashInfoRepository flashInfoRepository;
	@Override
	public FlashInfo creer(FlashInfo entity) throws InvalideTogetException {
		return flashInfoRepository.save(entity);
		
	}

	@Override
	public FlashInfo modifier(FlashInfo modif) throws InvalideTogetException {
		
		Optional<FlashInfo> flashInfo = flashInfoRepository.findById(modif.getId());

		if (flashInfo.isPresent()) {
			
			if (flashInfo.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return flashInfoRepository.save(modif);
	}

	@Override
	public List<FlashInfo> findAll() {
		
		return flashInfoRepository.findAll();
	}

	@Override
	public FlashInfo findById(Long id) {
		
		return flashInfoRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		
	flashInfoRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<FlashInfo> entites) {
		
		return false;
	}

	@Override
	public boolean existe(Long id) {
		flashInfoRepository.existsById(id);
		return true;
	}

	@Override
	public List<FlashInfo> findAllFlashInfoParIdSousBlock(Long id) {
		
		return flashInfoRepository.findAllFlashInfoByEcole(id);
	}

}

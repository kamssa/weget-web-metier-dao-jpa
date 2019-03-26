package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.DetailAbonnementRepository;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.exception.InvalideTogetException;

@Service
@Transactional
public class DetailAbonnementMetierImpl implements IDetailAbonnementMetier {
@Autowired
private DetailAbonnementRepository detailAbonnementRepository;
	@Override
	public DetailAbonnement creer(DetailAbonnement entity) throws InvalideTogetException {
		
		return detailAbonnementRepository.save(entity);
	}

	@Override
	public DetailAbonnement modifier(DetailAbonnement entity) throws InvalideTogetException {
		
		return detailAbonnementRepository.save(entity);
	}

	@Override
	public List<DetailAbonnement> findAll() {
		
		return detailAbonnementRepository.findAll();
	}

	@Override
	public DetailAbonnement findById(Long id) {
		
		return detailAbonnementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		
		detailAbonnementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<DetailAbonnement> entites) {
		
		 detailAbonnementRepository.deleteAll(entites);
		 return true;
	}

	@Override
	public boolean existe(Long id) {
		
		return detailAbonnementRepository.existsById(id);
	}

	@Override
	public DetailAbonnement rechercheDetailAbonnementParId(Long id) {
		
		return detailAbonnementRepository.findDetailAonnementyId(id);
	}

	@Override
	public List<DetailAbonnement> chercherDetailAbonnementParMc(String mc) {
		
		return detailAbonnementRepository.chercherDetailAbonnementParMc(mc);
	}

	@Override
	public DetailAbonnement findDetailAbonnementParIdDetailBlock(Long id) {
		
		return detailAbonnementRepository.findDetailAbonnementParIdDetailBlock(id);
	}

	@Override
	public List<DetailAbonnement> findDetailAbonnementParIdBlock(Long id) {
		
		return detailAbonnementRepository.findDetailAbonnementParIdBlock(id);
	}

	@Override
	public DetailAbonnement findDetailAbonnementParNom(String nom) {
		
		return detailAbonnementRepository.findDetailAbonnementParNom(nom);
	}

	@Override
	public List<DetailAbonnement> findDetailAbonnementParIdEta(Long id) {
		// TODO Auto-generated method stub
		return detailAbonnementRepository.findDetailAbonnementParIdEta(id);
	}

}

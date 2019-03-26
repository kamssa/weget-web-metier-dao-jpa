package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TarifAbonnementRepository;
import ci.weget.web.entites.abonnement.TarifAbonnement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TarifAbonnementMeteirImpl implements ITarifAbonnementMetier {
@Autowired
TarifAbonnementRepository tarifAbonnementRepository;
	@Override
	public TarifAbonnement creer(TarifAbonnement entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return tarifAbonnementRepository.save(entity);
	}

	@Override
	public TarifAbonnement modifier(TarifAbonnement entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return tarifAbonnementRepository.save(entity);
	}

	@Override
	public List<TarifAbonnement> findAll() {
		// TODO Auto-generated method stub
		return tarifAbonnementRepository.findAll();
	}

	@Override
	public TarifAbonnement findById(Long id) {
		// TODO Auto-generated method stub
		return tarifAbonnementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<TarifAbonnement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		
		return tarifAbonnementRepository.existsById(id);
	}

	@Override
	public TarifAbonnement tarifParIdAbonnement(Long id) {
		
		return tarifAbonnementRepository.getTarifParIdAbonnement(id);
	}

}

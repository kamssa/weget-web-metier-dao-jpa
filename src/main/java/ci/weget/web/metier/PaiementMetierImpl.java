package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ci.weget.web.dao.PaiementRepository;
import ci.weget.web.entites.commande.Paiement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PaiementMetierImpl implements IPaiementMetier {

	@Autowired
	PaiementRepository paiementRepository;
	@Override
	public Paiement creer(Paiement entity) throws InvalideTogetException {
		
		return paiementRepository.save(entity);
	}

	@Override
	public Paiement modifier(Paiement modif) throws InvalideTogetException {
		Optional<Paiement> paiement = paiementRepository.findById(modif.getId());

		if (paiement.isPresent()) {
			
			if (paiement.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return paiementRepository.save(modif);
	}

	@Override
	public List<Paiement> findAll() {
		return paiementRepository.findAll();
	}

	@Override
	public Paiement findById(Long id) {
		
		return paiementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		paiementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Paiement> entites) {
		paiementRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		paiementRepository.existsById(id);
		return true;
	}

	@Override
	public Paiement getPaiementDeCommande(Long id) {
		return paiementRepository.getPaiementDeCommande(id);
	}

}

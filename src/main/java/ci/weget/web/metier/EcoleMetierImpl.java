package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.EcoleRepository;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.exception.InvalideTogetException;

@Service
@Transactional
public class EcoleMetierImpl implements IEcoleMetier {
	@Autowired
	private EcoleRepository ecoleRepository;
	@Autowired
	private AbonnementRepository abonnementRepository;

	@Override
	public Ecole creer(Ecole entity) throws InvalideTogetException {

		Ecole db = ecoleRepository.save(entity);

		if (db.getNom().equals(null) && db.getPresentation().equals(null) && db.getPathLogo().equals(null)
				&& db.getTypeEtablissement().equals(null) && db.getAdresse().getPays().equals(null)
				&& db.getAdresse().getVille().equals(null) && db.getAdresse().getBoitePostal().equals(null)
				&& db.getAdresse().getEmail().equals(null) && db.getAdresse().getSiteWeb().equals(null)) {
			throw new RuntimeException("Vos informations ne sont pas à jour");
		}
		Abonnement ab = db.getAbonnement();
		ab.setActive(true);
		abonnementRepository.save(ab);
		return db;
	}

	@Override
	public Ecole modifier(Ecole modif) throws InvalideTogetException {

		Optional<Ecole> detailAbonnement = ecoleRepository.findById(modif.getId());
		if (detailAbonnement.get().getAbonnement().getEspace().getTypeEspace().equals("ecole")) {

			if (detailAbonnement.isPresent()) {

				if (detailAbonnement.get().getVersion() != modif.getVersion()) {
					throw new InvalideTogetException("ce libelle a deja ete modifier");
				}

			} else
				throw new InvalideTogetException("modif est un objet null");

			detailAbonnement.get().getAbonnement().setActive(true);
		}
		return ecoleRepository.save(modif);
	}

	@Override
	public List<Ecole> findAll() {

		return ecoleRepository.findAll();
	}

	@Override
	public Ecole findById(Long id) {

		return ecoleRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {

		ecoleRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Ecole> entites) {

		ecoleRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {

		return ecoleRepository.existsById(id);
	}

	@Override
	public List<Ecole> findEcoleByMc(String mc) {
		return ecoleRepository.findEcoleByMc(mc);
	}

	@Override
	public Ecole findEcoleByIdAbonnement(long id) {

		return ecoleRepository.findEcoleByIdAbonnement(id);
	}

	
	@Override
	public Ecole findEcoleParNom(String nom) {

		return ecoleRepository.findEcoleByNom(nom);
	}

	@Override
	public List<Ecole> findEcoleIdTypeEtablissement(long id) {

		return ecoleRepository.findEcoleByTypeEtablissement(id);
	}

}

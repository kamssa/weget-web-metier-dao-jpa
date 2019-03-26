package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TarifMetierImpl implements ITarifMetier {
    @Autowired
    private IEspaceMetier espaceMetier;
	@Autowired
	private TarifRepository tarifRepository;
	
	
	@Override
	public Tarif ajouterEspace(Tarif t, Espace b) {
		Espace b1= espaceMetier.findById(b.getId());
		t.setEspace(b1);
		return tarifRepository.save(t);
	}

	@Override
	public Tarif creer(Tarif entity) throws InvalideTogetException {
		
		return tarifRepository.save(entity);
	}

	@Override
	public Tarif modifier(Tarif modif) throws InvalideTogetException {
		
		Optional<Tarif> tarif = tarifRepository.findById(modif.getId());

		if (tarif.isPresent()) {
			
			if (tarif.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return tarifRepository.save(modif);
	}

	@Override
	public List<Tarif> findAll() {
		
		return tarifRepository.findAll();
	}

	@Override
	public Tarif findById(Long id) {
		
		return tarifRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		tarifRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Tarif> entites) {
		tarifRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		tarifRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Tarif> getTarifByIdEspace(long id) {
		return tarifRepository.getTarifByEspace(id);
	}

	
}

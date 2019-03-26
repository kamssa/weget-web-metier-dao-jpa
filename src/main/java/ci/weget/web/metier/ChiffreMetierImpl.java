package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.ChiffreRepository;
import ci.weget.web.dao.EcoleRepository;
import ci.weget.web.entites.ecole.Chiffre;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
@Transactional
public class ChiffreMetierImpl implements IChiffreMetier {
	@Autowired
	private ChiffreRepository chiffreRepository;
	@Autowired
	private EcoleRepository detailAbonnementRepository;

	@Override
	public Chiffre creer(Chiffre entity) throws InvalideTogetException {

		return chiffreRepository.save(entity);
	}

	@Override
	public Chiffre modifier(Chiffre modif) throws InvalideTogetException {
		Optional<Chiffre> chiffre = chiffreRepository.findById(modif.getId());

		if (modif != null) {

			if (chiffre.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		return chiffreRepository.save(modif);
	}

	@Override
	public List<Chiffre> findAll() {
		// TODO Auto-generated method stub
		return chiffreRepository.findAll();
	}

	@Override
	public Chiffre findById(Long id) {

		return chiffreRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		chiffreRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Chiffre> entites) {
		// TODO Auto-generated method stub
		chiffreRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		chiffreRepository.existsById(id);
		return true;
	}

	@Override
	public List<Chiffre> getChifreByIdSousBlock(Long id) {

		return null;
	}

}

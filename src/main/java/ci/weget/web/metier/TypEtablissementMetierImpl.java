package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TypeEtablissementRepository;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.Temoignage;
import ci.weget.web.entites.ecole.TypeEtablissement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TypEtablissementMetierImpl implements ITypeEtablissementMetier {
@Autowired
private TypeEtablissementRepository typeEtablissementRepository;
	@Override
	public TypeEtablissement creer(TypeEtablissement entity) throws InvalideTogetException {
		
		return typeEtablissementRepository.save(entity);
	}

	@Override
	public TypeEtablissement modifier(TypeEtablissement modif) throws InvalideTogetException {
		
		Optional<TypeEtablissement> typeEtablissement = typeEtablissementRepository.findById(modif.getId());

		if (modif != null) {
			
			if (typeEtablissement.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return typeEtablissementRepository.save(modif);
	}

	@Override
	public List<TypeEtablissement> findAll() {
		
		return typeEtablissementRepository.findAll();
	}

	@Override
	public TypeEtablissement findById(Long id) {
		
		return typeEtablissementRepository.getTypeEtablissementByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		typeEtablissementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<TypeEtablissement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeEtablissement rechercheParLibelle(String libelle) {
		
		return typeEtablissementRepository.findByLibelle(libelle);
	}

	

}

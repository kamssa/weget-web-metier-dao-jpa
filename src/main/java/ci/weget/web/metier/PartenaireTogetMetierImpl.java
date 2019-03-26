package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.PartenaireRepository;
import ci.weget.web.entites.Partenaire;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PartenaireMetierImpl implements IPartenaireMetier{
@Autowired
private PartenaireRepository partenaireRepository;

@Override
public Partenaire creer(Partenaire entity) throws InvalideTogetException {

	return partenaireRepository.save(entity);
}

@Override
public Partenaire modifier(Partenaire entity) throws InvalideTogetException {
	
	return partenaireRepository.save(entity);
}

@Override
public List<Partenaire> findAll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Partenaire findById(Long id) {
	
	return partenaireRepository.getByid(id);
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean supprimer(List<Partenaire> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<Partenaire> getPartenaireByIdSousBlock(Long id) {
	
	return null;
}
	
	
	
}

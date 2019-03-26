package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TypeEtablissementRepository;
import ci.weget.web.dao.combo.ComboTypeEtablissementRepository;
import ci.weget.web.entites.combo.ComboTypeEtablisement;
import ci.weget.web.entites.ecole.TypeEtablissement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboTypeEtablissementMetierImpl implements IComboTypeEtablissementMetier {
@Autowired
ComboTypeEtablissementRepository comboTypeEtablissementRepository;

@Override
public ComboTypeEtablisement creer(ComboTypeEtablisement entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return comboTypeEtablissementRepository.save(entity);
}

@Override
public ComboTypeEtablisement modifier(ComboTypeEtablisement entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return comboTypeEtablissementRepository.save(entity);
}

@Override
public List<ComboTypeEtablisement> findAll() {
	// TODO Auto-generated method stub
	return comboTypeEtablissementRepository.findAll();
}

@Override
public ComboTypeEtablisement findById(Long id) {
	// TODO Auto-generated method stub
	return comboTypeEtablissementRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	comboTypeEtablissementRepository.deleteById(id);
	return true;
}

@Override
public boolean supprimer(List<ComboTypeEtablisement> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}
	

}

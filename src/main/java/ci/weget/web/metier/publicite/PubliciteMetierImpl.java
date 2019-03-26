package ci.weget.web.metier.publicite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.publicite.PubliciteRepository;
import ci.weget.web.entites.publicite.Publicite;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PubliciteMetierImpl implements IPubliciteMetier{

	@Autowired
	PubliciteRepository publiciteRepository;
	@Override
	public Publicite creer(Publicite entity) throws InvalideTogetException {
		
		return publiciteRepository.save(entity);
	}

	@Override
	public Publicite modifier(Publicite entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return publiciteRepository.save(entity);
	}

	@Override
	public List<Publicite> findAll() {
		
		return publiciteRepository.findAll();
	}

	@Override
	public Publicite findById(Long id) {
		
		return publiciteRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		publiciteRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Publicite> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Publicite> findAllPubliciteParIdPosition(Long id) {
		// TODO Auto-generated method stub
		return publiciteRepository.findAllPubliciteParIdPosition(id);
	}

	@Override
	public List<Publicite> findAllPubliciteParPage(String page) {
		// TODO Auto-generated method stub
		return publiciteRepository.findAllPubliciteParPage(page);
	}

}

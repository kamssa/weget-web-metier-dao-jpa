package ci.weget.web.metier.publicite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.publicite.PositionRepository;
import ci.weget.web.entites.publicite.Position;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PositionMetierImpl implements IPositionMetier {
@Autowired
private PositionRepository positionRepository;
	@Override
	public Position creer(Position entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return positionRepository.save(entity);
	}

	@Override
	public Position modifier(Position entity) throws InvalideTogetException {
		
		return positionRepository.save(entity);
	}

	@Override
	public List<Position> findAll() {
		
		return positionRepository.findAll();
	}

	@Override
	public Position findById(Long id) {
		// TODO Auto-generated method stub
		return positionRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Position> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position positionParlibelle(String libelle) {
		// TODO Auto-generated method stub
		return positionRepository.findAllPositionParNom(libelle);
	}

}

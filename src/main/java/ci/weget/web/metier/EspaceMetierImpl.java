package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class BlocksMetierImpl implements IBlocksMetier {

	@Autowired
	private BlocksRepository blocksRepository;

	@Override
	public Block creer(Block block) throws InvalideTogetException {
		if ((block.getLibelle() == null) || (block.getLibelle() == "")) {
			throw new InvalideTogetException("Le libelle ne peut etre null");
		}

		Block blocks = null;

		blocks = blocksRepository.findByLibelle(block.getLibelle());
		if (blocks != null)
			throw new InvalideTogetException("ce libelle existe deja");

		return blocksRepository.save(block);
	}

	@Override
	public Block modifier(Block block) throws InvalideTogetException {

		return blocksRepository.save(block);
	}

	@Override
	public List<Block> chercherBlockParMc(String mc) {

		return blocksRepository.chercherBlockParMc(mc);
	}

	@Override
	public List<Block> findAll() {
		return blocksRepository.findAll();
	}

	@Override
	public Block findById(Long id) {
		return blocksRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		blocksRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Block> entites) {
		blocksRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		blocksRepository.existsById(id);
		return true;
	}

	@Override
	public Block rechercheParLibelle(String libelle) {

		return blocksRepository.findByLibelle(libelle);
	}

}

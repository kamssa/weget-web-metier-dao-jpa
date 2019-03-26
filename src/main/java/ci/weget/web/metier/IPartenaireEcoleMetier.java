package ci.weget.web.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.entites.ecole.PartenaireEcole;

public interface IPartenaireEcoleMetier extends Imetier<PartenaireEcole, Long> {
	public List<PartenaireEcole>	getPartenaireByIdEcole(Long id);
	PartenaireEcole createImagePartenaireEcole(MultipartFile file, Long id) throws IllegalStateException,IOException;
	byte[] getImagePartenaireEcole(Long version, Long id, String libelle) throws FileNotFoundException, IOException;
}

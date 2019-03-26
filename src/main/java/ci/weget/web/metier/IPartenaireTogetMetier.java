package ci.weget.web.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.entites.PartenaireToget;
import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.entites.personne.Personne;

public interface IPartenaireTogetMetier extends Imetier<PartenaireToget, Long>{
	PartenaireToget createImagePartenaireToget(MultipartFile file, Long id) throws IllegalStateException,IOException;
	byte[] getImagePartenaireToget(Long version, Long id, String libelle) throws FileNotFoundException, IOException;
}

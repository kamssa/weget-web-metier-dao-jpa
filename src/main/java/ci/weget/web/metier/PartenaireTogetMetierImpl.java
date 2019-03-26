package ci.weget.web.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.dao.PartenaireTogetRepository;
import ci.weget.web.entites.PartenaireToget;
import ci.weget.web.entites.ecole.Formation;
import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.PathImage;

@Service
public class PartenaireTogetMetierImpl implements IPartenaireTogetMetier {
	@Autowired
	private PartenaireTogetRepository partenaireTogetRepository;
////////////chemin ou sera sauvegarder les photos
//////////// ////////////////////////////////////////
@Value("${dir.images}")
private String togetImage;
	@Override
	public PartenaireToget creer(PartenaireToget entity) throws InvalideTogetException {

		return partenaireTogetRepository.save(entity);
	}

	@Override
	public PartenaireToget modifier(PartenaireToget modif) throws InvalideTogetException {

		Optional<PartenaireToget> partenaire = partenaireTogetRepository.findById(modif.getId());

		if (partenaire.isPresent()) {

			if (partenaire.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		return partenaireTogetRepository.save(modif);
	}

	@Override
	public List<PartenaireToget> findAll() {
		List<PartenaireToget> partenaires = partenaireTogetRepository.findAll();

		List<PartenaireToget> partenaireTrouves = partenaires.stream()
				.collect(Collectors.toList());
		
		return partenaireTrouves;

	}

	@Override
	public PartenaireToget findById(Long id) {

		return partenaireTogetRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		partenaireTogetRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<PartenaireToget> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PartenaireToget createImagePartenaireToget(MultipartFile file, Long id) throws IllegalStateException, IOException {
		PartenaireToget partenaireToget = null;
		String libelle = file.getOriginalFilename();
		String libelleSEspace = libelle.trim();
		String libelleS = libelleSEspace.replaceAll("\\s", "");
		partenaireToget = partenaireTogetRepository.findById(id).get();
		

		String path = PathImage.getLienRetourPartenaireToget() + partenaireToget.getVersion() + "/" + id+"/"+libelleS;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienRetourPartenaireToget() + "/"+id+"/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			partenaireToget.setPathLogo(path);
			System.out.println(path);
			file.transferTo(new File(dossier + libelleS));
            partenaireTogetRepository.save(partenaireToget);
		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
		return partenaireToget;
	}

	@Override
	public byte[] getImagePartenaireToget(Long version, Long id,String libelle) throws FileNotFoundException, IOException {
		String dossier = togetImage + "/" + PathImage.getLienImagePartenaireToget() + "/"+id+"/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	


}

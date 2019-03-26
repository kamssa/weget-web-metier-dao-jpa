package ci.weget.web.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.dao.FormationRepository;
import ci.weget.web.entites.ecole.Formation;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.PathImage;

@Service
public class FormationMetierImpl implements IFormationMetier{
////////////chemin ou sera sauvegarder les photos
//////////// ////////////////////////////////////////
@Value("${dir.images}")
private String togetImage;
	@Autowired
	private FormationRepository formationRepository;
	@Override
	public Formation creer(Formation entity) throws InvalideTogetException {
		
		return formationRepository.save(entity);
	}

	@Override
	public Formation modifier(Formation modif) throws InvalideTogetException {
		Optional<Formation> formation = formationRepository.findById(modif.getId());

		if (formation.isPresent()) {
			
			if (formation.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return formationRepository.save(modif);
	}

	@Override
	public List<Formation> findAll() {
		return formationRepository.findAll();
	}

	@Override
	public Formation findById(Long id) {
		
		return formationRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		formationRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Formation> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	@Override
	public List<Formation> getFormationByEcole(Long id) {
		
		return formationRepository.getFormationByEcole(id);
	}

	@Override
	public Formation createImageFormation(MultipartFile file, Long id) throws IllegalStateException, IOException {
		
		Formation f = null;
		String libelle = file.getOriginalFilename();
		f = formationRepository.findById(id).get();

		String path = PathImage.getLienRetourImageFormation() + f.getVersion() + "/" + id;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageFormation() + "/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			f.setPathPhoto(path);
			System.out.println(path);
			file.transferTo(new File(dossier + id));
            formationRepository.save(f);
		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
		return f;
	}

	@Override
	public Formation createFormulaireFormation(MultipartFile file, Long id) throws IllegalStateException, IOException {
		Formation f = null;
		String libelle = file.getOriginalFilename();
		f = formationRepository.findById(id).get();

		String path = PathImage.getLienRetourImageFormulaireFormation() + f.getVersion() + "/" + id;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageFormulaireFormation() + "/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			f.setPathPhoto(path);
			System.out.println(path);
			file.transferTo(new File(dossier + id));
            formationRepository.save(f);
		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
		return f;
	}

	@Override
	public Formation createCatalogueFormation(MultipartFile file, Long id) throws IllegalStateException, IOException {
		Formation f = null;
		String libelle = file.getOriginalFilename();
		f = formationRepository.findById(id).get();

		String path = PathImage.getLienRetourCatalogueFormation() + f.getVersion() + "/" + id;
		System.out.println(path);

		String dossier = togetImage + "/" + PathImage.getLienImageCatalogueFormation() + "/";
		File rep = new File(dossier);

		if (!file.isEmpty()) {
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}
		}
		try {
			// enregistrer le chemin dans la photo
			f.setPathPhoto(path);
			System.out.println(path);
			file.transferTo(new File(dossier + id));
            formationRepository.save(f);
		} catch (Exception e) {

			throw new RuntimeException("Operation reussi");
		}
		return f;
	}

	@Override
	public ResponseEntity<Resource> getImageFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException {

		String dossier = togetImage + "/" + PathImage.getLienImageFormation()+ "/" + id;

		Path filePath = Paths.get(dossier);
		Resource resource = new UrlResource(filePath.toUri());
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			throw new RuntimeException("Desole,nous ne pouvons determiner le type de fichier");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@Override
	public  ResponseEntity<Resource> getImageFormulaireFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException {
		System.out.println(version);

		String dossier = togetImage + "/" + PathImage.getLienImageFormulaireFormation()+ "/" + id;

		Path filePath = Paths.get(dossier);
		Resource resource = new UrlResource(filePath.toUri());
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			throw new RuntimeException("Desole,nous ne pouvons determiner le type de fichier");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@Override
	public  ResponseEntity<Resource> getImageCatalogueFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException {
		String dossier = togetImage + "/" + PathImage.getLienImageCatalogueFormation()+ "/" + id;

		Path filePath = Paths.get(dossier);
		Resource resource = new UrlResource(filePath.toUri());
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			throw new RuntimeException("Desole,nous ne pouvons determiner le type de fichier");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}

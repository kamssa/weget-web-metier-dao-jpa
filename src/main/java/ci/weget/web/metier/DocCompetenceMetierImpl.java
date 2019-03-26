package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.DocComptRepository;
import ci.weget.web.entites.competences.DocCompetence;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.exception.InvalideTogetException;
import org.springframework.core.io.Resource;
import java.nio.file.Path;
import org.springframework.core.io.UrlResource;


@Service
public class DocCompetenceMetierImpl implements IDocCompetenceMetier {

	private final Path fileStorageLocation=null;
	@Autowired
private DocComptRepository docCompetenceRepository;

@Override
public DocCompetence creer(DocCompetence entity) throws InvalideTogetException {
	
	return docCompetenceRepository.save(entity);
}

@Override
public DocCompetence modifier(DocCompetence modif) throws InvalideTogetException {
	
	Optional<DocCompetence> docCompetence = docCompetenceRepository.findById(modif.getId());

	if (modif != null) {
		
		if (docCompetence.get().getVersion() != modif.getVersion()) {
			throw new InvalideTogetException("ce libelle a deja ete modifier");
		}

	} else
		throw new InvalideTogetException("modif est un objet null");
	
	return docCompetenceRepository.save(modif);
}

@Override
public List<DocCompetence> findAll() {
	
	return docCompetenceRepository.findAll();
}

@Override
public DocCompetence findById(Long id) {
	
	return docCompetenceRepository.getByid(id);
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	 docCompetenceRepository.deleteById(id);
	 return true;
}

@Override
public boolean supprimer(List<DocCompetence> entites) {
	docCompetenceRepository.deleteAll(entites);
	return true;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public DocCompetence chercherDocParTitre(String titre) {
	
	return docCompetenceRepository.cherchereDocumentParTitre(titre);
}

@Override
public List<DocCompetence> findAllDocumentsParIdAbonne(Long id) {
	
	return docCompetenceRepository.findAllDocumentsParIdAbonne(id);
}
	
@Override
public Resource loadFileAsResource(String fileName) {
    try {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found " + fileName);
        }
    } catch (Exception ex) {
        throw new RuntimeException("File not found " + fileName, ex);
    }
}
   
}

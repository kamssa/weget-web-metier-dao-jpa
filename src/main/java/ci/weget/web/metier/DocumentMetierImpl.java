package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.DocumentRepository;
import ci.weget.web.entites.abonnement.Document;
import ci.weget.web.entites.competences.DocCompetence;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DocumentMetierImpl implements IDocumentMetier {
	@Autowired
	DocumentRepository documentRepository;

	@Override
	public Document creer(Document entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return documentRepository.save(entity);
	}

	@Override
	public Document modifier(Document modif) throws InvalideTogetException {
		
		
		Optional<Document> document = documentRepository.findById(modif.getId());

		if (modif != null) {
			
			if (document.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return documentRepository.save(modif);
	}

	@Override
	public List<Document> findAll() {
		
		return documentRepository.findAll();
	}

	@Override
	public Document findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Document> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document chercherDocumentParLibelle(String titre) {
		
		return documentRepository.cherchereDocumentParLibelle(titre);
	}

	

}

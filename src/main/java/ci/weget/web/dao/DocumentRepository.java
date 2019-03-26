package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	
	// rechercher un document a partir de son libelle
	@Query("select doc from Document doc where doc.titre=?1")
	Document cherchereDocumentParLibelle(String titre);

	}

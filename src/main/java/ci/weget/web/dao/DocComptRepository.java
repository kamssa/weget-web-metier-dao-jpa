package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.competences.DocCompetence;
import ci.weget.web.entites.espace.Espace;

@Repository
public interface DocComptRepository extends JpaRepository<DocCompetence, Long> {
	

	// ramener un block a partir de son identifiant
	@Query("select docCompt from DocCompetence docCompt where docCompt.id=?1")
	DocCompetence getByid(Long id);
	// rechercher un document competence a partir de son titre
	@Query("select docCompt from DocCompetence docCompt where docCompt.titre=?1")
	DocCompetence cherchereDocumentParTitre(String titre);

	// ramener une liste de document  quand on connait l'id abonne
	   @Query("select docCompt from DocCompetence docCompt left join fetch docCompt.abonnement ab where ab.id=?1")
		List<DocCompetence> findAllDocumentsParIdAbonne(Long id);
}

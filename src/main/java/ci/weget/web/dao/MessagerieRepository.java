package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.messagerie.Messagerie;

@Repository
public interface MessagerieRepository extends JpaRepository<Messagerie, Long> {
	// ramener la messagerie d'un message par id Message
	@Query("select m from Messagerie m  where m.message.id=?1")
	Messagerie findMessagerieByIdMessage(long id);

	// ramener toutes les messagerie d'une personne par id personne
	@Query("select m from Messagerie m  where m.personne.id=?1")
	List<Messagerie> getMessagrieByIdPersonne(long id);

	// ramener tous les messages recus
	@Query("select m from Messagerie m  where m.message.typeMessage=false")
	List<Messagerie> findMessagesEnvoyeParMessagerie();

	// ramener tous les messages transferes
	@Query("select m from Messagerie m  where m.message.typeMessage=true")
	List<Messagerie> findMessagesTransfererParMessagerie();

}

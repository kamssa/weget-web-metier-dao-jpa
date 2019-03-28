package ci.weget.web.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface IAbonnementMetier extends Imetier<Abonnement, Long> {

	List<Abonnement> getAbonnementByIdEspace(long id);

	List<Abonnement> getAbonnementByIdPersonne(long id);

	List<Abonnement> getAbonnementByLogin(String login);

	Abonnement updateVue(Long id) throws InvalideTogetException;

	List<Abonnement> getAbonnementByVille(String ville);

	List<Abonnement> abonnementSpecial();

	List<Personne> uneOcurrenceAbonne();

	Abonnement statutAbonnement(long id);

	List<Abonnement> abonnementByMois(LocalDate mois);

	List<Abonnement> abonnementBani();

	boolean creerAbonne(Personne personne) throws InvalideTogetException;

	List<Abonnement> getAbonnementByParam(String type, String specialite, String ville, double latitude,
			double longitude, boolean proximite);

	Abonnement createImageAbonnement(MultipartFile file, Long id) throws IllegalStateException, IOException;

	byte[] getImageAbonnement(Long version, Long id, String libelle) throws FileNotFoundException, IOException;
}

package ci.weget.web.modele.metier;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.EcoleRepository;

import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.entites.Adresse;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.TarifAbonnement;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.ecole.Chiffre;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.entites.ecole.Temoignage;
import ci.weget.web.entites.ecole.TypeEtablissement;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.ITarifAbonnementMetier;

@Service
public class CreerReAbonne implements ICreeReAbonne {

	@Override
	public void creerReAbonnement(Long idAbonnement) throws ParseException, InvalideTogetException {
		// TODO Auto-generated method stub
		
	}

	/*@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private DetailAbonnementRepository detailAbonnement;
	@Autowired
	private MembreRepository membreRepository;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private ITarifAbonnementMetier tarifAbonnementMetier;

	
	public void creerReAbonnement(Long idAbonnement) throws ParseException, InvalideTogetException {
		Abonnement abs = abonnementRepository.findAbonneParId(idAbonnement);

		List<Panier> paniers = panierRepository.findAllPanierParPersonneId(abs.getIdPersone());
		for (Panier panier : paniers) {
			Block block = panier.getBlock();
			Tarif tarif = panier.getTarif();
			// Personne p = panier.getPersonne();
			int dureeAbonnement = tarif.getDureeTarif();
			int dureeAbonnementSpecial = tarif.getDureeSpecial();
			LocalDateTime currentTime = LocalDateTime.now();
			if (panier.isAbonneSpecial() == false) {
				if (block.getTypeBlock().equals("ecole")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
				}

				if (block.getTypeBlock().equals("competence")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnement = tarif.getDureeTarif();
						currentTime = LocalDateTime.now();

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						abs.setDateExpire(currentTime.plusDays(dureeAbonnement));
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);


					}
				}

			}
			if (panier.isAbonneSpecial() == true) {
				if (block.getTypeBlock().equals("ecole")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnementSpecial = tarif.getDureeSpecial();
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnementSpecial = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);


					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
				}

				if (block.getTypeBlock().equals("competence")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnement = tarif.getDureeTarif();
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);


					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);

					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						abs.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						abs.setAbonneSpecial(true);
						abs.setActive(true);

						Abonnement abonne = abonnementRepository.save(abs);
						panierRepository.deleteAll(paniers);


					}
				}

			}

		}

	}*/
}

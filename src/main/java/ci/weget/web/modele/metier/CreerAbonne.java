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
import ci.weget.web.metier.IEcoleMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.ITarifAbonnementMetier;

@Service
public class CreerAbonne implements ICreeAbonne {

	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private IEcoleMetier detailAbonnement;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private ITarifAbonnementMetier tarifAbonnementMetier;

	private Personne updatePersonne(Personne personne) {
		try {
			personnesRepository.save(personne);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return personne;
	}

	public void creerUnAbonne(Personne personne) throws ParseException, InvalideTogetException {
		List<Panier> paniers = panierRepository.findPaniersByPersonne(personne.getId());
		for (Panier panier : paniers) {
			Tarif tarif = panier.getTarif();
			Personne p = panier.getPersonne();
			Espace espace = tarif.getEspace();
			int dureeAbonnement = tarif.getDureeTarif();
			int dureeAbonnementSpecial = tarif.getDureeSpecial();
			LocalDateTime currentTime = LocalDateTime.now();
			if (panier.isAbonneSpecial() == false) {
				if (espace.getTypeEspace().equals("ecole")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						Ecole ecole = new Ecole();
						ecole.setAbonnement(abonne);

						Adresse a = new Adresse();
						a.setLatitude(0d);
						a.setLongitude(0d);
						ecole.setAdresse(a);
                        ecole.setNom("Vous devez renseigner le nom de votre ecole");
						ecole.setDescription("Cet abonnement concerne les ecoles");
						

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();
						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);

					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();
						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);
						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);

					}
				}

				if (espace.getTypeEspace().equals("competence")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnement = tarif.getDureeTarif();
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();
						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);
						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();
						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);
						ab.setActive(true);

						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);
					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();
						ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
						ab.setMembre((Membre) p);
						ab.setEspace(espace);
						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);

					}
				}

			}
			if (panier.isAbonneSpecial() == true) {
				if (espace.getTypeEspace().equals("ecole")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnementSpecial = tarif.getDureeSpecial();
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnementSpecial = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);

					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);

					}
				}

				if (espace.getTypeEspace().equals("competence")) {
					if (tarif.getTypeDuree().equals("JOURS")) {

						dureeAbonnement = tarif.getDureeTarif();
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);

					}
					if (tarif.getTypeDuree().equals("MOIS")) {

						dureeAbonnement = tarif.getDureeTarif() * 30;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						ab.setActive(true);

						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);
					}
					if (tarif.getTypeDuree().equals("ANNEE")) {

						dureeAbonnement = tarif.getDureeTarif() * 30 * 12;
						currentTime = LocalDateTime.now();

						Role userRole = roleRepository.findByName(RoleName.ABONNE).get();

						p.setRoles(Collections.singleton(userRole));

						Abonnement ab = new Abonnement();

						ab.setDateExpireAbonneSpecial(currentTime.plusDays(dureeAbonnementSpecial));
						ab.setAbonneSpecial(true);
						ab.setMembre((Membre) p);
						ab.setEspace(espace);

						ab.setActive(true);
						Abonnement abonne = abonnementRepository.save(ab);
						TarifAbonnement tabonnement = new TarifAbonnement();
						tabonnement.setAbonnement(abonne);
						tabonnement.setTarif(tarif);
						tarifAbonnementMetier.creer(tabonnement);
						System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);

					}
				}

			}

		}

	}
}

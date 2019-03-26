package ci.weget.web.modeles;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.DetailAbonnementRepository;
import ci.weget.web.dao.MembreRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Adresse;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Chiffre;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Partenaire;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Role;
import ci.weget.web.entites.RoleName;
import ci.weget.web.entites.Tarif;
import ci.weget.web.entites.Temoignage;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IMembreMetier;

@Service
public class CreerAbonneGratuit implements ICreeAbonneGratuit {

	@Autowired
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

	private Personne updatePersonne(Personne personne) {
		try {
			personnesRepository.save(personne);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return personne;
	}

	
	public void creerUnAbonneGratuit(Personne personne) throws ParseException, InvalideTogetException {
		List<Panier> paniers = panierRepository.findAllPanierParPersonneId(personne.getId());
		for (Panier panier : paniers) {
			Block block = panier.getBlock();
			Tarif tarif = panier.getTarif();
			Personne p = panier.getPersonne();

			if (tarif.isFree()==true) {
				 if (block.getTypeBlock().equals("ecole")) {
					 
					int dureeAbonnement = tarif.getDureeTarif();
					LocalDateTime currentTime = LocalDateTime.now();

					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir ecole mois:" + p);

					Role userRole = roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir role ecole mois" + userRole);

					p.setRoles(Collections.singleton(userRole));
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir save passe pas");
					Personne personne1 = personnesRepository.getPersonneByid(p.getId());

					Membre m = personnesRepository.getMembreByid(p.getId());

					Abonnement ab = new Abonnement();
					ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
					ab.setMembre(m);
					ab.setBlock(block);
					ab.setActive(true);
					Abonnement abonne = abonnementRepository.save(ab);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);
					DetailAbonnement da = new DetailAbonnement();
					da.setAbonnement(abonne);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir detail abonnement setAbonnement" + da);

					da.setIdBlock(block.getId());
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir detail abonnement setIdBlock apres ajout" + da);
					Adresse a = new Adresse();
					a.setLatitude(0d);
					a.setLongitude(0d);
					da.setAdresse(a);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir detail abonnement setLatitude apres ajout" + da);

					
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir detail abonnement setLongitude apres ajout" + da);

					da.setNom("Vous devez renseigner le nom de votre ecole");
					da.setDescription("Cet abonnement concerne les ecoles");
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir detail abonnement apres ajout" + da);

					
					
					System.out.println("detailllllllllllllllllllllllllllllllllllllllllll");
					Chiffre c = new Chiffre();
					List<Chiffre> chiffre = new ArrayList<>();
					chiffre.add(c);
					Temoignage t = new Temoignage();
					List<Temoignage> temoignage = new ArrayList<>();
					temoignage.add(t);
					Partenaire pa = new Partenaire();
					List<Partenaire> partenaire = new ArrayList<>();
					partenaire.add(pa);
					da.setChiffre(chiffre);
					da.setPartenaire(partenaire);
					da.setTemoignage(temoignage);
					detailAbonnement.save(da);
		            System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer abonne");

				}
			}
			
			 if (tarif.isFree()==true) {
				  if (block.getTypeBlock().equals("competence")) {
					  
				  int dureeAbonnement = tarif.getDureeTarif();
					LocalDateTime currentTime = LocalDateTime.now();

					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir ecole mois:" + p);

					Role userRole = roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir role ecole mois" + userRole);

					p.setRoles(Collections.singleton(userRole));
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir save passe pas");
					Personne personne1 = personnesRepository.getPersonneByid(p.getId());

					Membre m = personnesRepository.getMembreByid(p.getId());

					Abonnement ab = new Abonnement();
					ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
					ab.setMembre(m);
					ab.setBlock(block);
					ab.setActive(true);
					ab.setFree(true);
					Abonnement abonne = abonnementRepository.save(ab);
					System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir  abonnement " + abonne);
			  
			  }
			  } 
			 /*
			 if (block.getTypeBlock().equals("immobilier")) { if
			 * (tarif.getTypeDuree().equals("JOURS")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
			 * 
			 * ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } if (tarif.getTypeDuree().equals("MOIS")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree * 30; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
			 * ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } if (tarif.getTypeDuree().equals("ANNEE")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree * 30 * 12; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
			 * ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } } if (block.getTypeBlock().equals("annonce")) { if
			 * (tarif.getTypeDuree().equals("JOURS")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
			 * 
			 * // ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } if (tarif.getTypeDuree().equals("MOIS")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree * 30; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement)); //
			 * ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } if (tarif.getTypeDuree().equals("ANNEE")) {
			 * 
			 * int durree = tarif.getDureeTarif();
			 * 
			 * int dureeAbonnement = durree * 30 * 12; LocalDateTime currentTime =
			 * LocalDateTime.now();
			 * 
			 * Abonnement ab = new Abonnement();
			 * 
			 * ab.setDateExpire(currentTime.plusDays(dureeAbonnement)); //
			 * ab.setAbonneSpecial(true); ab.setBlock(block);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Membre membre =
			 * personnesRepository.getMembreByid(id);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); Role userRole =
			 * roleRepository.getUserRoleParName(RoleName.ROLE_ABONNE);
			 * membre.setRoles(Collections.singleton(userRole));
			 * System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); //Personne leMembre =
			 * personnesRepository.save(membre);
			 * //System.out.println("voiiiiiiiiiiiiiiiiiiiiiiiiiir"); ab.setMembre((Membre)
			 * membre);
			 * System.out.println("verifieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
			 * ab.setActive(true); abonnementRepository.save(ab);
			 * 
			 * } }
			 */
		}

	}
}
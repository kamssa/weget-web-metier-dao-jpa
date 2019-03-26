package ci.weget.web.modele.metier;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.TarifAbonnement;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IRoleMetier;
import ci.weget.web.metier.ITarifAbonnementMetier;

@Service
public class CreerAbonneGratuit implements ICreeAbonneGratuit {

	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private IRoleMetier roleMetier;
	
	@Autowired
	private ITarifAbonnementMetier tarifAbonnementMetier;
	
	public void creerUnAbonneGratuit(Personne personne) throws ParseException, InvalideTogetException {
		List<Panier> paniers = panierRepository.findPaniersByPersonne(personne.getId());
		for (Panier panier : paniers) {
           // on recupere l tarif
			Tarif tarif = panier.getTarif();
			// on recupere l'espace
			Espace espace = tarif.getEspace();
			// on recupere la personne
			Personne p = panier.getPersonne();

			if (tarif.isFree() == true) {
				if (espace.getTypeEspace().equals("competence")) {

					int dureeAbonnement = tarif.getDureeTarif();
					LocalDateTime currentTime = LocalDateTime.now();
                    // on recupere le role abonne
					Role userRole = roleMetier.findByRoleName(RoleName.ABONNE);
                    // on met a jour le role 
					p.setRoles(Collections.singleton(userRole));
                    // on instancie un abonnement
					Abonnement ab = new Abonnement();
					// inserer les element de abonnement
					ab.setDateExpire(currentTime.plusDays(dureeAbonnement));
					ab.setMembre((Membre) p);
					ab.setEspace(espace);
					
					ab.setFree(true);
					//save abonnement
					Abonnement abonne = abonnementRepository.save(ab);
					System.out.println(">>>>>>>>>>>>>> voir le role de la personne"+"/"+p);
                    // enregistre des info pour la vue
					TarifAbonnement tabonnement = new TarifAbonnement();
					tabonnement.setAbonnement(abonne);
					tabonnement.setTarif(tarif);
					tarifAbonnementMetier.creer(tabonnement);
					// suppression du panier apres enregistrement
					panierRepository.deleteAll(paniers);
				}
			}

		}

	}
}
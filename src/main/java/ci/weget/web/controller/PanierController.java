package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.metier.ILigneCommandeMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.IPanierMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modele.metier.ICreeAbonneGratuit;
import ci.weget.web.modeles.AjoutPanier;
import ci.weget.web.modeles.ModifPanier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class PanierController {

	@Autowired
	private IPanierMetier panierMetier;
	@Autowired
	private ITarifMetier tarifMetier;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private IEspaceMetier espaceMetier;
	@Autowired
	private ILigneCommandeMetier ligneCommandeMetier;
	@Autowired
	private ICreeAbonneGratuit creeAbonneGratuit;
	@Autowired
	private IAbonnementMetier abonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<Panier> getPanierById(final Long id) {
		Panier panier = null;
		try {
			panier = panierMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Panier>(1, Static.getErreursForException(e), null);
		}
		if (panier == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le panier n'exste pas", id));
			return new Reponse<Panier>(2, messages, null);
		}
		return new Reponse<Panier>(0, null, panier);

	}

	private Reponse<Personne> getMembreById(final long id) {
		Personne personne = null;
		try {
			personne = membreMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	private Reponse<Personne> getMembreByLogin(final String login) {
		Personne personne = null;
		try {
			personne =	 membreMetier.findByLogin(login);
		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		} catch (InvalideTogetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	// recuprer le block a partir de son identifiant
	private Reponse<Espace> getEspace(final long id) {
		// on récupère le block
		Espace block = null;
		try {
			block = espaceMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Espace>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Espace>(2, messages, null);
		}
		// ok
		return new Reponse<Espace>(0, null, block);
	}

	// recuprer le Tarif a partir de son identifiant
	private Reponse<Tarif> getTarif(final long id) {
		// on récupère le tarif
		Tarif tarif = null;
		try {
			tarif = tarifMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Tarif>(1, Static.getErreursForException(e1), null);
		}
		// tarif existant ?
		if (tarif == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le tarif n'exste pas", id));
			return new Reponse<Tarif>(2, messages, null);
		}
		// ok
		return new Reponse<Tarif>(0, null, tarif);
	}

	@PostMapping("/panier")
	public String creer(@RequestBody AjoutPanier post) throws JsonProcessingException {


		Reponse<Boolean> reponse = null;
		Double total=null;
		
		
		// on recupere le reponse tarif
		Reponse<Tarif> reponseTarif = getTarif(post.getIdTarif());
		// on extrait le tarif
		Tarif tarif = (Tarif) reponseTarif.getBody();
		// on récupère  reponse block
		Reponse<Espace> reponseEspace = getEspace(tarif.getIdEspace());
		Espace espace = reponseEspace.getBody();
		Reponse<Personne> reponsePersonne = getMembreById(post.getIdMembre());
    	Personne personne = (Personne) reponsePersonne.getBody();
    	boolean abonneSpecial= post.isAbonneSpecial();
        
		Double quantite=null;
        quantite=post.getQuantite();
        double  nbreJours=post.getNbresJours();
        if (abonneSpecial==false) {
    		total = tarif.getPrix()* quantite;

		}if (abonneSpecial==true) {
    		total = (tarif.getPrix()+(tarif.getPrixSpecial()*post.getNbresJours()))* quantite;

		} 
          try {
			boolean boo = panierMetier.ajoutLignePanier(tarif,espace,personne, quantite, total,abonneSpecial,nbreJours);
			boolean bool = ligneCommandeMetier.ajoutLigneCommande(personne,espace, quantite, total,abonneSpecial,nbreJours);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("element ajouter au panier avec succes"));
			reponse = new Reponse<Boolean>(0, messages, boo);
			
		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}

        

		// on récupère la personne reponse personne

       // on récupère le tarif reponse tarif

		return jsonMapper.writeValueAsString(reponse);
	}
	/*@PostMapping("/panierReabonne")
	public String reabonnement(@RequestBody AjoutPanier post) throws JsonProcessingException {


		Reponse<Boolean> reponse = null;
		Double total=null;
		
		Abonnement ab=abonnementMetier.findById(post.getIdbonnement());
		// on recupere le reponse tarif
		Reponse<Tarif> reponseTarif = getTarif(post.getIdTarif());
		// on extrait le tarif
		Tarif tarif = (Tarif) reponseTarif.getBody();
		// on récupère  reponse block
		Reponse<Block> reponseBlock = getBlock(tarif.getBlock().getId());
		Block block = (Block) reponseBlock.getBody();
		Reponse<Personne> reponsePersonne = getMembreById(post.getIdMembre());
    	Personne personne = (Personne) reponsePersonne.getBody();
    	boolean abonneSpecial= post.isAbonneSpecial();
        
		Double quantite=null;
        quantite=post.getQuantite();
        double  nbreJours=post.getNbresJours();
        if (abonneSpecial==false) {
    		total = tarif.getPrix()* quantite;

		}if (abonneSpecial==true) {
    		total = (tarif.getPrix()+(tarif.getPrixSpecial()*post.getNbresJours()))* quantite;

		} 
          try {
			boolean boo = panierMetier.ajoutLignePanier(tarif, block, personne, quantite, total,abonneSpecial,nbreJours);
			boolean bool = ligneCommandeMetier.ajoutLigneCommande(block, personne, quantite, total,abonneSpecial,nbreJours);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("element ajouter au panier avec succes"));
			reponse = new Reponse<Boolean>(0, messages, boo);
			
		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}

        

		// on récupère la personne reponse personne

       // on récupère le tarif reponse tarif

		return jsonMapper.writeValueAsString(reponse);
	}*/
	// modif d'un panier dans la base de donnee
	/*@PutMapping("/panier")
	public String modfierUnPanier(@RequestBody ModifPanier modif) throws JsonProcessingException {

		Reponse<Boolean> reponse;
		Reponse<Panier> reponsePanier;
		Double total=null;
		Double totalSpecial=null;

		long idTarif = modif.getIdTarif();
		long idMembre = modif.getIdMembre();
		// on récupère le tarif reponse tarif
		Reponse<Tarif> reponseTarif = getTarif(idTarif);
		// on recupere le tarif
		Tarif tarif = (Tarif) reponseTarif.getBody();
		// on récupère le block reponse block
		Reponse<Block> reponseBlock = getBlock(tarif.getBlock().getId());
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();

		// on récupère la personne reponse personne
		Reponse<Personne> reponsePersonne = getMembreById(idMembre);
		// on recupere le block
		Personne personne = (Personne) reponsePersonne.getBody();
		Double quantite = modif.getQuantite();
    	boolean abonneSpecial= modif.isAbonneSpecial();
    	double nbreJours=modif.getNbreJours();

		if (abonneSpecial==false) {
		 total = tarif.getPrix() * modif.getQuantite();

		}if(abonneSpecial==true) {
    		total = (tarif.getPrix()+(tarif.getPrixSpecial())*modif.getNbreJours())* quantite;
	
		}

		try {
			
			boolean boo = panierMetier.modifLignePanier(modif.getId(), tarif, block, personne, quantite, total,abonneSpecial,nbreJours);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("element modifier avec succes"));
			reponse = new Reponse<Boolean>(0, messages, boo);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
*/
	// recuperer toutes les paniers de la base de donnee
	@GetMapping("/panier")
	public String findAllPanier() throws JsonProcessingException {
		Reponse<List<Panier>> reponse;
		try {
			List<Panier> paniers = panierMetier.findAll();
			reponse = new Reponse<List<Panier>>(0, null, paniers);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/*// renvoie les paniers d'une personne par son identifiant
	@GetMapping("/panierParPersonne/{idPersonne}")
	public String panierPanierParPrersonneId(@PathVariable("idPersonne") long idPersonne)
			throws JsonProcessingException {
		Reponse<List<Panier>> reponse;
		try {
			List<Panier> paniers = panierMetier.LesPanierDeLaPersonne(idPersonne);
			reponse = new Reponse<List<Panier>>(0, null, paniers);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
*/
	// renvoie un panier par son identifiant
	@GetMapping("/panier/{id}")
	public String chercherPanierParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Panier> reponse = null;
		reponse = getPanierById(id);
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/panier")
	public String supprimerAll(@RequestBody List<Panier> paniers) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;

		try {
			boolean boo = panierMetier.supprimer(paniers);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("pas de matiere enregistrer "));
			reponse = new Reponse<Boolean>(2, messages, true);

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/panier/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, panierMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
}

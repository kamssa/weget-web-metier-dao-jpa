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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.entites.commande.LigneCommande;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.ICommandeMetier;
import ci.weget.web.metier.ILigneCommandeMetier;
import ci.weget.web.metier.IPanierMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class CommandeController {

	@Autowired
	ICommandeMetier commandeMetier;
	@Autowired
	IAbonnementMetier detailBlocksMetier;
	@Autowired
	IPanierMetier panierMetier;
	@Autowired
	ILigneCommandeMetier ligneCommandeMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<Commande> getCommandeById(Long id) {
		Commande paiement = null;
		try {
			paiement = commandeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (paiement == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le paiement n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Commande>(0, null, paiement);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une commande dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/commande")
	public String creer(@RequestBody Personne pers,
			@RequestParam("code") String code) throws JsonProcessingException {
		Reponse<Commande> reponse = null;
		List<Panier> paniers = panierMetier.getPanierByIdPersonne(pers.getId());
		double montant = 0d;
		Personne personne = null;
		for (Panier panier : paniers) {
			montant += panier.getMontant();
			personne = panier.getPersonne();
			
		}
		
		try {
             
			Commande p = commandeMetier.creerCommande(personne, montant,code);
			
		List<LigneCommande>lg=	ligneCommandeMetier.findLigneCommandeParPersonneId(personne.getId());
			for(LigneCommande ligneC: lg) {
				ligneC.setCommande(p);
				ligneCommandeMetier.modifier(ligneC);
	             }
		  List<String> messages = new ArrayList<>();
			
			messages.add(String.format("%s  à été créer avec succes", p.getId()));
			reponse = new Reponse<Commande>(0, messages, p);
			
		} catch (InvalideTogetException e) {

			reponse = new Reponse<Commande>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un paiement dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/commande")
	public String modfier(@RequestBody Commande modif) throws JsonProcessingException {
		Reponse<Commande> reponsePersModif = null;
		Reponse<Commande> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getCommandeById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Commande p2 = commandeMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", p2.getId()));
				reponse = new Reponse<Commande>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Commande>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La commande n'existe pas"));
			reponse = new Reponse<Commande>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les paiements de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/commande")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Commande>> reponse;
		try {
			List<Commande> commande = commandeMetier.findAll();
			if (!commande.isEmpty()) {
				reponse = new Reponse<List<Commande>>(0, null, commande);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de commande trouve");
				reponse = new Reponse<List<Commande>>(1, messages, new ArrayList<>());
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Commande>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/getCommandeByIdPersonne/{id}")
	public String getByIdPersonne(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Commande> reponse;
		try {
		Commande commande = commandeMetier.getByIdPersonne(id);
			reponse = new Reponse<Commande>(0, null, commande);
		} catch (Exception e) {
			reponse = new Reponse<Commande>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/commande/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, commandeMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@DeleteMapping("/commande")
	public String supprimerAll(@RequestBody List<Commande> commandes) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;
		

		try {
			boolean boo = commandeMetier.supprimer(commandes);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("pas de commandes enregistrer "));
			reponse = new Reponse<Boolean>(2, messages, true);

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}

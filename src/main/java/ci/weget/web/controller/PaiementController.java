package ci.weget.web.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Paiement;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ICommandeMetier;
import ci.weget.web.metier.IPaiementMetier;
import ci.weget.web.modeles.ICreeAbonne;
import ci.weget.web.modeles.ICreeAbonneGratuit;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.modeles.ReponsePaiement;
import ci.weget.web.utilitaires.Static;;

@RestController
@CrossOrigin
public class PaiementController {
	@Autowired
	IPaiementMetier paiementMetier;
	@Autowired
	ICommandeMetier commandeMetier;
	@Autowired
	ICreeAbonne creeAbonne;
	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<Paiement> getPaiementById(Long id) {
		Paiement paiement = null;
		try {
			paiement = paiementMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (paiement == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le paiement n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Paiement>(0, null, paiement);
	}

	@PostMapping("/paiement")
	public String creer(@RequestBody Paiement paiement, @PathParam(value = "id") Long id)
			throws InvalideTogetException, IOException {
		Reponse<ResponseEntity<String>> reponse = null;
		Reponse<Paiement> reponsePaie = null;
		ReponsePaiement<Paiement, String> reponsePaiement = null;
		Reponse<String> reponseSignature = null;
		Reponse<List<String>> listString;
		Commande commande = commandeMetier.findById(id);
		Paiement paye = null;
		double montantCommande = commande.getMontant();
		int montant = (int) montantCommande;
		paiement.setCpm_amount(montant);
		paiement.setCommande(commande);
		paiement.setCpm_trans_id(commande.getId().intValue());

		try {
			String url = "https://api.cinetpay.com/v1/?method=getSignatureByPost";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<Object, Object> map = new LinkedMultiValueMap<Object, Object>();
			map.add("apikey", paiement.getApikey());
			map.add("cpm_amount", Integer.toString(paiement.getCpm_amount()));
			map.add("cpm_currency", paiement.getCpm_currency());
			map.add("cpm_custom", paiement.getCpm_custom());
			map.add("cpm_designation", paiement.getCpm_designation());
			map.add("cpm_language", paiement.getCpm_language());
			map.add("cpm_page_action", paiement.getCpm_page_action());
			map.add("cpm_payment_config", paiement.getCpm_payment_config());
			map.add("cpm_site_id", Integer.toString(paiement.getCpm_site_id()));
			map.add("cpm_trans_date", paiement.getCpm_trans_date());
			map.add("cpm_trans_id",Integer.toString(paiement.getCpm_trans_id()));
			map.add("cpm_version", paiement.getCpm_version());
			//.add("notify_url", paiement.getNotify_url());

			HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<MultiValueMap<Object, Object>>(map,
					headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			paye = paiementMetier.creer(paiement);
			Paiement retour= new Paiement();
			retour.setCpm_amount(paye.getCpm_amount());
			retour.setCpm_trans_date(paye.getCpm_trans_date());
			retour.setCpm_trans_id(paye.getCpm_trans_id());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s %s", paye.getCpm_trans_date(),paye.getCpm_trans_id(),paye.getCpm_amount()));
			String signature = response.getBody();

			reponseSignature = new Reponse<String>(0, messages, signature);
			reponsePaie = new Reponse<Paiement>(0, messages, paye);
			paye.getCommande().setPaye(false);
		   //Commande com=	paye.getCommande();
		   Paiement pa=	paiementMetier.modifier(paye);
		  reponsePaie = new Reponse<Paiement>(0, messages, paye);
			reponse = new Reponse<ResponseEntity<String>>(0,null, response);
			
			reponsePaiement = new ReponsePaiement<Paiement, String>(0,null, retour, signature);
			//creeAbonne.creerUnAbonne(commande.getPersonne());
		} catch (Exception e) {
			reponse = new Reponse<ResponseEntity<String>>(1, Static.getErreursForException(e), null);
		}
        
		return jsonMapper.writeValueAsString(reponsePaiement);
		
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un paiement dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/paiement")
	public String modfier(@RequestBody Paiement modif) throws JsonProcessingException {
		Reponse<Paiement> reponsePersModif = null;
		Reponse<Paiement> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getPaiementById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Paiement p2 = paiementMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", p2.getId()));
				reponse = new Reponse<Paiement>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Paiement>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le paiement n'existe pas"));
			reponse = new Reponse<Paiement>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

}

package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.abonnement.TarifAbonnement;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.ITarifAbonnementMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class TarifAbonnementController {
	@Autowired
	private ITarifAbonnementMetier tariAbonnementMetier;

	@Autowired
	private IAbonnementMetier abonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	
	// ajouter un block
	@PostMapping("/tarifAbonnemnt")
	public String creer(@RequestBody TarifAbonnement tarifAbonnement) throws JsonProcessingException {
		Reponse<TarifAbonnement> reponse;

		try {

			TarifAbonnement t1 = tariAbonnementMetier.creer(tarifAbonnement);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<TarifAbonnement>(0, messages, t1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<TarifAbonnement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// modifier un block
	@PutMapping("/tarifAbonnement")
	public String modifier(@RequestBody TarifAbonnement tarifAbonnement) throws JsonProcessingException {
		Reponse<TarifAbonnement> reponse;

		try {

			TarifAbonnement t1 = tariAbonnementMetier.modifier(tarifAbonnement);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<TarifAbonnement>(0, messages, t1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<TarifAbonnement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/tarifAbonnement")
	public String obtenirTariParIdBlock() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<TarifAbonnement>> reponse;
		try {
			List<TarifAbonnement> tarifs = tariAbonnementMetier.findAll();
			reponse = new Reponse<List<TarifAbonnement>>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/tarifParIdAbonnement/{id}")
	public String obtenirTariParIdAbonnement(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<TarifAbonnement> reponse;
		try {
			TarifAbonnement tarifs = tariAbonnementMetier.tarifParIdAbonnement(id);
			reponse = new Reponse<TarifAbonnement>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}

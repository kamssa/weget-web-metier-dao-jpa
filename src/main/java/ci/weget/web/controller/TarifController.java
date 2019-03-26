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

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class TarifController {

	@Autowired
	private ITarifMetier tarifMetier;

	@Autowired
	private IAbonnementMetier abonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	@PostMapping("/tarif")
	public String ajout(@RequestBody Tarif tarif, @RequestBody Espace block) throws JsonProcessingException {
		Reponse<Tarif> reponse;

		try {

			Tarif t = tarifMetier.ajouterEspace(tarif, block);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t.getPrix()));
			reponse = new Reponse<Tarif>(0, messages, t);

		} catch (Exception e) {

			reponse = new Reponse<Tarif>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// modifier un block
	@PutMapping("/tarif")
	public String modifier(@RequestBody Tarif tarif) throws JsonProcessingException {
		Reponse<Tarif> reponse;

		try {

			Tarif t1 = tarifMetier.modifier(tarif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getPrix()));
			reponse = new Reponse<Tarif>(0, messages, t1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Tarif>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/getTarifByIdEspace/{idEspace}")
	public String obtenirTariParIdBlock(@PathVariable("idEspace") long idEspace) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Tarif>> reponse;
		try {
			List<Tarif> tarifs = tarifMetier.getTarifByIdEspace(idEspace);
			reponse = new Reponse<List<Tarif>>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/getTarifByIdAbonnemnt/{id}")
	public String obtenirTariParIdAbonnement(@PathVariable Long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Tarif>> reponse;
		try {
			Abonnement ab = abonnementMetier.findById(id);
			Espace b = ab.getEspace();
			List<Tarif> tarifs = tarifMetier.getTarifByIdEspace(b.getId());
			reponse = new Reponse<List<Tarif>>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}

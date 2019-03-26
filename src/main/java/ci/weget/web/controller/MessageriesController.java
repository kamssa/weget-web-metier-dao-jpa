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

import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.messagerie.Message;
import ci.weget.web.entites.messagerie.Messagerie;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ImessagerieMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class MessageriesController {
	@Autowired
	private ImessagerieMetier messagerieMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuprer la messagerie a partir de son identifiant
		private Reponse<Messagerie> getMessagerieById(long id) {
			
			Messagerie messagerie = null;
			try {
				messagerie = messagerieMetier.findById(id);
			} catch (Exception e1) {
				return new Reponse<Messagerie>(1, Static.getErreursForException(e1), null);
			}
			// messagerie existant ?
			if (messagerie == null) {
				List<String> messages = new ArrayList<String>();
				messages.add(String.format("La messagerie n'exste pas", id));
				return new Reponse<Messagerie>(2, messages, null);
			}
			// ok
			return new Reponse<Messagerie>(0, null, messagerie);
		}
	
	@PostMapping("/messagerie")
	public String creerMessagerie(@RequestBody Messagerie msg) throws JsonProcessingException {
		Reponse<Messagerie> reponse;

		try {
              
			Messagerie m1 = messagerieMetier.creer(msg);
			
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", m1.getId()));
			reponse = new Reponse<Messagerie>(0, messages, m1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Messagerie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	
	@PostMapping("/sendMessage")
	public String envoyeMessage(@RequestBody Messagerie entity) throws InvalideTogetException, JsonProcessingException {
		Reponse<Boolean> reponse;

		try {

			messagerieMetier.sendEmail(entity);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" envoye avec succes"));
			reponse = new Reponse<Boolean>(0, messages,true);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PostMapping("/transferMessage")
	public String messageTransfere(@RequestBody Messagerie entity) throws InvalideTogetException, JsonProcessingException {
		Reponse<Boolean> reponse;

		try {

			messagerieMetier.transfertEmail(entity);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" envoye avec succes"));
			reponse = new Reponse<Boolean>(0, messages,true);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/updateStatutMessage")
	public String modifierStatutMessage(@RequestBody Messagerie entity) throws InvalideTogetException, JsonProcessingException {
		Reponse<Messagerie> reponse;

		try {

			Messagerie m1 = messagerieMetier.updateStautMessage(entity);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", m1.getId()));
			reponse = new Reponse<Messagerie>(0, messages, m1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Messagerie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/getMessageByPersonne/{idPersonne}")
	public String findAllMessagerisParPersonne(@PathVariable("idPersonne") long idPersonne) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Messagerie>> reponse;
		try {
			List<Messagerie> messages = messagerieMetier.findMessagerieByIdPersonneId(idPersonne);
			reponse = new Reponse<List<Messagerie>>(0, null, messages);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/sendMessage")
	public String findAllMessagesEnvoyer() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Messagerie>> reponse;
		try {
			List<Messagerie> messages = messagerieMetier.findMessagesEnvoyeParMessagerie();
			reponse = new Reponse<List<Messagerie>>(0, null, messages);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/tranferMessage")
	public String findAllMessageTransferer() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Messagerie>> reponse;
		try {
			List<Messagerie> messages = messagerieMetier.findMessagesTransfertParMessagerie();
			reponse = new Reponse<List<Messagerie>>(0, null, messages);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/messagerie/{id}")
	public String findMessageParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Messagerie> reponse = null;

		reponse = getMessagerieById(id);

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/messagerie")
	public String findAllMessageAdmin() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Messagerie>> reponse;
		try {
			List<Messagerie> messageries = messagerieMetier.findAll();
			reponse = new Reponse<List<Messagerie>>(0, null, messageries);
		} catch (Exception e) {
			reponse = new Reponse<List<Messagerie>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	
	
}

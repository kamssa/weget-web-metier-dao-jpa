package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.FlashInfo;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IFlashInfoMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class FlahInfoController {
	@Autowired
	private IFlashInfoMetier flashInfoMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<FlashInfo> getFlashInfoById(Long id) {
		FlashInfo flash = null;
		
		try {
			flash = flashInfoMetier.findById(id);
			if (flash == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("le flash info n'existe pas", id));
				new Reponse<FlashInfo>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<FlashInfo>(1, Static.getErreursForException(e), null);
		}
		
		return new Reponse<FlashInfo>(0, null, flash);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un flash info dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/flashInfo")
	public String creer(@RequestBody FlashInfo flash) throws JsonProcessingException {
		Reponse<FlashInfo> reponse;

		try {

			FlashInfo f1 = flashInfoMetier.creer(flash);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", f1.getId()));
			reponse = new Reponse<FlashInfo>(0, messages, f1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<FlashInfo>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un flash info dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	
	@PutMapping("/flashInfo")
	public String modfierUnBlock(@RequestBody FlashInfo modif) throws JsonProcessingException {
		Reponse<FlashInfo> reponsePersModif = null;
		Reponse<FlashInfo> reponse = null;

		// on recupere le flash info a modifier
		reponsePersModif = getFlashInfoById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				FlashInfo f2 = flashInfoMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", f2.getId()));
				reponse = new Reponse<FlashInfo>(0, messages, f2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<FlashInfo>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<FlashInfo>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les flash info de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/flashInfo")
	public String findAllFlashInfo() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<FlashInfo>> reponse;
		try {
			List<FlashInfo> flashs = flashInfoMetier.findAll();
			
			if (!flashs.isEmpty()) {
				reponse = new Reponse<List<FlashInfo>>(0, null, flashs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de flash info enregistrées");
				reponse = new Reponse<List<FlashInfo>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<FlashInfo>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	////////recuperer flash info par son id
	@GetMapping("/flashInfo/{id}")
	public String chercherFlashInfoParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<FlashInfo> reponse = null;

		reponse = getFlashInfoById(id);
		if (reponse.getBody()==null) {
			throw new RuntimeException("pas d'enregistrement pour ce sous block");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer tous les flash info 
		/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/flashInfoDetailAbonnement/{id}")
		public String findAllFlashInfoByIdSousBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
			Reponse<List<FlashInfo>> reponse;
			try {
				List<FlashInfo> flashs = flashInfoMetier.findAllFlashInfoParIdSousBlock(id);
				reponse = new Reponse<List<FlashInfo>>(0, null, flashs);
				if (reponse.getBody().isEmpty()) {
					throw new RuntimeException("pas de flash info trouve");
				}
			} catch (Exception e) {
				reponse = new Reponse<List<FlashInfo>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@DeleteMapping("/flashInfo/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s  a ete supprime", true));

				reponse = new Reponse<Boolean>(0, messages, flashInfoMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
}

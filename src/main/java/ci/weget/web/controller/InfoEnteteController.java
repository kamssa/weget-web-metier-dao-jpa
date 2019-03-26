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

import ci.weget.web.entites.InfoEntete;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IinfoEnteteMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class InfoEnteteController  {
	@Autowired
	private IinfoEnteteMetier infoEnteteMetier;

	@Autowired
	private ObjectMapper jsonMapper;
	
	
	@PostMapping("/infoEntete")
	public String creer(@RequestBody InfoEntete entite) throws JsonProcessingException {
		Reponse<InfoEntete> reponse;
		try {
			InfoEntete info = infoEnteteMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", info.getLibelle()));
			reponse = new Reponse<InfoEntete>(0, messages, info);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<InfoEntete>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/infoEntete")
	public String modifier(@RequestBody InfoEntete entite) throws JsonProcessingException {
		Reponse<InfoEntete> reponse;
		try {
			InfoEntete info = infoEnteteMetier.modifier(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", info.getLibelle()));
			reponse = new Reponse<InfoEntete>(0, messages, info);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<InfoEntete>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	
	@GetMapping("/infoEntete")
	public String findAllInfoEntete() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<InfoEntete>> reponse;
		try {
			List<InfoEntete> mats = infoEnteteMetier.findAll();
			reponse = new Reponse<List<InfoEntete>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<InfoEntete>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/infoEntete/{id}")
	public String findAllIdInfoEntete(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<InfoEntete> reponse;
		try {
			InfoEntete mats = infoEnteteMetier.findById(id);
			reponse = new Reponse<InfoEntete>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<InfoEntete>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}

package ci.weget.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.JwtTokenProvider;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class MembreController {

	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

////////////////////////////////////////////////////////////////
//////////////// Rechercher une personne a partir de son identifiant///////////


	@PostMapping("/membre")
	public String creer(@RequestBody Personne entite) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne p1 = membreMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personne>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/membre")
	public String modifier(@RequestBody Personne modif) throws JsonProcessingException {
		Reponse<Personne> reponse = null;

		try {
			Personne p2 = membreMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s a modifier avec succes", p2.getNom(), p2.getPrenom()));
			reponse = new Reponse<Personne>(0, messages, p2);
		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	
	// obtenir la liste des membres
	@GetMapping("/membre")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Personne>> reponse;

		try {
			List<Personne> personneTous = membreMetier.findAll();
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<Personne>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<Personne>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les membres par login
	@GetMapping("/getByLogin/{login}")
	public String membreParLogin(@PathVariable("login") String login) throws JsonProcessingException {

		Reponse<Personne> reponse;

		try {

			Personne p = membreMetier.findByLogin(login);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les membres par id
	@GetMapping("/membre/{id}")
	public String getById(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Personne> reponse;

		try {

			Personne p = membreMetier.findById(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// recherche les membres par id
		@GetMapping("/getMembre/{loginOrTelephone}")
		public String getByLoginOrTelephone(@PathVariable("loginOrTelephone") String loginOrTelephone) throws JsonProcessingException {

			Reponse<Personne> reponse;

			try {

				Optional<Personne> p = personnesRepository.findByLoginOrTelephone(loginOrTelephone, loginOrTelephone);
				List<String> messages = new ArrayList<>();
				messages.add(String.format(" à été créer avec succes"));
				reponse = new Reponse<Personne>(0, messages, p.get());

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	
	
	@DeleteMapping("/membre/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, membreMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer la photo d'un membre 
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/imageMembre")
	public String createPhoto(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<Personne> reponse;

		try {

			Personne p = membreMetier.createImageMembre(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////// enregistrer la photo de couverure dans la
	///////////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////
	@PostMapping("/imageCouvertureMembre")
	public String creerPhotoCouverture(@RequestParam(name = "image_photo") MultipartFile file,
			@RequestParam Long id) throws Exception {
		
		Reponse<Personne> reponse;

		try {

			Personne p = membreMetier.createImageCouvertureMembre(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	///////////////////////////////////////////////////////////////////////////////
	//// recuperer photo d' membre dans la base /////////////////////////////////
	@GetMapping(value = "/getImageMembre/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosMembre(@PathVariable Long version, @PathVariable Long id,@PathVariable String  libelle)
			throws FileNotFoundException, IOException {
		byte[] img=null;
		img= membreMetier.getImageMembre(version, id,libelle);
		return img;
		
		
	}
	//////// recuperer la photo de couverture pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getImageCouvertureMembre/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable Long version, @PathVariable Long id,@PathVariable String libelle)
			throws FileNotFoundException, IOException {
		byte[] img=null;
		img= membreMetier.getImageCouvertureMembre(version, id, libelle);
		return img;
	}

}

package ci.weget.web.controller.publicite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import ci.weget.web.entites.abonnement.Gallery;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.publicite.Publicite;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.publicite.IPubliciteMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class PubliciteController {
	@Autowired
	private IPubliciteMetier publiciteMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Publicite> getPubliciteById(final long id) {
		Publicite publicite = null;
		try {
			publicite = publiciteMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (publicite == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Publicite>(2, messages, null);
		}
		return new Reponse<Publicite>(0, null, publicite);

	}

	@PostMapping("/publicite")
	public String creer(@RequestBody Publicite publicite) throws JsonProcessingException {
		Reponse<Publicite> reponse = null;

		try {

			Publicite p = publiciteMetier.creer(publicite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", p.getId()));
			reponse = new Reponse<Publicite>(0, messages, p);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Publicite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/publicite")
	public String modfierUnePublicite(@RequestBody Publicite modif) throws JsonProcessingException {
		Reponse<Publicite> reponsePersModif = null;
		Reponse<Publicite> reponse = null;

		try {

			reponsePersModif = getPubliciteById(modif.getId());
			if (reponsePersModif == null) {
				throw new RuntimeException("pas de pays renvoye");
			}
			Publicite p1 = reponsePersModif.getBody();
			System.out.println("les elements a modifier" + p1);
			Publicite p = publiciteMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
			reponse = new Reponse<Publicite>(0, messages, p);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Publicite>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer le pays par id
	@GetMapping("/publicite/{id}")
	public String findPubliciteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Publicite> reponse;
		try {
			reponse = getPubliciteById(id);
			Publicite p = reponse.getBody();
			reponse = new Reponse<Publicite>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Publicite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer le pays par id
	@GetMapping("/publiciteParPage/{page}")
	public String findPubliciteParPage(@PathVariable String page)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Publicite>> reponse;
		List<Publicite> p = null;
		try {
			p = publiciteMetier.findAllPubliciteParPage(page);

			reponse = new Reponse<List<Publicite>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Publicite>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/publicite")
	public String findAllPublicite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Publicite>> reponse;
		try {
			List<Publicite> p = publiciteMetier.findAll();
			reponse = new Reponse<List<Publicite>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Publicite>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/publiciteIdPosition/{id}")
	public String findPubliciteIdPosition(@PathVariable Long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Publicite>> reponse;
		try {
			List<Publicite> p = publiciteMetier.findAllPubliciteParIdPosition(id);
			reponse = new Reponse<List<Publicite>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Publicite>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/publicite/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Publicite b = null;
		if (!erreur) {
			Reponse<Publicite> responseSup = getPubliciteById(id);
			b = responseSup.getBody();
			if (responseSup.getStatus() != 0) {
				reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s a ete supprime", b.getId()));

				reponse = new Reponse<Boolean>(0, messages, publiciteMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer la phorto d'un membre dans la
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/imagePub")
	public String creerImagePublicite(@RequestParam(name = "image_photo") MultipartFile file, 
			@RequestParam Long id)
			throws Exception {
		Reponse<Publicite> reponse = null;
		Reponse<Publicite> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		System.out.println(libelle);
		reponseParLibelle = getPubliciteById(id);
		Publicite p = reponseParLibelle.getBody();
		System.out.println(p);

		String path = "http://wegetback:8080/getImagePublicite" + "/" + p.getVersion()+"/" + p.getId() + "/" + libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "publiciteImage" +"/"+p.getId()+"/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				p.setPathPhoto(path);
				// p.setPathVideo(null);
				System.out.println(path);
				file.transferTo(new File(dossier+libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getId()));
				reponse = new Reponse<Publicite>(0, messages, publiciteMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Publicite>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Publicite>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer la photo de couverture pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getImagePublicite/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImagePublicite(@PathVariable Long version,@PathVariable Long id, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		
		String dossier = togetImage + "/" + "publiciteImage" + "/" +id+"/" +libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	@PostMapping("/videoPub")
	public String creerVideoPub(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<Publicite> reponse = null;
		Reponse<Publicite> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		System.out.println(libelle);
		reponseParLibelle = getPubliciteById(id);
		Publicite p = reponseParLibelle.getBody();
	

		String path = "http://wegetback:8080/getVideoPublicite" +"/" + p.getVersion()+"/" +p.getId() + "/"+ libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "publiciteVideo" + "/"+p.getId()+"/"+libelle;
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				p.setPathVideo(path);
				System.out.println(path);
				file.transferTo(new File(dossier));
				
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getId()));
				reponse = new Reponse<Publicite>(0, messages, publiciteMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Publicite>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Publicite>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getVideoPublicite/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosVideoPub(@PathVariable Long id,@PathVariable String libelle)
					throws FileNotFoundException, IOException {

		//System.out.println(version);
		String dossier = togetImage + "/" + "publiciteVideo" + "/"+id+"/"+libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

}

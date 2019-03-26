package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
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

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.Document;
import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.entites.abonnement.Gallery;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.IGalleryMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class GalleryController {

	@Autowired
	private IGalleryMetier galleryMetier;
	@Autowired
	private IAbonnementMetier abonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Gallery> getGalleryByLibelle(final String libelle) {
		Gallery gallery = null;
		try {
			gallery = galleryMetier.chercherGalleryParLibelle(libelle);

		} catch (RuntimeException e) {
			new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		if (gallery == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", libelle));
			return new Reponse<Gallery>(2, messages, null);
		}
		return new Reponse<Gallery>(0, null, gallery);

	}

	private Reponse<Gallery> getGalleryById(final Long id) {
		Gallery gallery = null;
		try {
			gallery = galleryMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		if (gallery == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Gallery>(2, messages, null);
		}
		return new Reponse<Gallery>(0, null, gallery);

	}

	/*
	 * private Reponse<List<PhotoGallery>> getPhotoGallery(final Long id) {
	 * List<PhotoGallery> photo = null; try { photo =
	 * galleryMetier.pathPhotoParGalleryId(id);
	 * 
	 * } catch (RuntimeException e) { new Reponse<Gallery>(1,
	 * Static.getErreursForException(e), null); } if (photo == null) { List<String>
	 * messages = new ArrayList<String>();
	 * messages.add(String.format("La personne n'exste pas")); return new
	 * Reponse<List<PhotoGallery>>(2, messages, null); } return new
	 * Reponse<List<PhotoGallery>>(0, null, photo);
	 * 
	 * }
	 */
	@PostMapping("/gallery")
	public String creer(@RequestBody Gallery gallery) throws JsonProcessingException {
		Reponse<Gallery> reponse;

		try {

			Gallery ga = galleryMetier.creer(gallery);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ga.getId()));
			reponse = new Reponse<Gallery>(0, messages, ga);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/gallery")
	public String modifier(@RequestBody Gallery gallery) throws JsonProcessingException {
		Reponse<Gallery> reponse;

		try {

			Gallery ga = galleryMetier.modifier(gallery);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ga.getId()));
			reponse = new Reponse<Gallery>(0, messages, ga);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/galleryParIdDetailAbonnement/{id}")
	public String findGalleryIdSousBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Gallery>> reponse;
		try {
			List<Gallery> ga = galleryMetier.findGalleryParIdDetailAbonnement(id);
			reponse = new Reponse<List<Gallery>>(0, null, ga);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas de docs  trouve");
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Gallery>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/galleryParIdAbonnement/{id}")
	public String findGalleryIdAbonnement(@PathVariable Long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Gallery>> reponse;
		try {
			List<Gallery> ga = galleryMetier.findGalleryParIdAbonnement(id);
			reponse = new Reponse<List<Gallery>>(0, null, ga);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas de docs  trouve");
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Gallery>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/gallery/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Gallery b = null;
		if (!erreur) {
			Reponse<Gallery> responseSup = getGalleryById(id);
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

				reponse = new Reponse<Boolean>(0, messages, galleryMetier.supprimer(id));
				String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + id + "/"+b.getLibelle();
				String dossier1 = togetImage + "/" + "abonnementGallery" + "/" + id + "/"+b.getLibelle();
				String dossier2 = togetImage + "/" + "abonnementVideo" + "/" + id + "/"+b.getLibelle();

				
				File rep = new File(dossier);
				File rep1 = new File(dossier1);
				File rep2 = new File(dossier2);

				if (rep.exists()) {
					rep.delete();
				}
				if (rep1.exists()) {
					rep1.delete();

				}
				if (rep2.exists()) {
					rep2.delete();

				}

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@DeleteMapping("/galleryImageDetailAbonnement")
	public String supprimerImage(@RequestParam(name = "nomPhoto") String nomPhoto,
			@RequestParam Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		List<Gallery> gas = galleryMetier.findGalleryParIdDetailAbonnement(id);

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" la suppression a ete supprime"));

			reponse = new Reponse<Boolean>(0, messages, galleryMetier.supprimer(gas));
			
				for(Gallery ga:gas) {
					String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + id + "/"+ga.getLibelle();
					File rep = new File(dossier+nomPhoto);
				//galleryMetier.s
					rep.delete();
				}
				
			
				
		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@DeleteMapping("/galleryImageAbonnement")
	public String supprimerImageAbonnemnt(@RequestParam(name = "nomPhoto") String nomPhoto,
			@RequestParam Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		List<Gallery> gas = galleryMetier.findGalleryParIdAbonnement(id);

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" la sppression a ete supprime"));

			reponse = new Reponse<Boolean>(0, messages, galleryMetier.supprimer(gas));
			
            for(Gallery ga:gas) {
					String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + id + "/"+ga.getLibelle();
					File rep = new File(dossier);
					rep.delete();
				}
				
			
				
		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	/*@PostMapping("/photoGalleryDetailAbonnement")
	public String creerGalleryPhotosDetailAbonnement(@RequestParam(name = "image_photo[]") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		Long idDAb = ga.getDetailAbonnement().getId();
		File rep=null;
		List<String> pathGallery = new ArrayList<>();
		String[] tab =new String[5];
		int taille= tab.length;
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + idDAb + "/" + ga.getLibelle() + "/";
			 rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			String libelleSansEspace = libelle.trim();
			String libelleS = libelleSansEspace.replaceAll("\\s", "");

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + idDAb + "/" + ga.getLibelle() + "/"
					+ libelleS;
			String path = "http://wegetback:8080/getDetailAbonnementGallery" + "/" + ga.getVersion() + "/" + idDAb + "/"
					+ ga.getLibelle() + "/" + libelleS;
				
                
				file.transferTo(new File(dossier));
				ga.setPathPhoto(pathGallery);
			

		}

		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoGalleryAbonnement")
	public String creerGalleryPhotosAbonnement(@RequestParam(name = "image_photo[]") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		String galleryLibelle= ga.getLibelle();
		String galleryLibelleSEspace = galleryLibelle.trim();
		String galleryLibelleS = galleryLibelleSEspace.replaceAll("\\s", "");

		Long idAb = ga.getAbonnement().getId();
		List<String> pathGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "abonnementGallery" + "/" + idAb + "/" + galleryLibelleS + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			String libelleSansEspace = libelle.trim();
			String libelleS = libelleSansEspace.replaceAll("\\s", "");

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			String dossier = togetImage + "/" + "abonnementGallery" + "/" + idAb + "/" 
			+ galleryLibelleS+ "/"
					+ libelleS;
			String path = "http://wegetback:8080/getAbonnementGallery" + "/" + ga.getVersion() + "/" + idAb + "/"
					+ galleryLibelleS + "/" + libelleS;
			pathGallery.add(path);
			file.transferTo(new File(dossier));
			ga.setPathPhoto(pathGallery);
		}

		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	private String saveUploadedFiles(List<MultipartFile> files) throws IOException {
		if (Files.notExists(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"))) {
			init();
		}

		// String randomPath = "";

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; // next pls
			}

			byte[] bytes = file.getBytes();

			String fileName = file.getOriginalFilename();
			// String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			// randomPath += generateRandomPath() + "." + suffix;

			Path path = Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/" + fileName);
			Files.write(path, bytes);
		}

		return null;
	}

	private void init() {
		try {
			Files.createDirectory(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getDetailAbonnementGallery/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosGalleryDetailAbonnement(@PathVariable Long version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle) throws FileNotFoundException, IOException {

		// System.out.println(version);
		String dossier = togetImage + "/" + "detailAbonnementGallery" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getAbonnementGallery/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosGalleryAbonnement(@PathVariable Long version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle) throws FileNotFoundException, IOException {

		// System.out.println(version);
		String dossier = togetImage + "/" + "abonnementGallery" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// mettre en ligne des video des sous block
	@PostMapping("/videoGalleryDetailAbonnement")
	public String creerGalleryVideoDetailAbonnement(@RequestParam(name = "image_photo[]") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		String gaLibelle = ga.getLibelle();
		String galleryLibelleSEspace = gaLibelle.trim();
		String galleryLibelleS = galleryLibelleSEspace.replaceAll("\\s", "");
		Long idDAb = ga.getDetailAbonnement().getId();
		List<String> pathGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "detailAbonnementVideo" + "/" + idDAb + "/" + galleryLibelleS + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			
			String fileName = libelle.replaceAll("\\s", "");
			if (file.getSize() == 5) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			String dossier = togetImage + "/" + "detailAbonnementVideo" + "/" + idDAb + "/" + galleryLibelleS + "/"
					+ fileName;
			String path = "http://wegetback:8080/getDetailAbonnementVideo" + "/" + ga.getVersion() + "/" + idDAb + "/"
					+ galleryLibelleS+ "/" + fileName;
			pathGallery.add(path);

			file.transferTo(new File(dossier));
			ga.setPathVideo(pathGallery);
		}

		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/videoAbonnement")
	public String creerGalleryvideoAbonnement(@RequestParam(name = "image_photo[]") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		String gaLibelle = ga.getLibelle();
		String galleryLibelleSEspace = gaLibelle.trim();
		String galleryLibelleS = galleryLibelleSEspace.replaceAll("\\s", "");
		Long idAb = ga.getAbonnement().getId();
		List<String> pathVideoGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "abonnementVideo" + "/" + idAb + "/" + galleryLibelleS + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			String fileName = libelle.replaceAll("\\s", "");

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			String dossier = togetImage + "/" + "abonnementVideo" + "/" + idAb + "/" + galleryLibelleS + "/";
			File rep1 = new File(dossier+idAb);
			if(rep1.listFiles().length>=3) {
				throw new RuntimeException("limite atteint");
			}
			file.transferTo(new File(dossier+fileName));
			String path = "http://wegetback:8080/getAbonnementVideo" + "/" + ga.getVersion() + "/" + idAb + "/"
					+ ga.getLibelle() + "/" + fileName;

			pathVideoGallery.add(path);

			ga.setPathVideo(pathVideoGallery);
		}

		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer les images de la gallery
	
	@GetMapping(value = "/getDetailAbonnementVideo/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosVideoDetailAbonnement(
			@PathVariable Long version, 
			@PathVariable Long id,
			@PathVariable String titre, 
			@PathVariable String libelle) 
			throws FileNotFoundException, IOException {

		String dossier = togetImage + "/" + "detailAbonnementVideo" + "/" 
		+ id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// recuperer les images de la gallery
	
	@GetMapping(value = "/getAbonnementVideo/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosVideoMembres(@PathVariable Long version, @PathVariable Long id, @PathVariable String titre,
			@PathVariable String libelle) throws FileNotFoundException, IOException {

		// System.out.println(version);
		String dossier = togetImage + "/" + "abonnementVideo" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
*/
}

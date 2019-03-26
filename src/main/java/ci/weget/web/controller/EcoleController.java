package ci.weget.web.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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

import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IDetailAbonnementMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DetailAbonnementController {

	@Autowired
	private IDetailAbonnementMetier detailAbonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<DetailAbonnement> getSousBlockById(Long id) {
		DetailAbonnement da = null;
		try {
			da = detailAbonnementMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (da == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<DetailAbonnement>(0, null, da);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	private Reponse<DetailAbonnement> getSousBlockParNom(String nom) {
		DetailAbonnement sousBlock = null;
		try {
			sousBlock = detailAbonnementMetier.findDetailAbonnementParNom(nom);
		} catch (RuntimeException e) {
			new Reponse<DetailAbonnement>(1, Static.getErreursForException(e), null);
		}
		if (sousBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'exixte pas", nom));
			return new Reponse<DetailAbonnement>(2, messages, null);
		}
		return new Reponse<DetailAbonnement>(0, null, sousBlock);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/detailAbonnement")
	public String creer(@RequestBody DetailAbonnement sousBlock) throws JsonProcessingException {
		Reponse<DetailAbonnement> reponse;

		try {

			DetailAbonnement sb1 = detailAbonnementMetier.creer(sousBlock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", sb1.getId()));
			reponse = new Reponse<DetailAbonnement>(0, messages, sb1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<DetailAbonnement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	@PutMapping("/detailAbonnement")
	public String modfierUnBlock(@RequestBody DetailAbonnement modif) throws JsonProcessingException {
		Reponse<DetailAbonnement> reponsePersModif = null;
		Reponse<DetailAbonnement> reponse = null;

		// on recupere la personne a modifier

		try {
			DetailAbonnement sb2 = detailAbonnementMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s a modifier avec succes", sb2.getId()));
			reponse = new Reponse<DetailAbonnement>(0, messages, sb2);
		} catch (InvalideTogetException e) {

			reponse = new Reponse<DetailAbonnement>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les blocks de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/detailAbonnement")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<DetailAbonnement>> reponse;
		try {
			List<DetailAbonnement> mats = detailAbonnementMetier.findAll();
			reponse = new Reponse<List<DetailAbonnement>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<DetailAbonnement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	//////////// personnes d'un block par id
	@GetMapping("/detailAbonnementParIdAbonnement/{id}")
	public String getSousBlockParIdDetailBlock(@PathVariable Long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<DetailAbonnement> reponse;
		try {
			DetailAbonnement sb = detailAbonnementMetier.findDetailAbonnementParIdDetailBlock(id);
			reponse = new Reponse<DetailAbonnement>(0, null, sb);
			if (reponse.getBody() == null) {
				throw new RuntimeException("pas d'enregistrement");
			}
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/detailAbonnementParIdBlock/{id}")
	public String getSousBlockParIdBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<DetailAbonnement>> reponse;
		try {
			List<DetailAbonnement> sb = detailAbonnementMetier.findDetailAbonnementParIdBlock(id);
			reponse = new Reponse<List<DetailAbonnement>>(0, null, sb);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas d'enregistrement");
			}
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/detailAbonnement/{id}")
	public String chercherBlockParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<DetailAbonnement> reponse = null;

		reponse = getSousBlockById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////// recherche a partir de mot cle
	///////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////
	@GetMapping("/rechercheDetailAbonnement")
	public String chercherSousBlocParMc(@RequestParam String mc) throws JsonProcessingException {
		Reponse<List<DetailAbonnement>> reponse = null;

		try {
			List<DetailAbonnement> blocks = detailAbonnementMetier.chercherDetailAbonnementParMc(mc);
			if (!blocks.isEmpty()) {
				reponse = new Reponse<List<DetailAbonnement>>(0, null, blocks);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de block enregistrer "));
				reponse = new Reponse<List<DetailAbonnement>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/detailAbonnement/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		DetailAbonnement b = null;
		if (!erreur) {
			Reponse<DetailAbonnement> responseSup = getSousBlockById(id);
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

				reponse = new Reponse<Boolean>(0, messages, detailAbonnementMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	
	@GetMapping("/detailAbonnementParEtablissement/{id}")
	public String profilAbonneLogin(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<DetailAbonnement>> reponse = null;

		try {
			List<DetailAbonnement> db = detailAbonnementMetier.findDetailAbonnementParIdEta(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<DetailAbonnement>>(0, messages, db);
		} catch (Exception e) {

			reponse = new Reponse<List<DetailAbonnement>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	////// ajouter une photo a la base a partir du libelle d'un block
	///////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////

	@PostMapping("/detailAbonnementPhotoCouverture")
	public String creerPhoto(@RequestParam(name = "image_photo[]") MultipartFile[] files, @RequestParam Long id)
			throws Exception {
		Reponse<DetailAbonnement> reponse = null;
		Reponse<DetailAbonnement> reponseParLibelle;
		reponseParLibelle = getSousBlockById(id);

		DetailAbonnement sb = reponseParLibelle.getBody();
		List<String> pathPhotoCouverture = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
	       
	        if (file.isEmpty()) {
				 throw new Exception("impossible de charger un fichier vide "+file.getOriginalFilename() ); 
			}
	        
	        
			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getPhotoCouvertureDetailAbonnement" + "/" + sb.getVersion() + "/" + sb.getId()
					+ "/" + libelle;
			pathPhotoCouverture.add(path);
			sb.setPathPhotoCouverture(pathPhotoCouverture);
			
				
			
	        
		}
		reponse = new Reponse<DetailAbonnement>(0, null, detailAbonnementMetier.modifier(sb));

		return jsonMapper.writeValueAsString(reponse);
	}

	// Multiple file upload
	/*
	 * @PostMapping("/sousBlockphotoCouverture") public String
	 * uploadFileMulti(@RequestParam("image_photo") MultipartFile[]
	 * multipartFiles, @RequestParam Long id) throws InvalideTogetException,
	 * JsonProcessingException { Reponse<SousBlock> reponse = null;
	 * Reponse<SousBlock> reponseParId; reponseParId = getSousBlockById(id); //
	 * appartir de ce libelle on recupere la gallery SousBlock sb =
	 * reponseParId.getBody(); String uploadedFileName =
	 * Arrays.stream(multipartFiles) .map(x -> x.getOriginalFilename()) .filter(x ->
	 * !StringUtils.isEmpty(x)) .collect(Collectors.joining(" , "));
	 * 
	 * if (StringUtils.isEmpty(uploadedFileName)) { List<String> messages = new
	 * ArrayList<>(); messages.add(String.format(" Non effectue")); reponse = new
	 * Reponse<SousBlock>(0, messages, null); }
	 * 
	 * try { String ok = saveUploadedFiles(Arrays.asList(multipartFiles));
	 * List<String> messages = new ArrayList<>();
	 * 
	 * messages.add(String.format(" ok"));
	 * 
	 * reponse = new Reponse<SousBlock>(0, messages, sousBlocksMetier.modifier(sb));
	 * 
	 * } catch (IOException e) { reponse = new Reponse<SousBlock>(1,
	 * Static.getErreursForException(e), null); }
	 * 
	 * return jsonMapper.writeValueAsString(reponse); }
	 */
	// Save single file
	/*private String saveUploadedFiles(List<MultipartFile> files) throws Exception {
		if (Files.notExists(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"))) {
			init();
		}

		// String randomPath = "";

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				 throw new Exception("impossible de charger un fichier vide "+file.getOriginalFilename() ); 
			}
			 String ext = file.getOriginalFilename();
		        String[] extAutorise = {"mp4", "avi","ogg","ogv","jpg","jpeg","png","gif"};
		        String fileNameTarget ="";
		        if (ArrayUtils.contains(extAutorise, ext)) {
		        	fileNameTarget = file.getOriginalFilename();
		             fileNameTarget = fileNameTarget.replaceAll(" ", "_");
		        	byte[] bytes = file.getBytes();

					String fileName = file.getOriginalFilename();
					Path path = Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/" + fileName);
					Files.write(path, bytes);
				}
			
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
*/
	public static String generateRandomPath() {
		String path = UUID.randomUUID().toString().replace("-", "");
		return path;
	}
	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoCouvertureDetailAbonnement/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable Long id, @PathVariable String libelle)
			throws FileNotFoundException, IOException {
		Reponse<DetailAbonnement> reponseParLibelle;
		reponseParLibelle = getSousBlockById(id);

		DetailAbonnement sb = reponseParLibelle.getBody();
		System.out.println(version);
		   if (sb.getPathPhotoCouverture()==null) {
			   throw new RuntimeException("vous devez entrer des photos couverture");
		}
		String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/" + libelle;
		
			File f = new File(dossier);
           byte[] img = IOUtils.toByteArray(new FileInputStream(f));
          

		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// photo de couverture du sous block

	@PostMapping("/detailAbonnementLogo")
	public String creerPhotoLogo(@RequestParam(name = "image_photo") MultipartFile file,
			@RequestParam String nom_sousblock)
			throws Exception {
		Reponse<DetailAbonnement> reponse = null;
		Reponse<DetailAbonnement> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		
		reponseParLibelle = getSousBlockParNom(nom_sousblock);
		DetailAbonnement sb = reponseParLibelle.getBody();
		System.out.println(sb);

		String path = "http://wegetback:8080/getPhotoLogoDetailAbonnement/" + sb.getVersion() + "/" +sb.getId() + "/"+ libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "photologoDetailAbonnement" + "/"+sb.getId() +"/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				sb.setPathLogo(path);
				System.out.println(path);
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", sb.getNom()));
				reponse = new Reponse<DetailAbonnement>(0, messages, detailAbonnementMetier.modifier(sb));

			} catch (Exception e) {

				reponse = new Reponse<DetailAbonnement>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<DetailAbonnement>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo de couverture avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoLogoDetailAbonnement/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosCouverture(@PathVariable String version,
			@PathVariable Long id,
			@PathVariable String libelle)
			throws FileNotFoundException, IOException {

		        System.out.println(version);
				String dossier = togetImage + "/" + "photologoDetailAbonnement" + "/"+id+ "/";
				File f = new File(dossier + libelle);
				byte[] img = IOUtils.toByteArray(new FileInputStream(f));

				return img;
	}
}

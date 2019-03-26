package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.Gallery;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEcoleMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class EcoleController {

	@Autowired
	private IEcoleMetier ecoleMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<Ecole> getEcoleById(Long id) {
		Ecole ecole = null;
		try {
			ecole = ecoleMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (ecole.equals(null)) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Ecole>(0, null, ecole);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	private Reponse<Ecole> getEcoleParNom(String nom) {
		Ecole ecole = null;
		try {
			ecole = ecoleMetier.findEcoleParNom(nom);
		} catch (RuntimeException e) {
			new Reponse<Ecole>(1, Static.getErreursForException(e), null);
		}
		if (ecole == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette ecole n'exixte pas", nom));
			return new Reponse<Ecole>(2, messages, null);
		}
		return new Reponse<Ecole>(0, null, ecole);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// create ecole
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/ecole")
	public String create(@RequestBody Ecole ecole) throws JsonProcessingException {
		Reponse<Ecole> reponse;

		try {

			Ecole save = ecoleMetier.creer(ecole);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", save.getId()));
			reponse = new Reponse<Ecole>(0, messages, save);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Ecole>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// update ecole
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	@PutMapping("/ecole")
	public String update(@RequestBody Ecole modif) throws JsonProcessingException {
		Reponse<Ecole> reponse = null;
		Reponse<Ecole> reponsePersModif=null;
		
		// on recupere ecole a modifier
				reponsePersModif = getEcoleById(modif.getId());
				if (reponsePersModif.getBody() != null) {
					try {
						Ecole ecole = ecoleMetier.modifier(modif);
						List<String> messages = new ArrayList<>();
						messages.add(String.format("%s a modifier avec succes", ecole.getId()));
						reponse = new Reponse<Ecole>(0, messages, ecole);
					} catch (InvalideTogetException e) {

						reponse = new Reponse<Ecole>(1, Static.getErreursForException(e), null);
					}

				} else {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("l'ecole n'existe pas"));
					reponse = new Reponse<Ecole>(0, messages, null);
				}


		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// getAll ecoles
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/ecole")
	public String getAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Ecole>> reponse;
		try {
			List<Ecole> ecoles = ecoleMetier.findAll();
			if (!ecoles.isEmpty()) {
				reponse = new Reponse<List<Ecole>>(0, null, ecoles);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'espaces enregistrées");
				reponse = new Reponse<List<Ecole>>(1, messages, new ArrayList<>());
			}

			reponse = new Reponse<List<Ecole>>(0, null, ecoles);
		} catch (Exception e) {
			reponse = new Reponse<List<Ecole>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

/////////////////////////////////////////////////////////////////////////////////
// get ecole by id
///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/ecole/{id}")
	public String getEcoleBid(@PathVariable Long id) throws JsonProcessingException {
// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Ecole> reponse = null;

		reponse = getEcoleById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// delete ecole
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/ecole/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Ecole b = null;
		if (!erreur) {
			Reponse<Ecole> responseSup = getEcoleById(id);
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

				reponse = new Reponse<Boolean>(0, messages, ecoleMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	//////////// personnes d'un block par id
	@GetMapping("/getEcoleByIdAbonnement/{idAbonnement}")
	public String getEcoleByIdAbonnement(@PathVariable("idAbonnement") long idAbonnement) throws JsonProcessingException, InvalideTogetException {
		Reponse<Ecole> reponse;
		try {
			Ecole sb = ecoleMetier.findEcoleByIdAbonnement(idAbonnement);
			reponse = new Reponse<Ecole>(0, null, sb);
			if (reponse.getBody().equals(null)) {
				throw new RuntimeException("pas d'enregistrement");
			}
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////// recherche a partir de mot cle
	///////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////
	@GetMapping("/getEcoleByMc")
	public String getEcoleByMc(@RequestParam String mc) throws JsonProcessingException {
		Reponse<List<Ecole>> reponse = null;

		try {
			List<Ecole> ecoles = ecoleMetier.findEcoleByMc(mc);
			if (!ecoles.isEmpty()) {
				reponse = new Reponse<List<Ecole>>(0, null, ecoles);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas d'espaces enregistres "));
				reponse = new Reponse<List<Ecole>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}


	@GetMapping("/getEcoleByIdEtablissement/{id}")
	public String getEcoleByIdEtablissement(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Ecole>> reponse = null;

		try {
			List<Ecole> ecoles = ecoleMetier.findEcoleIdTypeEtablissement(id);
			if (!ecoles.isEmpty()) {
				reponse = new Reponse<List<Ecole>>(0, null, ecoles);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'espaces enregistrées");
				reponse = new Reponse<List<Ecole>>(1, messages, new ArrayList<>());
			}

			reponse = new Reponse<List<Ecole>>(0, null, ecoles);
		} catch (Exception e) {

			reponse = new Reponse<List<Ecole>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	////// ajouter une photo a la base a partir du libelle d'un block
	///////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////

	@PostMapping("/uploadPhotoCouverture")
	public String creerDetailAbonnementPhotoCouverture(@RequestParam(name = "image_photo[]") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Ecole> reponse = null;
		Reponse<Ecole> reponseParLibelle;
		reponseParLibelle = getEcoleById(id);

		Ecole da = reponseParLibelle.getBody();
		Long idAb = da.getId();
		List<String> pathPhoto = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/" + idAb;
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/" + idAb + "/" + libelle;
			String path = "http://wegetback:8080/getPhotoCouvertureDetailAbonnement" + "/" + idAb + "/" + libelle;
			pathPhoto.add(path);

			file.transferTo(new File(dossier));
			da.setPathPhotoCouverture(pathPhoto);
		}

		reponse = new Reponse<Ecole>(0, null, ecoleMetier.modifier(da));

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
	/*
	 * private String saveUploadedFiles(List<MultipartFile> files) throws Exception
	 * { if (Files.notExists(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture"
	 * + "/"))) { init(); }
	 * 
	 * // String randomPath = "";
	 * 
	 * for (MultipartFile file : files) { if (file.isEmpty()) { throw new
	 * Exception("impossible de charger un fichier vide "+file.getOriginalFilename()
	 * ); } String ext = file.getOriginalFilename(); String[] extAutorise = {"mp4",
	 * "avi","ogg","ogv","jpg","jpeg","png","gif"}; String fileNameTarget =""; if
	 * (ArrayUtils.contains(extAutorise, ext)) { fileNameTarget =
	 * file.getOriginalFilename(); fileNameTarget = fileNameTarget.replaceAll(" ",
	 * "_"); byte[] bytes = file.getBytes();
	 * 
	 * String fileName = file.getOriginalFilename(); Path path =
	 * Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/" + fileName);
	 * Files.write(path, bytes); }
	 * 
	 * }
	 * 
	 * return null; }
	 * 
	 * private void init() { try { Files.createDirectory(Paths.get(togetImage + "/"
	 * + "sousBlockPhotoCouverture" + "/")); } catch (IOException e) { throw new
	 * RuntimeException("Could not initialize storage", e); } }
	 */
	public static String generateRandomPath() {
		String path = UUID.randomUUID().toString().replace("-", "");
		return path;
	}
	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getEcolePhotoCouverture/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable Long id, @PathVariable String libelle)
			throws FileNotFoundException, IOException {
		Reponse<Ecole> reponseParLibelle;
		reponseParLibelle = getEcoleById(id);

		Ecole sb = reponseParLibelle.getBody();
		// System.out.println(version);
		if (sb.getPathPhotoCouverture() == null) {
			throw new RuntimeException("vous devez entrer des photos couverture");
		}
		String dossier = togetImage + "/" + "detailAbonnementPhotoCouverture" + "/" + sb.getId() + "/" + libelle;

		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// photo de couverture du sous block

	@PostMapping("/uploadLogo")
	public String creerPhotoLogo(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<Ecole> reponse = null;
		Reponse<Ecole> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();

		reponseParLibelle = getEcoleById(id);
		Ecole sb = reponseParLibelle.getBody();
		System.out.println(sb);

		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "photologoDetailAbonnement" + "/" + sb.getId() + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				String path = "http://wegetback:8080/getPhotoLogoDetailAbonnement" + "/" + sb.getId() + "/" + libelle;
				sb.setPathLogo(path);
				System.out.println(path);
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", sb.getId()));
				reponse = new Reponse<Ecole>(0, messages, ecoleMetier.modifier(sb));

			} catch (Exception e) {

				reponse = new Reponse<Ecole>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Ecole>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo de couverture avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getEcoleLogo/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosCouverture(@PathVariable Long id, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// System.out.println(version);
		String dossier = togetImage + "/" + "photologoDetailAbonnement" + "/" + id + "/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
}

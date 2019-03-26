package ci.weget.web.controller.combo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ci.weget.web.entites.combo.ComboTypeEtablisement;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboTypeEtablissementMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComboTypeEtablissementController {
	@Autowired
	private IComboTypeEtablissementMetier comboTypeEtablissementMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;
	@PostMapping("/comboTypeEtablissement")
	public String creer(@RequestBody ComboTypeEtablisement typeEtablissement) throws JsonProcessingException {
		Reponse<ComboTypeEtablisement> reponse = null;

		try {

			ComboTypeEtablisement t = comboTypeEtablissementMetier.creer(typeEtablissement);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t.getId()));
			reponse = new Reponse<ComboTypeEtablisement>(0, messages, t);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboTypeEtablisement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/comboTypeEtablissement")
	public String modfierUnComboTypeEtablisement(@RequestBody ComboTypeEtablisement modif) throws JsonProcessingException {
		
		Reponse<ComboTypeEtablisement> reponse = null;

		try {

			ComboTypeEtablisement t = comboTypeEtablissementMetier.findById(modif.getId());
			if (t == null) {
				throw new RuntimeException("pas de TypeEtablissement renvoye");
			}
			
			ComboTypeEtablisement te = comboTypeEtablissementMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", te.getId()));
			reponse = new Reponse<ComboTypeEtablisement>(0, messages, te);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboTypeEtablisement>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer le pays par id
	@GetMapping("/comboTypeEtablissement/{id}")
	public String findTypeEtablissementParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<ComboTypeEtablisement> reponse;
		try {
			ComboTypeEtablisement t = comboTypeEtablissementMetier.findById(id);
			
			reponse = new Reponse<ComboTypeEtablisement>(0, null, t);
		} catch (Exception e) {
			reponse = new Reponse<ComboTypeEtablisement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les diplome
	@GetMapping("/comboTypeEtablissement")
	public String findAllTypeComboTypeEtablisement() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ComboTypeEtablisement>> reponse;
		try {
			List<ComboTypeEtablisement> p = comboTypeEtablissementMetier.findAll();
			reponse = new Reponse<List<ComboTypeEtablisement>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<ComboTypeEtablisement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// supprimer touts les diplomes par id
		@DeleteMapping("/comboTypeEtablissement/{id}")
		public String supprimer(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
			Reponse<Boolean> reponse;
			try {
				boolean p = comboTypeEtablissementMetier.supprimer(id);
				reponse = new Reponse<Boolean>(0, null, p);
			} catch (Exception e) {
				reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
}

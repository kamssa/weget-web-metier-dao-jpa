package ci.weget.web.controller.publicite;

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

import ci.weget.web.entites.publicite.Position;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.publicite.IPositionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class PositionController {
	@Autowired
	private IPositionMetier positionMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;
	
	private Reponse<Position> getPositionById(final long id) {
		Position position = null;
		try {
			position = positionMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Position>(1, Static.getErreursForException(e), null);
		}
		if (position == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La position n'exste pas", id));
			return new Reponse<Position>(2, messages, null);
		}
		return new Reponse<Position>(0, null, position);

	}


	@PostMapping("/position")
	public String creer(@RequestBody Position position) throws JsonProcessingException {
		Reponse<Position> reponse = null;
		

		try {
          
			Position p = positionMetier.creer(position);
        	   List<String> messages = new ArrayList<>();
   			messages.add(String.format("%s  à été créer avec succes", p.getId()));
   			reponse = new Reponse<Position>(0, messages, p);
           
			
			} catch (InvalideTogetException e) {

			reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/position")
	public String modfierUnePosition(@RequestBody Position modif) throws JsonProcessingException {
		Reponse<Position> reponsePersModif = null;
		Reponse<Position> reponse = null;
		
		try {
	           
	        	 reponsePersModif=  getPositionById(modif.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	 Position p1= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+p1);
	        	   Position p = positionMetier.modifier(modif);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
	   			reponse = new Reponse<Position>(0, messages, p);
	           
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer  le pays par id
		@GetMapping("/position/{id}")
		public String findPositionParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
			Reponse<Position> reponse;
			try {
				reponse = getPositionById(id);
				Position p=reponse.getBody();
				reponse = new Reponse<Position>(0, null, p);
			} catch (Exception e) {
				reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// recuperer  le pays par id
				@GetMapping("/positionParLibelle/{libelle}")
				public String findPositionParLibelle(@PathVariable String libelle) throws JsonProcessingException, InvalideTogetException {
					Reponse<Position> reponse;
					Position p=null;
					try {
						
						p= positionMetier.positionParlibelle(libelle);
						
						reponse = new Reponse<Position>(0, null, p);
					} catch (Exception e) {
						reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}

	// recuperer touts les pays
	@GetMapping("/position")
	public String findAllPosition() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Position>> reponse;
		try {
			List<Position> p = positionMetier.findAll();
			reponse = new Reponse<List<Position>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Position>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/position/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Position b = null;
		if (!erreur) {
			Reponse<Position> responseSup = getPositionById(id);
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

				reponse = new Reponse<Boolean>(0, messages, positionMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	
}

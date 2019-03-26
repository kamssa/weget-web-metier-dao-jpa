package ci.weget.web.modele.metier;

import java.text.ParseException;

import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICreeAbonne {
	public void creerUnAbonne(Personne personne) throws ParseException, InvalideTogetException;
}

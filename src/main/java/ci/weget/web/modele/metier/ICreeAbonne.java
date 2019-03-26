package ci.weget.web.modeles;

import java.text.ParseException;

import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICreeAbonne {
	public void creerUnAbonne(Personne personne) throws ParseException, InvalideTogetException;
}

package ci.weget.web.modeles;

import java.text.ParseException;

import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;

public interface ICreeAbonneGratuit {
	public void creerUnAbonneGratuit(Personne personne) throws ParseException, InvalideTogetException;
}

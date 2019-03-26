package ci.weget.web.modele.metier;

import java.text.ParseException;

import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICreeAbonneGratuit {
	public void creerUnAbonneGratuit(Personne personne) throws ParseException, InvalideTogetException;
}

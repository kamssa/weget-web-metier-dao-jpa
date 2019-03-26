package ci.weget.web.metier.publicite;

import ci.weget.web.entites.publicite.Position;
import ci.weget.web.metier.Imetier;

public interface IPositionMetier extends Imetier<Position, Long> {
public Position positionParlibelle(String libelle);
}

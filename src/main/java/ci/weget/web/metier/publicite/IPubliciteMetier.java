package ci.weget.web.metier.publicite;

import java.util.List;

import ci.weget.web.entites.publicite.Publicite;
import ci.weget.web.metier.Imetier;

public interface IPubliciteMetier extends Imetier<Publicite, Long> {
public List<Publicite>findAllPubliciteParIdPosition(Long id);
public List<Publicite>findAllPubliciteParPage(String page);
}

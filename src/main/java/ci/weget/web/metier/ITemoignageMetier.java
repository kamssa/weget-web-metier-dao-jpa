package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.ecole.Temoignage;

public interface ITemoignageMetier extends Imetier<Temoignage, Long> {
public List<Temoignage>	getTemoignageByIdSousBlock(Long id);
}

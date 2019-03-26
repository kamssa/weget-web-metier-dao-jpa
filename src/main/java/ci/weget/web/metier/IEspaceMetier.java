package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;

public interface IBlocksMetier extends Imetier<Block, Long> {
Block rechercheParLibelle(String libelle);
List<Block> chercherBlockParMc(String mc);
Block findById(Long id);
}

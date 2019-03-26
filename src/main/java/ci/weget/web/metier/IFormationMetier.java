package ci.weget.web.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.entites.ecole.Formation;

public interface IFormationMetier extends Imetier<Formation, Long> {
List<Formation>	getFormationByEcole(Long id);
Formation createImageFormation(MultipartFile file, Long id) throws IllegalStateException,IOException;
Formation createFormulaireFormation(MultipartFile file, Long id) throws IllegalStateException,IOException;
Formation createCatalogueFormation(MultipartFile file, Long id) throws IllegalStateException,IOException;
ResponseEntity<Resource> getImageFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException;
ResponseEntity<Resource> getImageFormulaireFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException;
ResponseEntity<Resource> getImageCatalogueFormation(Long version, Long id,HttpServletRequest request) throws FileNotFoundException, IOException;

}

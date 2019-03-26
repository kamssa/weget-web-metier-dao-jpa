package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.abonnement.Document;

public interface IDocumentMetier extends Imetier<Document, Long> {
Document chercherDocumentParLibelle(String titre);

}

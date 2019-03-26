package ci.weget.web.metier;

import java.util.List;

import org.springframework.mail.MailException;

import ci.weget.web.entites.messagerie.Messagerie;
import ci.weget.web.exception.InvalideTogetException;

public interface ImessagerieMetier extends Imetier<Messagerie, Long> {
public List<Messagerie> findMessagerieByIdPersonneId(long id);

public Messagerie updateStautMessage(Messagerie messagerie) throws InvalideTogetException;
public boolean sendEmail(Messagerie m) throws MailException;
public Messagerie findMessagerieByIdMessage(long id);
public boolean transfertEmail(Messagerie m) throws MailException;
public List<Messagerie> findMessagesEnvoyeParMessagerie();
public List<Messagerie> findMessagesTransfertParMessagerie();


}

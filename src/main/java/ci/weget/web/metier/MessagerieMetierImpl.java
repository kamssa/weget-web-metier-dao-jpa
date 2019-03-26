package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.MessagerieRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.messagerie.Expediteur;
import ci.weget.web.entites.messagerie.Message;
import ci.weget.web.entites.messagerie.Messagerie;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class MessagerieMetierImpl implements ImessagerieMetier {
	@Autowired
	private MessagerieRepository messagerieRepository;
	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public Messagerie creer(Messagerie entity) throws InvalideTogetException {
		Personne personne = personnesRepository.findById(entity.getPersonne().getId()).get();

		Messagerie msg = new Messagerie();
		msg.getMessage().setTypeMessage(false);
		msg.getMessage().setStatut(true);
		msg.setExpediteur(entity.getExpediteur());
		msg.setMessage(entity.getMessage());
		msg.setPersonne(personne);
		return messagerieRepository.save(msg);
	}

	@Override
	public Messagerie modifier(Messagerie modif) throws InvalideTogetException {

		Optional<Messagerie> messagerie = messagerieRepository.findById(modif.getId());

		if (messagerie.isPresent()) {

			if (messagerie.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		return messagerieRepository.save(modif);
	}

	@Override
	@Async
	public boolean sendEmail(Messagerie m) throws MailException {
		MimeMessage email = mailSender.createMimeMessage();
		String mailExp = m.getPersonne().getAdresse().getEmail();
		String mailDest = m.getExpediteur().getEmail();
		String subject = m.getMessage().getSujet();
		String contenu = m.getMessage().getContenu();
		System.out.println("****************messssssssssssssage***********" + "##" + email + "##" + mailExp + "##"
				+ mailDest + "##" + subject + "##" + contenu + m.getMessage().isStatut());

		try {
			MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");

			helper.setFrom(mailExp);
			helper.setTo(mailDest);
			helper.setSubject(subject);
			helper.setText(contenu, true);
			mailSender.send(email);
			Messagerie messagerie = new Messagerie();
			Message mes = new Message();
			Personne personne = personnesRepository.findById(m.getPersonne().getId()).get();
			Expediteur ex = new Expediteur();

			ex.setNom(m.getExpediteur().getNom());
			ex.setPrenom(m.getExpediteur().getPrenom());
			ex.setEntreprise(m.getExpediteur().getEntreprise());
			ex.setEmail(m.getExpediteur().getEmail());

			mes.setContenu(m.getMessage().getContenu());
			mes.setSujet(m.getMessage().getSujet());
			mes.setStatut(false);
			mes.setTypeMessage(true);
			System.out.println("statut messagerie###########################" + mes);

			mes.setStatut(false);

			messagerie.setExpediteur(ex);
			messagerie.setMessage(mes);
			messagerie.setPersonne(personne);

			messagerieRepository.save(messagerie);
		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return true;
	}

	@Override
	@Async
	public boolean transfertEmail(Messagerie m) throws MailException {
		MimeMessage email = mailSender.createMimeMessage();
		String mailExp = m.getPersonne().getAdresse().getEmail();
		String mailDest = m.getExpediteur().getEmail();
		String subject = m.getMessage().getSujet();
		String contenu = m.getMessage().getContenu();
		System.out.println("****************messssssssssssssage***********" + "##" + email + "##" + mailExp + "##"
				+ mailDest + "##" + subject + "##" + contenu + m.getMessage().isStatut());

		try {
			MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");

			helper.setFrom(mailExp);
			helper.setTo(mailDest);
			helper.setSubject(subject);
			helper.setText(contenu, true);
			mailSender.send(email);
			Messagerie messagerie = new Messagerie();
			Message mes = new Message();
			Expediteur ex = new Expediteur();
			Personne personne = personnesRepository.findById(m.getPersonne().getId()).get();
			ex.setNom(m.getExpediteur().getNom());
			ex.setPrenom(m.getExpediteur().getPrenom());
			ex.setEntreprise(m.getExpediteur().getEntreprise());
			ex.setEmail(m.getExpediteur().getEmail());

			// defini si le message est un message envoye par mail
			mes.setContenu(m.getMessage().getContenu());
			mes.setSujet(m.getMessage().getSujet());
			mes.setStatut(false);
			mes.setTypeMessage(true);

			mes.setStatut(false);

			messagerie.setExpediteur(ex);
			messagerie.setMessage(mes);
			messagerie.setPersonne(personne);
			messagerieRepository.save(messagerie);
		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<Messagerie> findAll() {

		return messagerieRepository.findAll();
	}

	@Override
	public Messagerie findById(Long id) {

		return messagerieRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		messagerieRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Messagerie> entites) {
		messagerieRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		return messagerieRepository.existsById(id);
	}

	@Override
	public List<Messagerie> findMessagerieByIdPersonneId(long id) {

		return messagerieRepository.getMessagrieByIdPersonne(id);

	}

	@Override
	public Messagerie updateStautMessage(Messagerie messagerie) throws InvalideTogetException {
		Messagerie mes = findById(messagerie.getId());

		mes.getMessage().setStatut(false);

		return messagerieRepository.save(mes);
	}

	@Override
	public Messagerie findMessagerieByIdMessage(long id) {

		return messagerieRepository.findMessagerieByIdMessage(id);
	}

	@Override
	public List<Messagerie> findMessagesEnvoyeParMessagerie() {

		return messagerieRepository.findMessagesEnvoyeParMessagerie();

	}

	@Override
	public List<Messagerie> findMessagesTransfertParMessagerie() {

		return messagerieRepository.findMessagesTransfererParMessagerie();

	}

}

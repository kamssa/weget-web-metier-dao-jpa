package ci.weget.web.listener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import ci.weget.web.entites.personne.Membre;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.ContenuMail;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	@Autowired
	private IMembreMetier service;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;
	@Value("${support.email}")
	private String setFrom;

	@Override
	@Async
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Async
	private void confirmRegistration(final OnRegistrationCompleteEvent event) throws MessagingException, NoSuchAlgorithmException {
		
		final Membre membre = event.getMembre();
		final String action = event.getAppUrl();
		String token = "";
		String tokenHtml = "";
        String token1 = "";
        String lien="http://weget:4200/#/control/"+membre.getLogin()+"/"+UUID.randomUUID().toString();
		
       if (action.equals("Register")) {
    	   for (int i = 0; i < 6; i++) {
   			token1=(int) (Math.random() * (10))+"";
   			token += token1;
   			 tokenHtml += "<div style=\"width:10%; padding:10px;  border:1px dashed red; float:left;\">"+token1+"</div>"
  ;

   		}

   		ContenuMail mail = new ContenuMail();
   		mail.setCodeValidation(tokenHtml);
   		//String codeValidation= mail.getCodeValidation();
   		String contentHtml = mail.getContenuHtml(tokenHtml,lien+"/"+token);
   		service.createVerificationToken(membre, token);

   		constructEmailMessage(event, membre, token, contentHtml);
   		constructSmsMessage(event, membre );
   		// mailSender.send(email);
	}else if(action.equals("Reset")) {
		String mdpOublier = "123456789";
	    String tokenMdpOublier=null;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdpOublier.getBytes());
        byte byteData[] = md.digest();
      //convertir le tableau de bits en une format hexadécimal
        StringBuffer hexString = new StringBuffer();
     for (int i=0;i<byteData.length;i++) {
      String hex=Integer.toHexString(0xff & byteData[i]);
          if(hex.length()==1) hexString.append('0');
          hexString.append(hex);
     }
     tokenMdpOublier = hexString.toString();
		  lien="http://weget:4200/#/checking/"+membre.getLogin()+"/"+tokenMdpOublier+"/"+UUID.randomUUID().toString();
		    ContenuMail mail = new ContenuMail();
		   // mail.setCodeValidation(tokenHtml);
		    System.out.println("le lien !"+lien);
            
		    String contentHtml = mail.getContenuHtml(tokenHtml,lien+"/"+token);
		  //  System.out.println("le lien !"+lien);
		    System.out.println("le lien !"+tokenMdpOublier);
			service.createVerificationToken(membre, tokenMdpOublier);
			constructEmailMessage(event, membre, tokenMdpOublier, contentHtml);
		  }
		  else
		    System.out.println("le mail n'est pas parti !");
       
	 
	}
   

		
		// mailSender.send(email);
	


	@Async
	private final void constructEmailMessage(final OnRegistrationCompleteEvent event, final Membre membre,
			final String token, String contHtml) throws MessagingException {
		// ContenuMail contenu = new ContenuMail();
		final String recipientAddress = membre.getLogin();
		final String subject = "Registration Confirmation";
		final String confirmationUrl = contHtml;
		//final String codeValidation1 = codeValidation;
 
		// final String message = messages.getMessage("impossible de continue", null,
		// event.getLocale());

		MimeMessage email = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");

		// final String message = messages.getMessage("impossible de continue", null,
		// event.getLocale());

		helper.setFrom(setFrom);
		helper.setTo(recipientAddress);
		helper.setSubject(subject);
		helper.setText(confirmationUrl, true);
		// email.setFrom(env.getProperty("support.email"));
		mailSender.send(email);

	}
	private final void constructSmsMessage(final OnRegistrationCompleteEvent event, 
			final Membre membre) throws MessagingException {
		String url = "http://africasmshub.mondialsms.net/api/api_http.php";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<Object, Object> map = new LinkedMultiValueMap<Object, Object>();
		map.add("username", "PREPA");
		map.add("password", "PREPA1TOGET");
		map.add("sender", "TOGETOP");
		map.add("text", "Saisissez ce code dans le champ:");
		map.add("type", "text");
		map.add("datetime", LocalDateTime.now().toString());
		map.add("to", membre.getTelephone());
		

		HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<MultiValueMap<Object, Object>>(map,
				headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		
	}

	public String getSetFrom() {
		return setFrom;
	}

	public void setSetFrom(String setFrom) {
		this.setFrom = setFrom;
	}


}

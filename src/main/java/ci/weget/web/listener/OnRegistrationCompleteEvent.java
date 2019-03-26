package ci.weget.web.listener;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import ci.weget.web.entites.personne.Membre;


public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	    private final String appUrl;
	     private final Locale locale;
	    private final Membre membre;

	public OnRegistrationCompleteEvent(final Membre membre,final Locale locale, final String appUrl) {
		super(membre);
		this.membre = membre;
		this.locale = locale;
		this.appUrl = appUrl;

	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public Membre getMembre() {
		return membre;
	}

	
}

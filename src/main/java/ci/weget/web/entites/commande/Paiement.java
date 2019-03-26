package ci.weget.web.entites;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Paiement")
public class Paiement extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private final String apikey="8314848845acc7e576295b1.30166753";
	private  int cpm_amount;
	private final String cpm_currency="CFA";
	private  String cpm_custom ="";
	private final String cpm_designation="ACHAT DE BLOCK(S)";
	private final String cpm_language="fr";
	private final String cpm_page_action="PAYMENT";
	private final String cpm_payment_config="SINGLE";
	private final int cpm_site_id=831130;
	private  String cpm_trans_date=LocalDateTime.now().toString();
	private  int cpm_trans_id;
	private final String cpm_version="V1";
	//private final String notify_url="#";

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Commande")
	private Commande commande;

	
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public int getCpm_amount() {
		return cpm_amount;
	}

	public int getCpm_site_id() {
		return cpm_site_id;
	}

	public String getApikey() {
		return apikey;
	}

	public String getCpm_currency() {
		return cpm_currency;
	}

	public String getCpm_custom() {
		return cpm_custom;
	}

	public String getCpm_designation() {
		return cpm_designation;
	}

	public String getCpm_language() {
		return cpm_language;
	}

	public String getCpm_page_action() {
		return cpm_page_action;
	}

	public String getCpm_payment_config() {
		return cpm_payment_config;
	}

	public String getCpm_trans_date() {
		return cpm_trans_date;
	}

	

	public int getCpm_trans_id() {
		return cpm_trans_id;
	}

	public void setCpm_custom(String cpm_custom) {
		this.cpm_custom = cpm_custom;
	}

	public void setCpm_trans_date(String cpm_trans_date) {
		this.cpm_trans_date = cpm_trans_date;
	}

	public String getCpm_version() {
		return cpm_version;
	}

	public void setCpm_amount(int cpm_amount) {
		this.cpm_amount = cpm_amount;
	}

	public void setCpm_trans_id(int cpm_trans_id) {
		this.cpm_trans_id = cpm_trans_id;
	}

	
}

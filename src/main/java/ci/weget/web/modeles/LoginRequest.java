package ci.weget.web.modeles;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
	private String loginOrTelephone;

	private String telephone;

	@NotBlank
	private String password;
	@NotBlank
	private String type;

	public LoginRequest() {
		super();

	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLoginOrTelephone() {
		return loginOrTelephone;
	}

	public void setLoginOrTelephone(String loginOrTelephone) {
		this.loginOrTelephone = loginOrTelephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

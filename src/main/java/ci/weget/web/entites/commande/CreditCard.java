package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ValidationException;

@Embeddable
public class CreditCard {
	 @Column(name = "credit_card_number", length = 30) 
	 private String creditCardNumber; 
	 @Column(name = "credit_card_type")  
	 private String creditCardType; 
	 @Column(name = "credit_card_expiry_date", length = 5)  
	 private String creditCardExpDate;
	 
	   @PrePersist  
	   @PreUpdate  
	   private void validateData() { 
		   if (creditCardNumber==null || "".equals(creditCardNumber))
			   throw new ValidationException("Invalid number");  
		   if (creditCardType == null || "".equals(creditCardType))  
			   throw new ValidationException("Invalid type");   
		   if (creditCardExpDate==null|| "".equals(creditCardExpDate)) 
			   throw new ValidationException("Invalid expiry date"); 
		   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditCardExpDate == null) ? 0 : creditCardExpDate.hashCode());
		result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime * result + ((creditCardType == null) ? 0 : creditCardType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (creditCardExpDate == null) {
			if (other.creditCardExpDate != null)
				return false;
		} else if (!creditCardExpDate.equals(other.creditCardExpDate))
			return false;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		if (creditCardType == null) {
			if (other.creditCardType != null)
				return false;
		} else if (!creditCardType.equals(other.creditCardType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardNumber=" + creditCardNumber + ", creditCardType=" + creditCardType
				+ ", creditCardExpDate=" + creditCardExpDate + "]";
	}

}

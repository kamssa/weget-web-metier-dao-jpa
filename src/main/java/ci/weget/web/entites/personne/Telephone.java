package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Telephone")
public class Telephone extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	// les attibuts de telephone
		private String type;
		private String numero;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public void setType() {
			this.type = "mobile";
		}
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		@Override
		public String toString() {
			return "Telephones [type=" + type + ", numero=" + numero + "]";
		}
		
} 

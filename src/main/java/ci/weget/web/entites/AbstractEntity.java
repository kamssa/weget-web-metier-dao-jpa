package ci.weget.web.entites;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import ci.weget.web.modeles.audit.DateAudit;

@MappedSuperclass
public class AbstractEntity extends DateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// definition de la cle primaire
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected Long id;
	@Version
	protected Long version;
	
	// mise en place de la methode hashage pour une recherche rapide
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	
	// initialisation

	public AbstractEntity() {
		super();
	}

	public AbstractEntity build(Long id, Long version) {
		this.id = id;
		this.version = version;
		return this;
	}

	public AbstractEntity(Long id) {
		super();
		this.id = id;
	}

	@Override
	public boolean equals(Object entity) {
		String class1 = this.getClass().getName();
		String class2 = entity.getClass().getName();
		if (!class2.equals(class1)) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) entity;
		return this.id == other.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	}

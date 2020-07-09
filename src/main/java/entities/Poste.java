package entities;

import javax.persistence.Entity;

@Entity
public class Poste extends Base {

	public Poste() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Poste(long id, String reference, String designation) {
		super();
		// TODO Auto-generated constructor stub
		this.setId(id);
		this.setReference(reference);
		this.setDesignation(designation);
	}

}

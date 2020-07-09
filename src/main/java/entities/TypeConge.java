package entities;

import javax.persistence.Entity;

@Entity
public class TypeConge extends Base {

	public TypeConge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TypeConge(long id, String reference, String designation) {
		super();
		// TODO Auto-generated constructor stub
		this.setId(id);
		this.setReference(reference);
		this.setDesignation(designation);
	}

}

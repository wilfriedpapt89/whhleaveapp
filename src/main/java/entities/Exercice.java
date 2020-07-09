package entities;

import javax.persistence.Entity;

@Entity
public class Exercice extends Base {

	public Exercice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exercice(long id, String reference, String designation, boolean deleted) {
		super(id, reference, designation, deleted);
		// TODO Auto-generated constructor stub
	}

	
}

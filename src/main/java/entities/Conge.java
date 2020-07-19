package entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Conge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private OperateurC employee;
	@OneToOne
	private OperateurC interimat;
	@OneToOne
	private OperateurC referant;
	private Integer state;
	private Date debut;
	private Date dateValidation;
	private Date dateRejet;
	private Date fin;
	@OneToOne
	private Exercice exercice;
	@OneToOne
	private TypeConge typeConge;
	private String motif;
	private String motifValidation;
	private String motifRejet;
	@Transient
	private String dateDebutString;
	@Transient
	private String dateFinString;
	@Transient
	private String statutLibelle;

	public Conge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Conge(Long id, OperateurC employee, OperateurC interimat, OperateurC referant, Integer state, Date debut,
			Date dateValidation, Date dateRejet, Date fin, Exercice exercice, TypeConge typeConge, String motif,
			String motifValidation, String motifRejet, String dateDebutString, String dateFinString) {
		super();
		this.id = id;
		this.employee = employee;
		this.interimat = interimat;
		this.referant = referant;
		this.state = state;
		this.debut = debut;
		this.dateValidation = dateValidation;
		this.dateRejet = dateRejet;
		this.fin = fin;
		this.exercice = exercice;
		this.typeConge = typeConge;
		this.motif = motif;
		this.motifValidation = motifValidation;
		this.motifRejet = motifRejet;
		this.dateDebutString = dateDebutString;
		this.dateFinString = dateFinString;
	}

	public Conge(Conge conge) {
		super();
		this.id = conge.getId();
		this.employee = conge.getEmployee();
		this.interimat = conge.getInterimat();
		this.referant = conge.getReferant();
		this.state = conge.getState();
		this.debut = conge.getDebut();
		this.dateValidation = conge.getDateValidation();
		this.dateRejet = conge.getDateRejet();
		this.fin = conge.getFin();
		this.exercice = conge.getExercice();
		this.typeConge = conge.getTypeConge();
		this.motif = conge.getMotif();
		this.motifValidation = conge.getMotifValidation();
		this.motifRejet = conge.getMotifRejet();
		this.dateDebutString = conge.getDateDebutString();
		this.dateFinString = conge.getDateFinString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OperateurC getEmployee() {
		return employee;
	}

	public void setEmployee(OperateurC employee) {
		this.employee = employee;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public TypeConge getTypeConge() {
		return typeConge;
	}

	public void setTypeConge(TypeConge typeConge) {
		this.typeConge = typeConge;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public OperateurC getInterimat() {
		return interimat;
	}

	public void setInterimat(OperateurC interimat) {
		this.interimat = interimat;
	}

	public OperateurC getReferant() {
		return referant;
	}

	public void setReferant(OperateurC referant) {
		this.referant = referant;
	}

	public Exercice getExercice() {
		return exercice;
	}

	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}

	public String getDateDebutString() {
		String result = Utilitaire.transformUtilDateInString(debut, 2);
		return result;
	}

	public String getDateFinString() {
		String result = Utilitaire.transformUtilDateInString(fin, 2);
		return result;
	}

	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public Date getDateRejet() {
		return dateRejet;
	}

	public void setDateRejet(Date dateRejet) {
		this.dateRejet = dateRejet;
	}

	public String getMotifValidation() {
		return motifValidation;
	}

	public void setMotifValidation(String motifValidation) {
		this.motifValidation = motifValidation;
	}

	public String getMotifRejet() {
		return motifRejet;
	}

	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}

	public String getStatutLibelle() {

		switch (state) {
		case 1:
			statutLibelle = "Demande en cours ...";
			break;
		case 2:
			statutLibelle = "Demande validée";
			break;
		case 3:
			statutLibelle = "Demande rejetté";
			break;
		}

		return statutLibelle;

	}

	public void setStatutLibelle(String statutLibelle) {
		this.statutLibelle = statutLibelle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Conge other = (Conge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

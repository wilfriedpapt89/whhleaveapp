package managedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import entities.Conge;
import entities.OperateurC;
import entities.TypeConge;
import entities.Utilitaire;
import persistence.CongeDAO;
import persistence.TypeCongeDAO;

@ManagedBean
@ViewScoped
public class DemandeCongeBean {

	@ManagedProperty(value = "#{employeeBean}")
	private EmployeeBean employeeBean;
	@ManagedProperty(value = "#{appManagerBean}")
	private AppManagerBean appManagerBean;
	private Conge conge;
	private List<Conge> allConges;
	private Conge congeSelected;
	private List<TypeConge> typeConges;
	private List<SelectItem> typeCongesItems;
	private List<SelectItem> interimairesItems;
	private List<OperateurC> interimaires;
	private int interimaireSelected;
	private int typeCongeSelected;

	public DemandeCongeBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {

		conge = new Conge();
		buildTypeConge();
		buildInterimaire();

	}

	public void save() {

		if (typeCongeSelected == 0) {
			Utilitaire.afficherAttention("A votre attention", "Veuillez précisez le type de congé");
			Utilitaire.updateForm("form1:msg");
			return;
		}

		else if (interimaireSelected == 0) {
			Utilitaire.afficherAttention("A votre attention", "Veuillez précisez celui qui assurera votre intérimat");
			Utilitaire.updateForm("form1:msg");
			return;
		}

		for (TypeConge ty : typeConges)
			if (typeCongeSelected == ty.getId()) {
				conge.setTypeConge(ty);
				break;
			}

		for (OperateurC op : employeeBean.getEmployees())
			if (interimaireSelected == op.getId()) {
				conge.setInterimat(op);
			} else
				conge.setEmployee(op);

		conge.setState(1);
		boolean saved = CongeDAO.saveOrUpdate(conge);

		if (saved) {
			appManagerBean.addDemandeConge(conge);
			Utilitaire.afficherInformation("Demande de congé envoyée.");
			Utilitaire.updateForm("form1");
			conge = new Conge();
			typeCongeSelected = 0;
			interimaireSelected = 0;
		}
	}

	private void buildTypeConge() {
		// TODO Auto-generated method stub

		typeConges = TypeCongeDAO.loadAll();
		typeConges = typeConges.stream().filter(fi -> {
			if (!fi.isDeleted())
				return true;
			else
				return false;
		}).collect(Collectors.toList());

		if (typeConges != null && !typeConges.isEmpty()) {
			typeCongesItems = new ArrayList<SelectItem>();
			typeCongesItems.add(new SelectItem(0, "Séléctionner le type de congé"));
			for (TypeConge ty : typeConges) {
				typeCongesItems.add(new SelectItem(ty.getId(), ty.getDesignation()));
			}
		}
	}

	private void buildInterimaire() {
		// TODO Auto-generated method stub

		interimaires = employeeBean.getEmployees();

		if (interimaires != null && !interimaires.isEmpty()) {
			interimairesItems = new ArrayList<SelectItem>();
			interimairesItems.add(new SelectItem(0, "Séléctionner celui qui s'occupera de votre interimat"));
			for (OperateurC op : interimaires) {
				interimairesItems.add(new SelectItem(op.getId(), op.getNom() + " - " + op.getPrenom()));
			}
		}
	}

	public Conge getConge() {
		return conge;
	}

	public void setConge(Conge conge) {
		this.conge = conge;
	}

	public List<Conge> getAllConges() {
		return allConges;
	}

	public void setAllConges(List<Conge> allConges) {
		this.allConges = allConges;
	}

	public Conge getCongeSelected() {
		return congeSelected;
	}

	public void setCongeSelected(Conge congeSelected) {
		this.congeSelected = congeSelected;
	}

	public List<SelectItem> getTypeCongesItems() {
		return typeCongesItems;
	}

	public void setTypeCongesItems(List<SelectItem> typeCongesItems) {
		this.typeCongesItems = typeCongesItems;
	}

	public int getTypeCongeSelected() {
		return typeCongeSelected;
	}

	public void setTypeCongeSelected(int typeCongeSelected) {
		this.typeCongeSelected = typeCongeSelected;
	}

	public EmployeeBean getEmployeeBean() {
		return employeeBean;
	}

	public void setEmployeeBean(EmployeeBean employeeBean) {
		this.employeeBean = employeeBean;
	}

	public List<SelectItem> getInterimairesItems() {
		return interimairesItems;
	}

	public void setInterimairesItems(List<SelectItem> interimairesItems) {
		this.interimairesItems = interimairesItems;
	}

	public int getInterimaireSelected() {
		return interimaireSelected;
	}

	public void setInterimaireSelected(int interimaireSelected) {
		this.interimaireSelected = interimaireSelected;
	}

	public AppManagerBean getAppManagerBean() {
		return appManagerBean;
	}

	public void setAppManagerBean(AppManagerBean appManagerBean) {
		this.appManagerBean = appManagerBean;
	}

}

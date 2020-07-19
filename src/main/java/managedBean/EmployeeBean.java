package managedBean;

import java.security.Provider.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import entities.OperateurC;
import entities.Poste;
import entities.Utilitaire;
import persistence.EmployeeDAO;

@ManagedBean
@ViewScoped
public class EmployeeBean {

	@ManagedProperty(value = "#{posteBean}")
	private PosteBean posteBean;
	private OperateurC employee;
	private OperateurC employeeSelected;
	private List<OperateurC> employees;
	private List<Poste> postes;
	private List<SelectItem> postesSelectItems;
	private List<SelectItem> referantSelectItems;
	private int posteSelected;
	private int referantSelected;

	@PostConstruct
	public void init() {

		employee = new OperateurC();
		employees = new ArrayList<>();
		referantSelectItems = new ArrayList<>();
		postes = posteBean.getPostes();
		postesSelectItems = new ArrayList<>();
		buildAllSelectItems();
	}

	private void buildAllSelectItems() {
		postesSelectItems.clear();
		postesSelectItems.add(new SelectItem(0, "Choisir mon poste"));
		employees = EmployeeDAO.loadAllEmployee();
		for (Poste poste : postes)
			postesSelectItems.add(new SelectItem(poste.getId(), poste.getDesignation()));

		referantSelectItems.clear();
		referantSelectItems.add(new SelectItem(0, "Choisir mon référant"));
		if (employees != null && !employees.isEmpty())
			for (OperateurC referant : employees)
				referantSelectItems.add(new SelectItem(referant.getId(),
						referant.getPrenom() + "  " + referant.getNom()));

	}

	public void save() {

		if (employees != null && !employees.isEmpty()) {

			if (!checkPoste()) {
				Utilitaire.afficherAttention("A votre attention", "Veuillez ajouter votre poste.");
				Utilitaire.updateComponents(Arrays.asList("form1:msg"));
				return;
			}

			checkEmployee();
			employee.setInscription(LocalDate.now());
			employee.setActif(false);
			employee.setSuspendu(false);
			employee.setAdmin(false);
			employee.setNotifie(false);
		} else {
			employee.setInscription(LocalDate.now());
			employee.setActif(true);
			employee.setSuspendu(false);
			employee.setAdmin(true);
			employee.setNotifie(true);
		}
		boolean saved = EmployeeDAO.saveOrUpdate(employee);

		if (saved) {
			if (!employee.isActif())
				Utilitaire.afficherInformation("Compte créé. Contacter l'administrateur pour pouvoir l'activer");
			else
				Utilitaire.afficherInformation(
						"Compte créé. Vous avez été paramtré comme administrateur de la plate-form");
			referantSelected = 0;
			posteSelected = 0;
			employee = new OperateurC();
			employees = EmployeeDAO.loadAllEmployee();
		} else {
			Utilitaire.afficherAttention("Oups", "Echec de l'opération");
		}

		Utilitaire.updateComponents(Arrays.asList("form1"));
	}

	private boolean checkPoste() {

		boolean founded = false;
		for (Poste poste : postes) {
			if (poste.getId() == posteSelected) {
				founded = true;
				employee.setPoste(poste);
				break;
			}

		}

		return founded;
	}

	private boolean checkEmployee() {

		boolean founded = false;
		for (OperateurC operateur : employees) {
			if (operateur.getId() == referantSelected) {
				founded = true;
				employee.setReferant(operateur);
				break;
			}
		}
		return founded;
	}

	public OperateurC getEmployee() {
		return employee;
	}

	public void setEmployee(OperateurC employee) {
		this.employee = employee;
	}

	public OperateurC getEmployeeSelected() {
		return employeeSelected;
	}

	public void setEmployeeSelected(OperateurC employeeSelected) {
		this.employeeSelected = employeeSelected;
	}

	public List<OperateurC> getEmployees() {
		return employees;
	}

	public void setEmployees(List<OperateurC> employees) {
		this.employees = employees;
	}

	public List<Poste> getServices() {
		return postes;
	}

	public void setServices(List<Poste> postes) {
		this.postes = postes;
	}

	public PosteBean getPosteBean() {
		return posteBean;
	}

	public void setPosteBean(PosteBean posteBean) {
		this.posteBean = posteBean;
	}

	public List<SelectItem> getPostesSelectItems() {
		return postesSelectItems;
	}

	public void setPostesSelectItems(List<SelectItem> postesSelectItems) {
		this.postesSelectItems = postesSelectItems;
	}

	public int getPosteSelected() {
		return posteSelected;
	}

	public void setPosteSelected(int posteSelected) {
		this.posteSelected = posteSelected;
	}

	public int getReferantSelected() {
		return referantSelected;
	}

	public void setReferantSelected(int referantSelected) {
		this.referantSelected = referantSelected;
	}

	public List<SelectItem> getServicesItems() {
		return postesSelectItems;
	}

	public List<SelectItem> getReferantSelectItems() {
		return referantSelectItems;
	}

	public void setReferantSelectItems(List<SelectItem> referantSelectItems) {
		this.referantSelectItems = referantSelectItems;
	}

	public void setServicesItems(List<SelectItem> servicesItems) {
		this.postesSelectItems = servicesItems;
	}

	public int getServiceSelected() {
		return posteSelected;
	}

	public void setServiceSelected(int serviceSelected) {
		this.posteSelected = serviceSelected;
	}

}

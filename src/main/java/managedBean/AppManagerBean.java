package managedBean;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import entities.Conge;
import entities.OperateurC;
import entities.Poste;
import entities.Utilitaire;
import persistence.CongeDAO;
import persistence.EmployeeDAO;
import persistence.PosteDAO;

@ManagedBean(eager = true)
@ApplicationScoped
public class AppManagerBean {

	// Pour le RH
	private CopyOnWriteArrayList<Conge> demandeConges;
	private CopyOnWriteArrayList<Conge> congesValides;
	private CopyOnWriteArrayList<Conge> congesRejettes;
	// Pour notifier les interims
	private Map<Long, Boolean> notifications;
	private CopyOnWriteArrayList<Conge> allConges;
	private CopyOnWriteArrayList<Poste> allPostes;
	private CopyOnWriteArrayList<OperateurC> allEmployee;

	@PostConstruct
	public void init() {
		sortConnges();
		loadAllPoste();
		loadAllEmployee();
	}

	private void loadAllEmployee() {
		// TODO Auto-generated method stub

		allEmployee = EmployeeDAO.loadAllValidateEmployee2();
	}

	private void loadAllPoste() {
		// TODO Auto-generated method stub
		allPostes = PosteDAO.loadAll2();
	}

	public void addDemandeConge(Conge conge) {

		demandeConges.add(conge);

		if (notifications != null) {
			if (conge.getReferant() != null)
				notifications.put(conge.getReferant().getId(), true);
		}
	}

	public void removeFromDemandeConge(Conge conge) {

		demandeConges.remove(conge);

		if (notifications != null) {
			if (conge.getEmployee() != null)
				notifications.put(conge.getEmployee().getId(), true);
		}

		if (conge.getDateValidation() != null) {

			congesValides.add(0, conge);
		} else {

			congesRejettes.add(0, conge);
		}

	}

	public void sortConnges() {

		demandeConges = new CopyOnWriteArrayList<>();
		congesValides = new CopyOnWriteArrayList<>();
		allConges = CongeDAO.loadAll2();
		if (allConges != null)
			for (Conge c : allConges) {
				if (c.getState() == 1) {
					demandeConges.add(c);
				} else if (c.getState() == 2) {
					congesValides.add(c);
				} else if (c.getState() == 3) {
					congesRejettes.add(c);
				}
			}
		else
			allConges = new CopyOnWriteArrayList<>();
	}

	public boolean checkNotification(OperateurC operateurC) {

		if (notifications != null && !notifications.isEmpty()) {
			if (notifications.containsKey(operateurC.getId()))
				return notifications.get(operateurC.getId());
			else
				return false;
		} else
			return false;
	}

	public void logout() {

		Utilitaire.getSession().invalidate();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPosteSaved(Long posteId) {

		Poste poste = PosteDAO.getOnlyOne(posteId);
		allPostes.add(0, poste);
	}

	public void loadAllEmploye(Long posteId) {

	}

	public CopyOnWriteArrayList<Conge> getDemandeConges() {
		return demandeConges;
	}

	public void setDemandeConges(CopyOnWriteArrayList<Conge> demandeConges) {
		this.demandeConges = demandeConges;
	}

	public CopyOnWriteArrayList<Conge> getAllConges() {
		return allConges;
	}

	public void setAllConges(CopyOnWriteArrayList<Conge> allConges) {
		this.allConges = allConges;
	}

	public Map<Long, Boolean> getNotifications() {
		return notifications;
	}

	public void setNotifications(Map<Long, Boolean> notifications) {
		this.notifications = notifications;
	}

	public CopyOnWriteArrayList<Conge> getCongesValides() {
		return congesValides;
	}

	public void setCongesValides(CopyOnWriteArrayList<Conge> congesValides) {
		this.congesValides = congesValides;
	}

	public CopyOnWriteArrayList<Conge> getCongesRejettes() {
		return congesRejettes;
	}

	public void setCongesRejettes(CopyOnWriteArrayList<Conge> congesRejettes) {
		this.congesRejettes = congesRejettes;
	}

	public CopyOnWriteArrayList<Poste> getAllPostes() {
		return allPostes;
	}

	public void setAllPostes(CopyOnWriteArrayList<Poste> allPostes) {
		this.allPostes = allPostes;
	}

	public CopyOnWriteArrayList<OperateurC> getAllEmployee() {
		return allEmployee;
	}

	public void setAllEmployee(CopyOnWriteArrayList<OperateurC> allEmployee) {
		this.allEmployee = allEmployee;
	}

}

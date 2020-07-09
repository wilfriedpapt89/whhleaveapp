package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import entities.Roles;
import entities.Tache;
import entities.Utilitaire;
import persistence.HibernateUtil;
import persistence.RoleDAO;
import persistence.TacheDAO;

@ManagedBean
@ViewScoped
public class RolesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3761731263496436702L;
	private Roles roles;
	private List<String> tachesSelected;
	private List<Tache> allTaches;
	private List<SelectItem> tachesItems;
	private List<Roles> allRoles;

	public RolesBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {

		initialize();
		loadAllTaches();
		loadAllRoles();
		tachesItems = new ArrayList<>();
		for (Tache t : allTaches) {
			tachesItems.add(new SelectItem(t.getId(), t.getNomTache()));
		}

		System.out.println("Les taches sont " + allTaches.size());

	}

	public void saveUpdate() {

		boolean saved = false;
		// System.out.println("roles " + roles.getNomRole());
		populateRole();
		saved = RoleDAO.saveUpdateRole(roles);
		if (saved) {
			initialize();
			Utilitaire.afficherInformation("Opération réussie.");
			Utilitaire.updateForm("form");
		}

	}

	private void initialize() {

		roles = new Roles();
		tachesSelected = new ArrayList<>();
		loadAllTaches();
	}

	private void populateRole() {

		if (tachesSelected != null && !tachesSelected.isEmpty()) {

			for (String t : tachesSelected) {
				for (Tache ta : allTaches) {
					if (ta.getId() == Integer.parseInt(t)) {
						roles.getTaches().add(ta);
						break;
					}
				}
			}
		}
	}

	private void loadAllTaches() {
		allTaches = TacheDAO.loadAllTaches();
	}

	private void loadAllRoles() {
		allRoles = RoleDAO.loadAllRoles();
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public List<String> getTachesSelected() {
		return tachesSelected;
	}

	public void setTachesSelected(List<String> tachesSelected) {
		this.tachesSelected = tachesSelected;
	}

	public List<SelectItem> getTachesItems() {
		return tachesItems;
	}

	public void setTachesItems(List<SelectItem> tachesItems) {
		this.tachesItems = tachesItems;
	}

}

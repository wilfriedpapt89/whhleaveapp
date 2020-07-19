package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import entities.OperateurC;
import entities.Utilitaire;
import persistence.EmployeeDAO;

@ManagedBean
@ViewScoped
public class ProfilMeBean {

	@ManagedProperty(value = "#{appManagerBean}")
	private AppManagerBean appManagerBean;
	private HttpSession maSession;
	private OperateurC operateur;
	private List<SelectItem> operateursItems;
	private boolean userNameCheck;
	private boolean userLastNameCheck;
	private boolean userTelefonCheck;
	private boolean userPasswordCheck;
	private boolean userConfirmPasswordCheck;
	private boolean userEmailCheck;
	private boolean userReferantCheck;
	private String confirme;
	private long referant;

	@PostConstruct
	public void init() {

		maSession = Utilitaire.getSession();
		operateur = (OperateurC) maSession.getAttribute("sessionUser");
		if (operateur.getReferant() != null)
			referant = operateur.getReferant().getId();
		attribuateStatus(true);
		buildOperateurItems();
	}

	private void attribuateStatus(boolean status) {

		userNameCheck = status;
		userLastNameCheck = status;
		userEmailCheck = status;
		userTelefonCheck = status;
		userPasswordCheck = status;
		userConfirmPasswordCheck = status;
	}

	private void buildOperateurItems() {
		operateursItems = new ArrayList<>();
		operateursItems.add(new SelectItem(0, "Choisir mon référant"));
		for (OperateurC op : appManagerBean.getAllEmployee()) {
			if (op.getId() != operateur.getId())
				operateursItems.add(new SelectItem(op.getId(), op.getNom() + " - " + op.getPrenom()));
			else {
				operateursItems.add(new SelectItem(op.getId(), "Moi-même"));
			}
		}

		if (operateur.getReferant() != null) {
			referant = operateur.getReferant().getId();
		}

	}

	public void save() {

		if (operateur.getNom().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Remplir le champ 'Nom");
			Utilitaire.updateForm("form1:msg");
			return;
		} else if (operateur.getPrenom().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Remplir le champ 'Prénom");
			Utilitaire.updateForm("form1:msg");
			return;
		} else if (operateur.getEmail().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Remplir le champ 'Email");
			Utilitaire.updateForm("form1:msg");
			return;
		} else if (operateur.getTelefon().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Remplir le champ 'Téléphone");
			Utilitaire.updateForm("form1:msg");
			return;
		} else if (operateur.getPassword().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Remplir le champ 'Motde passe");
			Utilitaire.updateForm("form1:msg");
			return;
		} else if (!confirme.equals(operateur.getPassword())) {

			Utilitaire.afficherAttention(" A votre attention", " Veuillez confirmer votre mot de passe");
			Utilitaire.updateForm("form1:msg");
			return;
		}

		boolean saved = EmployeeDAO.saveOrUpdate(operateur);
		if (saved) {
			maSession.setAttribute("sessionUser", operateur);
			Utilitaire.afficherInformation("Informations modifiées");
			if (operateur.getReferant() != null)
				referant = operateur.getReferant().getId();
			confirme = "";
			attribuateStatus(true);
			Utilitaire.updateForm("form1");
		}
	}

	public boolean isUserReferantCheck() {
		return userReferantCheck;
	}

	public void setUserReferantCheck(boolean userReferantCheck) {
		this.userReferantCheck = userReferantCheck;
	}

	public AppManagerBean getAppManagerBean() {
		return appManagerBean;
	}

	public void setAppManagerBean(AppManagerBean appManagerBean) {
		this.appManagerBean = appManagerBean;
	}

	public List<SelectItem> getOperateursItems() {
		return operateursItems;
	}

	public void setOperateursItems(List<SelectItem> operateursItems) {
		this.operateursItems = operateursItems;
	}

	public long getReferant() {
		return referant;
	}

	public void setReferant(long referant) {
		this.referant = referant;
	}

	public void changeFlag() {
		userNameCheck = false;
	}

	public void changeFlag2() {
		userLastNameCheck = false;
	}

	public void changeFlag3() {
		userEmailCheck = false;
	}

	public void changeFlag4() {

		userTelefonCheck = false;
	}

	public void changeFlag5() {

		userPasswordCheck = false;
	}

	public void changeFlag6() {

		userReferantCheck = false;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public OperateurC getOperateur() {
		return operateur;
	}

	public void setOperateur(OperateurC operateur) {
		this.operateur = operateur;
	}

	public boolean isUserNameCheck() {
		return userNameCheck;
	}

	public void setUserNameCheck(boolean userNameCheck) {
		this.userNameCheck = userNameCheck;
	}

	public boolean isUserLastNameCheck() {
		return userLastNameCheck;
	}

	public void setUserLastNameCheck(boolean userLastNameCheck) {
		this.userLastNameCheck = userLastNameCheck;
	}

	public boolean isUserTelefonCheck() {
		return userTelefonCheck;
	}

	public void setUserTelefonCheck(boolean userTelefonCheck) {
		this.userTelefonCheck = userTelefonCheck;
	}

	public boolean isUserPasswordCheck() {
		return userPasswordCheck;
	}

	public void setUserPasswordCheck(boolean userPasswordCheck) {
		this.userPasswordCheck = userPasswordCheck;
	}

	public boolean isUserConfirmPasswordCheck() {
		return userConfirmPasswordCheck;
	}

	public void setUserConfirmPasswordCheck(boolean userConfirmPasswordCheck) {
		this.userConfirmPasswordCheck = userConfirmPasswordCheck;
	}

	public boolean isUserEmailCheck() {
		return userEmailCheck;
	}

	public void setUserEmailCheck(boolean userEmailCheck) {
		this.userEmailCheck = userEmailCheck;
	}

	public String getConfirme() {
		return confirme;
	}

	public void setConfirme(String confirme) {
		this.confirme = confirme;
	}

}

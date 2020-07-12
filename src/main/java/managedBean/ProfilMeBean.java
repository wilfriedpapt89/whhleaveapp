package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import entities.OperateurC;
import entities.Utilitaire;
import persistence.EmployeeDAO;

@ManagedBean
@ViewScoped
public class ProfilMeBean {

	private HttpSession maSession;
	private OperateurC operateur;
	private boolean userNameCheck;
	private boolean userLastNameCheck;
	private boolean userTelefonCheck;
	private boolean userPasswordCheck;
	private boolean userConfirmPasswordCheck;
	private boolean userEmailCheck;

	@PostConstruct
	public void init() {

		maSession = Utilitaire.getSession();
		operateur = (OperateurC) maSession.getAttribute("sessionUser");
		userNameCheck = true;
		userLastNameCheck = true;
		userEmailCheck = true;

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
		}

		boolean saved = EmployeeDAO.saveOrUpdate(operateur);
		if (saved) {
			maSession.setAttribute("sessionUser", operateur);
			Utilitaire.afficherInformation("Informations modifiées");
			Utilitaire.updateForm("form1");
		}
	}

	public void changeFlag() {
		System.out.println("get that method" + userNameCheck);
		userNameCheck = false;
	}

	public void changeFlag2() {
		System.out.println("get that method 2 " + userLastNameCheck);
		userLastNameCheck = false;
	}

	public void changeFlag3() {
		System.out.println("get that method 3 " + userEmailCheck);
		userEmailCheck = false;
	}

	public void changeFlag4() {

		userTelefonCheck = false;
	}

	public void changeFlag5() {

		userPasswordCheck = false;
	}

	public void changeFlag6() {
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

}

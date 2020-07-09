package managedBean;

import java.io.IOException;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sun.faces.facelets.tag.ui.UILibrary;

import entities.OperateurC;
import entities.Utilitaire;
import persistence.EmployeeDAO;

@ManagedBean
@ViewScoped
public class LoginBean {

	private String login;
	private String password;
	private HttpSession session;
	private OperateurC employee;

	public void authentificateUser() {

		if (login.isEmpty() || password.isEmpty()) {

			Utilitaire.afficherAttention("Erreur", "Remplissez tous les champs S.V.P");
			Utilitaire.updateComponents(Arrays.asList("msg"));
			return;
		}

		try {
			employee = EmployeeDAO.loginEmploye(login, password);
			if (employee != null) {
				System.out.println("Pas null ");
				Utilitaire.getSession().setAttribute("sessionUser", employee);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/dashboard.xhtml");
			} else {

				Utilitaire.afficherAttention("Erreur", "Identifiant ou mot de passe incorrect");
				Utilitaire.updateComponents(Arrays.asList("msg"));
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

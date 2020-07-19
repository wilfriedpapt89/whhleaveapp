package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.SimpleFormatter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

public class Utilitaire {

	public static final String ACCOUNT_SID = "AC7649c33cc9ff06d26de4b65f36ec5210";
	public static final String AUTH_TOKEN = "3af17d4e547d2d46c6c6b5015ec9645b";

	public static void afficherInformation(String message) {

		String titreToDisplay = "INFO";
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titreToDisplay, message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void afficherErreur(String message) {

		String titreToDisplay = "ERREUR";
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titreToDisplay, message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static HttpSession getSession() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return session;
	}

	public static Class<? extends Object> getSessionAttribute() {

		return null;
	}

	public static void afficherAttention(String titre, String message) {

		String titreToDisplay = "A VOTRE ATTENTION";
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titreToDisplay, message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void updateForm(String form) {

		PrimeFaces.current().ajax().update(form);
	}

	public static String incrementReference(String reference, int nbreCaractere) {

		int recup = Integer.parseInt(reference);
		recup += 1;
		String ref = (recup) + "";
		int diff = nbreCaractere - ref.length();
		if (ref.length() < nbreCaractere) {
			for (int i = 0; i < diff; i++) {
				ref = "0" + ref;
			}
		}
		return ref;
	}

	public static void updateComponents(List<String> components) {

		PrimeFaces.current().ajax().update(components);
	}

	public HttpSession getMySession() {

		HttpSession maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return maSession;
	}

	public OperateurC getMySessionUser() {

		OperateurC sessionUser = (OperateurC) getMySession().getAttribute("sessionUser");
		return sessionUser;
	}

	public static void sendSMS(String phoneTo, String messageBody) {

		// Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		// Message.creator(new PhoneNumber(phoneTo), new
		// PhoneNumber("+19142020629"), messageBody).create();
	}

	public static String generateOTP(int lenght) {
		String otp = "";
		Random r = new Random();

		for (int i = 0; i < lenght; i++) {
			otp += ((char) (r.nextInt(10) + 48)) + "";
		}

		return otp;
	}

	public static String transformUtilDateInString(Date debut, int mode) {
		// TODO Auto-generated method stub
		DateFormat format1 = null;
		if (mode == 1)
			format1 = new SimpleDateFormat("dd/MM/yyyy");
		else if (mode == 2)
			format1 = new SimpleDateFormat("E. dd/MM/yyyy", Locale.FRANCE);
		String result = format1.format(debut);

		return result;
	}
}

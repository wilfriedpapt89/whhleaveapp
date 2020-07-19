package managedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import entities.Conge;
import entities.OperateurC;
import entities.Utilitaire;
import persistence.CongeDAO;

@ManagedBean
@ViewScoped
public class AceuilBean {

	@ManagedProperty(value = "#{appManagerBean}")
	private AppManagerBean appManagerBean;
	private List<Conge> mesConges;
	private HttpSession mySession;
	private ArrayList<Conge> allDemandesSentToMe;
	private boolean interimat;
	private ArrayList<Conge> allMyDemandes;
	private ArrayList<Conge> allMyPendingDemandes;
	private int allDemandesSentToMeSize;
	private String mesCongeStatut;
	private String interimatStatut;
	private OperateurC monCompte;

	/**
	 * 
	 * Demandes de congé
	 * 
	 */

	private Conge congeSelected;
	private String motifDecision;

	public AceuilBean() {

	}

	@PostConstruct
	public void init() {

		mySession = Utilitaire.getSession();
		monCompte = (OperateurC) mySession.getAttribute("sessionUser");
		mesCongeStatut = "";
		interimatStatut = "";
		allMyDemandes = new ArrayList<>();
		allMyPendingDemandes = new ArrayList<>();
		loadAllDemandeSent();
	}

	private void loadAllDemandeSent() {
		// TODO Auto-generated method stub
		allDemandesSentToMe = new ArrayList<Conge>();
		allDemandesSentToMeSize = 0;
		monCompte = (OperateurC) mySession.getAttribute("sessionUser");
		for (Conge c : appManagerBean.getDemandeConges()) {
			if (c.getReferant() != null)
				if (monCompte.getId() == c.getReferant().getId()) {
					allDemandesSentToMe.add(c);
				}
		}
		allDemandesSentToMeSize = allDemandesSentToMe.size();
		loadMesDemandesValides();
		checkForMyDemande();
	}

	public void loadMesDemandesValides() {

		allMyDemandes.clear();
		allMyPendingDemandes.clear();

		for (Conge c : appManagerBean.getDemandeConges()) {
			if (monCompte.getId() == c.getEmployee().getId()) {
				allMyPendingDemandes.add(0, c);

			}
		}

		if (allMyPendingDemandes.size() == 1) {
			mesCongeStatut = allMyPendingDemandes.size() + " demande en cours";
		} else if (allMyPendingDemandes.size() > 1) {
			mesCongeStatut = allMyPendingDemandes.size() + " demandes en cours";
		} else if (allMyPendingDemandes.size() < 1) {
			mesCongeStatut = "Pas de demande";
		}

	}

	// public void loadMesDemandesRejettes() {
	//
	// allMyDemandesRejettes = new ArrayList<Conge>();
	// monCompte = (OperateurC) mySession.getAttribute("sessionUser");
	//
	// for (Conge c : appManagerBean.getCongesValides()) {
	// if (monCompte.getId() == c.getEmployee().getId()) {
	// allMyDemandesRejettes.add(c);
	// }
	// }
	// }

	public void checkStatus() {

		if (interimat) {

			interimatStatut = "";
		}
	}

	public void checkNotification() {

		if (appManagerBean.checkNotification(monCompte) > 0) {
			loadAllDemandeSent();
			Utilitaire.updateComponents(Arrays.asList("form1:analysedemande", " form1:mesconges"));
		}
	}

	public void checkForMyDemande() {

		if (appManagerBean.getCongesValides() != null && !appManagerBean.getCongesValides().isEmpty())
			for (Conge c : appManagerBean.getCongesValides())
				if (c.getEmployee().getId() == monCompte.getId())
					allMyDemandes.add(c);
	}

	public void changestateConge() {

		int state = 2;
		if (congeSelected != null) {

			Conge copyConge = new Conge(congeSelected);

			if (motifDecision == null || !motifDecision.isEmpty()) {
				motifDecision = "Pas de motif précisé.";
			}

			copyConge.setState(state);
			copyConge.setDateValidation(Calendar.getInstance().getTime());
			copyConge.setMotifValidation(motifDecision);
			boolean saved = CongeDAO.saveOrUpdate(copyConge);

			if (saved) {
				motifDecision = "";
				appManagerBean.removeFromDemandeConge(copyConge);
				loadAllDemandeSent();
				Utilitaire.afficherInformation("Décision enregistrée");
				Utilitaire.updateForm("form1");
			} else if (!saved) {
				Utilitaire.afficherAttention("A votre attention SVP", "LA décision n'a pas pu être enregistrée");
				Utilitaire.updateForm("form1");
			}
		}

	}

	public void changestateConge2() {

		int state = 3;
		if (congeSelected != null) {

			Conge copyConge = new Conge(congeSelected);

			copyConge.setState(state);
			copyConge.setDateValidation(Calendar.getInstance().getTime());
			copyConge.setMotifRejet(motifDecision);

			boolean saved = CongeDAO.saveOrUpdate(copyConge);

			if (saved) {
				motifDecision = "";
				appManagerBean.removeFromDemandeConge(copyConge);
				loadAllDemandeSent();
				Utilitaire.afficherInformation("Décision enregistrée");
				Utilitaire.updateForm("form1");
			} else if (!saved) {
				Utilitaire.afficherAttention("A votre attention SVP", "LA décision n'a pas pu être enregistrée");
				Utilitaire.updateForm("form1");
			}
		}

	}

	public AppManagerBean getAppManagerBean() {
		return appManagerBean;
	}

	public void setAppManagerBean(AppManagerBean appManagerBean) {
		this.appManagerBean = appManagerBean;
	}

	public List<Conge> getMesConges() {
		return mesConges;
	}

	public void setMesConges(List<Conge> mesConges) {
		this.mesConges = mesConges;
	}

	public ArrayList<Conge> getAllDemandesSentToMe() {
		return allDemandesSentToMe;
	}

	public void setAllDemandesSentToMe(ArrayList<Conge> allDemandesSentToMe) {
		this.allDemandesSentToMe = allDemandesSentToMe;
	}

	public boolean isInterimat() {
		return interimat;
	}

	public void setInterimat(boolean interimat) {
		this.interimat = interimat;
	}

	public ArrayList<Conge> getAllMyDemandes() {
		return allMyDemandes;
	}

	public void setAllMyDemandes(ArrayList<Conge> allMyDemandes) {
		this.allMyDemandes = allMyDemandes;
	}

	public ArrayList<Conge> getAllMyPendingDemandes() {
		return allMyPendingDemandes;
	}

	public void setAllMyPendingDemandes(ArrayList<Conge> allMyPendingDemandes) {
		this.allMyPendingDemandes = allMyPendingDemandes;
	}

	public String getMesCongeStatut() {
		return mesCongeStatut;
	}

	public void setMesCongeStatut(String mesCongeStatut) {
		this.mesCongeStatut = mesCongeStatut;
	}

	public ArrayList<Conge> getAllMyDemandesValides() {
		return allMyDemandes;
	}

	public void setAllMyDemandesValides(ArrayList<Conge> allMyDemandesValides) {
		this.allMyDemandes = allMyDemandesValides;
	}

	public int getAllDemandesSentToMeSize() {
		return allDemandesSentToMeSize;
	}

	public void setAllDemandesSentToMeSize(int allDemandesSentToMeSize) {
		this.allDemandesSentToMeSize = allDemandesSentToMeSize;
	}

	public String getMonCongeStatut() {
		return mesCongeStatut;
	}

	public void setMonCongeStatut(String monCongeStatut) {
		this.mesCongeStatut = monCongeStatut;
	}

	public String getInterimatStatut() {
		return interimatStatut;
	}

	public void setInterimatStatut(String interimatStatut) {
		this.interimatStatut = interimatStatut;
	}

	public Conge getCongeSelected() {
		return congeSelected;
	}

	public void setCongeSelected(Conge congeSelected) {
		this.congeSelected = congeSelected;
	}

	public String getMotifDecision() {
		return motifDecision;
	}

	public void setMotifDecision(String motifDecision) {
		this.motifDecision = motifDecision;
	}

}

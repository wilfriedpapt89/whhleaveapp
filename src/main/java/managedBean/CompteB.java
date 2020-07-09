package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.OperateurC;
import entities.Utilitaire;
import persistence.EmployeeDAO;

@ManagedBean
@ViewScoped
public class CompteB {

	private List<OperateurC> comptesNoValides;
	private List<OperateurC> allComptes;
	private List<OperateurC> compteSuspsendus;

	private List<OperateurC> comptesNoValidesSelecteds;
	private List<OperateurC> allComptesSelecteds;
	private List<OperateurC> compteSuspsendusSelecteds;

	@PostConstruct
	public void init() {

		loadAllComptes();
	}

	private void loadAllComptes() {
		comptesNoValidesSelecteds = null;
		compteSuspsendus = null;
		allComptesSelecteds = null;
		allComptes = EmployeeDAO.loadAllEmployee();
		comptesNoValides = new ArrayList<OperateurC>();
		compteSuspsendus = new ArrayList<>();
		if (allComptes != null && !allComptes.isEmpty()) {

			for (OperateurC op : allComptes) {

				if (!op.isSuspendu() && !op.isActif()) {
					comptesNoValides.add(op);
				}

				else if (op.isSuspendu()) {
					compteSuspsendus.add(op);
				}

			}
		}
	}

	public void changeComptesStatus() {

		if (comptesNoValidesSelecteds != null) {

			for (OperateurC op : comptesNoValidesSelecteds) {
				op.setActif(true);
			}

			boolean saved = EmployeeDAO.saveOrUpdate(comptesNoValidesSelecteds);
			if (saved) {
				Utilitaire.afficherInformation("Compte(s) activité(s)");
				loadAllComptes();
				Utilitaire.updateForm("form1");
			} else {
				Utilitaire.afficherAttention("A votre attention", "Echec de l'opération");
				Utilitaire.updateForm("form1:msg");
			}
		}
	}

	public List<OperateurC> getComptesNoValides() {
		return comptesNoValides;
	}

	public void setComptesNoValides(List<OperateurC> comptesNoValides) {
		this.comptesNoValides = comptesNoValides;
	}

	public List<OperateurC> getAllComptes() {
		return allComptes;
	}

	public void setAllComptes(List<OperateurC> allComptes) {
		this.allComptes = allComptes;
	}

	public List<OperateurC> getCompteSuspsendus() {
		return compteSuspsendus;
	}

	public void setCompteSuspsendus(List<OperateurC> compteSuspsendus) {
		this.compteSuspsendus = compteSuspsendus;
	}

	public List<OperateurC> getComptesNoValidesSelecteds() {
		return comptesNoValidesSelecteds;
	}

	public void setComptesNoValidesSelecteds(List<OperateurC> comptesNoValidesSelecteds) {
		this.comptesNoValidesSelecteds = comptesNoValidesSelecteds;
	}

	public List<OperateurC> getAllComptesSelecteds() {
		return allComptesSelecteds;
	}

	public void setAllComptesSelecteds(List<OperateurC> allComptesSelecteds) {
		this.allComptesSelecteds = allComptesSelecteds;
	}

	public List<OperateurC> getCompteSuspsendusSelecteds() {
		return compteSuspsendusSelecteds;
	}

	public void setCompteSuspsendusSelecteds(List<OperateurC> compteSuspsendusSelecteds) {
		this.compteSuspsendusSelecteds = compteSuspsendusSelecteds;
	}

}

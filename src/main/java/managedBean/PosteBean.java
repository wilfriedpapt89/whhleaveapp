package managedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import entities.Poste;
import entities.Utilitaire;
import persistence.PosteDAO;

@ManagedBean
@ViewScoped
public class PosteBean {

	@ManagedProperty(value = "#{appManagerBean}")
	private AppManagerBean appManagerBean;
	private Poste poste;
	private Poste posteSelected;
	private List<Poste> postes;

	public PosteBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		poste = new Poste();
		loadAll();

	}

	public void save() {

		if (poste.getDesignation() != null && poste.getDesignation().trim().isEmpty()) {
			Utilitaire.afficherAttention("A votre attention", "Le libellé du poste est vide");
			Utilitaire.updateForm("form1:msg");
			return;
		}
		boolean saved = PosteDAO.saveOrUpdateTache(poste);
		Poste copy = new Poste(poste.getId(), poste.getReference(), poste.getDesignation());
		appManagerBean.addPosteSaved(copy);
		if (saved) {
			Utilitaire.afficherInformation("Poste enregistré");
			poste = new Poste();
			loadAll();
			Utilitaire.updateComponents(Arrays.asList("form1"));
		} else {
			Utilitaire.afficherAttention("Oups", "Echec de l'opération");
		}

	}

	public void delete() {

		posteSelected.setDeleted(true);
		boolean deleted = PosteDAO.saveOrUpdateTache(posteSelected);
		if (deleted) {
			loadAll();
			Utilitaire.afficherInformation("Poste supprimé");
			Utilitaire.updateForm("form1:msg");
		}
	}

	public void getSelectItem() {

		if (posteSelected != null) {
			poste = new Poste(posteSelected.getId(), posteSelected.getReference(), posteSelected.getDesignation());
			Utilitaire.updateForm("form1");
			return;
		}
	}

	private void loadAll() {

		postes = new ArrayList<>();
		if (appManagerBean.getAllPostes() != null && !appManagerBean.getAllPostes().isEmpty())
			for (Poste p : appManagerBean.getAllPostes()) {
				postes.add(p);
			}

		postes = postes.stream().filter(fil -> {
			if (!fil.isDeleted()) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		// Collections.reverse(postes);

		if (postes != null && !postes.isEmpty()) {
			poste.setReference(Utilitaire.incrementReference(postes.get(0).getReference(), 3));
		} else {
			poste.setReference(Utilitaire.incrementReference("0", 3));
		}
	}

	public Poste getPoste() {
		return poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}

	public Poste getPosteSelected() {
		return posteSelected;
	}

	public void setPosteSelected(Poste posteSelected) {
		this.posteSelected = posteSelected;
	}

	public List<Poste> getPostes() {
		return postes;
	}

	public void setPostes(List<Poste> postes) {
		this.postes = postes;
	}

	public AppManagerBean getAppManagerBean() {
		return appManagerBean;
	}

	public void setAppManagerBean(AppManagerBean appManagerBean) {
		this.appManagerBean = appManagerBean;
	}

}

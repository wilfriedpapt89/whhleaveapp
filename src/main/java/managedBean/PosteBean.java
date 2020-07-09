package managedBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Poste;
import entities.Utilitaire;
import persistence.PosteDAO;

@ManagedBean
@ViewScoped
public class PosteBean {

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

		boolean saved = PosteDAO.saveOrUpdateTache(poste);
		if (saved) {
			Utilitaire.afficherInformation("Poste enregistr�");
			poste = new Poste();
			loadAll();
		} else {
			Utilitaire.afficherAttention("Oups", "Echec de l'op�ration");
		}
		Utilitaire.updateComponents(Arrays.asList("form1"));
	}

	public void delete() {

		posteSelected.setDeleted(true);
		boolean deleted = PosteDAO.saveOrUpdateTache(posteSelected);
		if (deleted) {
			loadAll();
			Utilitaire.afficherInformation("Poste supprim�");
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

		postes = PosteDAO.loadAll();
		postes = postes.stream().filter(fil -> {
			if (!fil.isDeleted()) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		Collections.reverse(postes);
		
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

}
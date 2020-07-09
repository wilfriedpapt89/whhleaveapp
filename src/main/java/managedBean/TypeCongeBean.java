package managedBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Poste;
import entities.TypeConge;
import entities.Utilitaire;
import persistence.PosteDAO;
import persistence.TypeCongeDAO;

@ManagedBean
@ViewScoped
public class TypeCongeBean {

	private TypeConge typeConge;
	private TypeConge typeCongeSelected;
	private List<TypeConge> typeConges;

	public TypeCongeBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		typeConge = new TypeConge();
		loadAll();

	}

	public void save() {

		boolean saved = TypeCongeDAO.saveOrUpdateTache(typeConge);
		if (saved) {
			Utilitaire.afficherInformation("Type de congé enregistré");
			typeConge = new TypeConge();
			loadAll();
		} else {
			Utilitaire.afficherAttention("Oups", "Echec de l'opération");
		}
		Utilitaire.updateComponents(Arrays.asList("form1"));
	}

	public void delete() {

		typeCongeSelected.setDeleted(true);
		boolean deleted = TypeCongeDAO.saveOrUpdateTache(typeCongeSelected);
		if (deleted) {
			loadAll();
			Utilitaire.afficherInformation("Type de congé supprimé");
			Utilitaire.updateForm("form1:msg");
		}
	}

	public void getSelectItem() {

		if (typeCongeSelected != null) {
			typeConge = new TypeConge(typeCongeSelected.getId(), typeCongeSelected.getReference(),
					typeCongeSelected.getDesignation());
			Utilitaire.updateForm("form1");
			return;
		}
	}

	private void loadAll() {

		typeConges = TypeCongeDAO.loadAll();
		typeConges = typeConges.stream().filter(fil -> {
			if (!fil.isDeleted()) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		Collections.reverse(typeConges);

		if (typeConges != null && !typeConges.isEmpty()) {
			typeConge.setReference(Utilitaire.incrementReference(typeConges.get(0).getReference(), 3));
		} else {
			typeConge.setReference(Utilitaire.incrementReference("0", 3));
		}
	}

	public TypeConge getTypeConge() {
		return typeConge;
	}

	public void setTypeConge(TypeConge typeConge) {
		this.typeConge = typeConge;
	}

	public TypeConge getTypeCongeSelected() {
		return typeCongeSelected;
	}

	public void setTypeCongeSelected(TypeConge typeCongeSelected) {
		this.typeCongeSelected = typeCongeSelected;
	}

	public List<TypeConge> getTypeConges() {
		return typeConges;
	}

	public void setTypeConges(List<TypeConge> typeConges) {
		this.typeConges = typeConges;
	}

}

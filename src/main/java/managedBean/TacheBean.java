package managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Tache;
import entities.Utilitaire;
import persistence.TacheDAO;

@ManagedBean
@ViewScoped
public class TacheBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6639588161711998562L;
	private Tache tache;
	private List<Tache> taches;

	@PostConstruct
	public void init() {
		initialize();
	}

	public void saveTache() {

		boolean saved = false;
		System.out.println("tache designation " + tache.getNomTache());
		saved = TacheDAO.saveOrUpdateTache(tache);
		if (saved) {
			Utilitaire.updateForm("form");
			Utilitaire.afficherInformation("Opération réussie");
			initialize();
		}
	}

	public void initialize() {
		tache = new Tache();
	}

	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	public List<Tache> getTaches() {
		return taches;
	}

	public void setTaches(List<Tache> taches) {
		this.taches = taches;
	}
}

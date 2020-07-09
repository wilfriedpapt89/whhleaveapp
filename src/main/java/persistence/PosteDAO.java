package persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Poste;

public class PosteDAO {

	public static boolean saveOrUpdateTache(Poste poste) {

		boolean saved = false;
		Session session = HibernateUtil.getSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(poste);
		try {
			tr.commit();
			saved = true;
		} catch (Exception e) {
			// TODO: handle exception
			saved = false;
			tr.rollback();
		}

		return saved;
	}

	public static List<Poste> loadAll() {
		List<Poste> postes = null;
		Session session = HibernateUtil.getSession();
		postes = HibernateUtil.loadAllData(Poste.class, session);
		postes = postes.stream().filter(fil -> !fil.isDeleted()).collect(Collectors.toList());
		return postes;
	}
}

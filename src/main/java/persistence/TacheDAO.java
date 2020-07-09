package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Tache;

public class TacheDAO {

	public static boolean saveOrUpdateTache(Tache tache) {

		boolean saved = false;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(tache);
		tr.commit();
		saved = true;

		return saved;
	}

	public static List<Tache> loadAllTaches() {
		List<Tache> taches = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		taches = HibernateUtil.loadAllData(Tache.class, session);
		return taches;
	}
}

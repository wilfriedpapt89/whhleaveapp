package persistence;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Conge;
import entities.Tache;

public class CongeDAO {

	public static boolean saveOrUpdate(Conge congE) {

		boolean saved = false;

		Session session = HibernateUtil.getSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(congE);
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

	public static List<Conge> loadAll() {
		List<Conge> conges = null;
		Session session = HibernateUtil.getSession();
		conges = HibernateUtil.loadAllData(Conge.class, session);
		return conges;
	}

	public static CopyOnWriteArrayList<Conge> loadAll2() {
		List<Conge> conges = null;
		CopyOnWriteArrayList<Conge> congesResult = null;
		Session session = HibernateUtil.getSession();
		conges = HibernateUtil.loadAllData(Conge.class, session);
		if (conges != null) {
			congesResult = new CopyOnWriteArrayList<>();
			for (Conge c : conges) {
				congesResult.add(c);
			}
		}
		return congesResult;
	}
}

package persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.TypeConge;

public class TypeCongeDAO {

	public static boolean saveOrUpdateTache(TypeConge typeConge) {

		boolean saved = false;
		Session session = HibernateUtil.getSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(typeConge);
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

	public static List<TypeConge> loadAll() {
		List<TypeConge> typeConges = null;
		Session session = HibernateUtil.getSession();
		typeConges = HibernateUtil.loadAllData(TypeConge.class, session);
		typeConges = typeConges.stream().filter(fil -> !fil.isDeleted()).collect(Collectors.toList());
		return typeConges;
	}
}

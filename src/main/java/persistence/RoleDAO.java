package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Roles;

public class RoleDAO {

	public static boolean saveUpdateRole(Roles role) {

		boolean saved = false;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = session.beginTransaction();

		session.saveOrUpdate(role);
		tr.commit();
		saved = true;
		return saved;

	}

	public static <T> List<Roles> loadAllRoles() {

		List<Roles> roles = new ArrayList<Roles>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		roles = HibernateUtil.loadAllData(Roles.class, session);
		return roles;
	}

}

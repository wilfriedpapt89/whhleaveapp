package persistence;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entities.OperateurC;

public class EmployeeDAO {

	public static boolean saveOrUpdate(OperateurC employee) {

		boolean saved = false;
		Session session = HibernateUtil.getSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(employee);
		tr.commit();
		saved = true;
		return saved;
	}

	public static boolean saveOrUpdate(List<OperateurC> operateurs) {

		boolean saved = false;
		Session session = HibernateUtil.getSession();
		Transaction tr = session.beginTransaction();
		for (OperateurC op : operateurs)
			session.saveOrUpdate(op);
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

	public static List<OperateurC> loadAllEmployee() {
		List<OperateurC> employees = null;
		Session session = HibernateUtil.getSession();
		employees = HibernateUtil.loadAllData(OperateurC.class, session);
		return employees;
	}

	public static CopyOnWriteArrayList<OperateurC> loadAllValidateEmployee2() {
		List<OperateurC> employees = null;
		CopyOnWriteArrayList<OperateurC> employeesResult = null;
		Session session = HibernateUtil.getSession();
		employees = HibernateUtil.loadAllData(OperateurC.class, session);

		if (employees != null && !employees.isEmpty()) {
			employeesResult = new CopyOnWriteArrayList<>();
			for (OperateurC op : employees) {
				if (op.isActif() && !op.isSuspendu())
					employeesResult.add(0, op);
			}
		}
		return employeesResult;
	}

	public static OperateurC loginEmploye(String login, String password) {
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(
				"from OperateurC WHERE email Like :login AND password Like :password AND actif = :actif AND suspendu = :suspendu");
		q.setParameter("login", login);
		q.setParameter("password", password);
		q.setParameter("actif", true);
		q.setParameter("suspendu", false);
		OperateurC employee = null;
		try {
			employee = (OperateurC) q.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			employee = null;
		}

		return employee;
	}
}

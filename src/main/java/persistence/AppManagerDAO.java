package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import entities.Conge;

public class AppManagerDAO {

	public static List<Conge> loadAllConge() {

		List<Conge> allConges = new ArrayList<Conge>();
		Query query = HibernateUtil.getSession().createQuery("from Conge c ");
		allConges = query.list();
		return allConges;
	}
}

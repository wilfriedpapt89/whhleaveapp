package persistence;

import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import entities.Conge;
import entities.Exercice;
import entities.OperateurC;
import entities.Poste;
import entities.TypeConge;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {

			Configuration configuration = new Configuration();

			Properties settings = new Properties();
			settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
			settings.put(Environment.URL, "jdbc:mysql://us-cdbr-east-02.cleardb.com/heroku_b17393d74fa243e?reconnect=true");
//			settings.put(Environment.URL, "jdbc:mysql://localhost:3306/myholidz");
//			settings.put(Environment.USER, "root");
//			settings.put(Environment.PASS, "root");
			settings.put(Environment.USER, "b033fa04b8bf10");
			settings.put(Environment.PASS, "403b2de2");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//			settings.put(Environment.SHOW_SQL, "true");
			// settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,
			// "thread");
			settings.put(Environment.HBM2DDL_AUTO, "update");

			configuration.setProperties(settings);

			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
					.build();

			configuration.addAnnotatedClass(OperateurC.class);
			configuration.addAnnotatedClass(Poste.class);
			configuration.addAnnotatedClass(TypeConge.class);
			configuration.addAnnotatedClass(Conge.class);
			configuration.addAnnotatedClass(Exercice.class);
			sessionFactory = configuration.buildSessionFactory(registry);
		}

		return sessionFactory;
	}

	public static Session getSession() {

		Session session = null;
		if (threadLocal.get() == null) {
			// Create Session object
			session = getSessionFactory().openSession();
			threadLocal.set(session);
		} else {
			session = threadLocal.get();
		}
		return session;
	}

	public static <T> List<T> loadAllData(Class<T> type, Session session) {

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		List<T> data = session.createQuery(criteria).getResultList();
		return data;
	}
}

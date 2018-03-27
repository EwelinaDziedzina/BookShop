package controller;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Book;

public class HibernateUtil {
	
	private HibernateUtil() {
		
	}
	
	private static SessionFactory sessionFactory;
	
	static {
		Configuration config = new Configuration().configure();
		config.addAnnotatedClass(Book.class);
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		
		sessionFactory = config.buildSessionFactory(builder.build());
	}
	
	protected static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}

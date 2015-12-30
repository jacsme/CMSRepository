package com.wom.cms.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUtil {

	public static Session session;
	public static Transaction tx;
	 
	public static Session callSession(SessionFactory sessionFactory){
		
		session = sessionFactory.openSession();
		tx = session.getTransaction();
		session.beginTransaction();
		
		return session;
	}
	
	public static void callCommit(SessionFactory sessionFactory){
		session.flush();
		tx.commit();
	}
	
	public static void callClose(SessionFactory sessionFactory){
		session.close();
		
	}
	
	public static void callCommitClose(SessionFactory sessionFactory){
		tx.commit();
		session.close();
	}
	
	public static void callRollBack(SessionFactory sessionFactory){
		tx.rollback();
	}
	
	
}


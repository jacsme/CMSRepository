package com.wom.cms.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUtil {

	public static Session session;
	public static Transaction tx;
	 
	public static Session callSession(SessionFactory sessionFactory){
		session = sessionFactory.openSession();
		//tx = session.getTransaction();
		//session.beginTransaction();
		
		return session;
	}
	
	public static void callCommit(Session session){
		//tx = session.getTransaction();
		//tx.commit();
		session.flush();
		session.clear();
	}
	
	public static void callClose(Session session){
		session.flush();
		session.clear();
		//session.close();
	}
	
	public static void callCommitClose(Session session){
		session.flush();
		session.clear();
	}
}


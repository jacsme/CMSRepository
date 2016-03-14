package com.wom.cms.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUtil {

	public static Session session;
	public static Transaction tx;
	 
	public static Session callSession(SessionFactory sessionFactory){
		session = sessionFactory.openSession();
		return session;
	}
	
	public static void callCommit(Session session){
		session.flush();
		session.clear();
		session.close();
	}
	
	public static void callClose(Session session){
		session.flush();
		session.clear();
		session.close();
	}
	
	public static void callCommitClose(Session session){
		session.flush();
		session.clear();
		session.close();
	}
}


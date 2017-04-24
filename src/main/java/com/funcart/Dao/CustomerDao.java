package com.funcart.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import com.funcart.customer.Customer;

@Component
public class CustomerDao {
	
	public boolean insertCustomer(Customer customer){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("signupUnit");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		boolean flag = true;
		try{
			tx.begin();
			em.persist(customer);
			em.flush();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			flag = false;
		}finally{
			em.close();
		}
		return flag;
	}
	
	public boolean checkingCustomer(Customer customer){
		
		return true;
	}

}

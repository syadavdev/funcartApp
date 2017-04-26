package com.funcart.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.funcart.domain.Customer;

@Repository
public class CustomerDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(rollbackOn=Exception.class)
	public boolean insertCustomer(Customer customer)throws Exception{
		em.persist(customer);
		return true;
	}

	public boolean checkingCustomer(Customer customer){
		
		boolean flag = true;
		Query q = em.createQuery("from Customer u where u.username=:name and u.password=:pwd");
		q.setParameter("name",customer.getUsername());
		q.setParameter("pwd",customer.getPassword()); 
       	Customer returnCustomer =(Customer) q.getSingleResult();
       	if(returnCustomer == null)
       		flag = false;
       	return flag;
	}
}

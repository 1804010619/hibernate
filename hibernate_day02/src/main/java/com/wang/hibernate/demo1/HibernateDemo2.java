package com.wang.hibernate.demo1;

import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.junit.Test;

import javax.security.auth.login.CredentialException;
import java.util.List;

/**
 * @author wanglimin
 * @date 2020-05-28 09:21
 *
 * Hibernate其他api
 */
public class HibernateDemo2 {
    /**
     * 测试Query
     */
    @Test
    public void demo1(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        //通过session获得Query接口
        //简单查询
//        String hql = "from Customer";
        //条件查询
//        String hql = "from Customer where cust_name like ?";
        //分页
        String hql = "from Customer";
        Query query = currentSession.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(2);
        //query.setParameter(0,"王%");
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 测试Criteria
     */
    @Test
    public void demo2(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        //通过session获得Criteria的对象
//        Criteria criteria = currentSession.createCriteria(Customer.class);
//        List<Customer> list = criteria.list();
        //条件查询
//        Criteria criteria = currentSession.createCriteria(Customer.class);
//        criteria.add(Restrictions.like("cust_name","李%"));
//        List<Customer> list = criteria.list();
        //分页
        Criteria criteria = currentSession.createCriteria(Customer.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(2);
        List<Customer> list = criteria.list();

        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }
}

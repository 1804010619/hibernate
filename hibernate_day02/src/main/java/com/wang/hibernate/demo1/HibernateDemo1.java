package com.wang.hibernate.demo1;

import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author wanglimin
 * @date 2020-05-28 07:02
 *
 * Hibernate的主键生成策略
 */
public class HibernateDemo1 {
    /**
     * 演示increment
     */
    @Test
    public void demo1(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("王西");
        session.save(customer);

        transaction.commit();
        session.close();
    }

    /**
     * 演示increment
     */
    @Test
    public void demo2(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("王北");
        session.save(customer);

        transaction.commit();
        session.close();
    }

}

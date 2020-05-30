package com.wang.hibernate.demo1;

import com.wang.hibernate.domain.Customer;
import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author wanglimin
 * @date 2020-05-29 10:39
 *
 * Sql查询
 */
public class HibernateDemo3 {
    /**
     *
     */
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

//        NativeQuery query = session.createSQLQuery("select * from cst_customer");
//        List<Object[]> list = query.list();

        NativeQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
        sqlQuery.addEntity(Customer.class);
        List<Customer> list = sqlQuery.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }
}

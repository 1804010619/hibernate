package com.wang.hibernate.demo1;

import com.wang.hibernate.domain.Customer;
import com.wang.hibernate.domain.LinkMan;
import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.List;

/**
 * @author wanglimin
 * @date 2020-05-29 09:06
 *
 * QBC的查询
 */
public class HibernateDemo2 {
    /**
     * 简单查询
     */
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //获得Criteria的对象
        Criteria criteria = session.createCriteria(Customer.class);
        List<Customer> list = criteria.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 排序查询
     */
    @Test
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //排序查询
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.addOrder(Order.desc("cust_id"));
        List<Customer> list = criteria.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 分页查询
     */
    @Test
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //分页查询
        Criteria criteria = session.createCriteria(LinkMan.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);
        List<LinkMan> list = criteria.list();
        for(LinkMan linkMan : list){
            System.out.println(linkMan);
        }
        transaction.commit();
    }

    /**
     * 条件查询查询
     */
    @Test
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //条件查询
        Criteria criteria = session.createCriteria(Customer.class);
        //设置条件
        criteria.add(Restrictions.eq("cust_source","小广告"));
        List<Customer> list = criteria.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 统计查询
     */
    @Test
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Customer.class);
        /**
         * add              :普通条件，where后面的条件
         * addOrder         :排序
         * setProjection    :聚合函数和group by ,having
         */
        criteria.setProjection(Projections.rowCount());
        Long num = (Long) criteria.uniqueResult();
        System.out.println(num);
        transaction.commit();
    }

    /**
     * 离线条件查询
     */
    @Test
    public void demo6(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        detachedCriteria.add(Restrictions.like("cust_name","王%"));

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);

        List<Customer> list = criteria.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }
}

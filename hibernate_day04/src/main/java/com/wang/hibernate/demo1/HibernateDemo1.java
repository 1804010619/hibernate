package com.wang.hibernate.demo1;

import com.wang.hibernate.domain.Customer;
import com.wang.hibernate.domain.LinkMan;
import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author wanglimin
 * @date 2020-05-29 07:44
 *
 * HQL查询方式的测试类
 */
public class HibernateDemo1 {
    /**
     * 初始化数据
     */
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //创建一个客户
        Customer customer = new Customer();
        customer.setCust_name("王五");
        for(int i=0;i<10;i++){
            LinkMan linkMan = new LinkMan();
            linkMan.setLkm_name("wangwu联系人"+i);
            linkMan.setCustomer(customer);
            customer.getLinkMans().add(linkMan);
            session.save(linkMan);
        }
        session.save(customer);

        transaction.commit();
    }

    /**
     * HQL的简单查询
     */
    @Test
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //简单查询
        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();

        //sql中支持*的写法，但HQL中不支持*的写法

        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 别名查询
     */
    @Test
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //别名查询
//        Query query = session.createQuery("from Customer c");
//        List<Customer> list = query.list();

        Query query = session.createQuery("select c from Customer c");
        List<Customer> list = query.list();

        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 排序查询
     */
    @Test
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //排序查询
        List<Customer> list = session.createQuery("from Customer order by cust_id desc ").list();

        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 条件查询
     */
    @Test
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //条件查询
        //一、按位置绑定：根据参数的位置进行绑定
//        Query query = session.createQuery("from Customer where cust_name='张三'");
//        List<Customer> list = query.list();
        //二、按名称绑定：
        Query query = session.createQuery("from Customer where cust_name= :aaa");
        query.setParameter("aaa","张三");
        List<Customer> list = query.list();
        for(Customer customer : list){
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 投影查询:查询某个对象的某个或某些属性
     */
    @Test
    public void demo6(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //单个属性
//        Query query = session.createQuery("select c.cust_name from Customer c");
//        List<Object> list = query.list();
//        for(Object obj : list){
//            System.out.println(obj);
//        }
        //多个属性
//        Query query = session.createQuery("select c.cust_name,c.cust_source from Customer c");
//        List<Object[]> list = query.list();
//
//        for(Object[] objects : list){
//            System.out.println(Arrays.toString(objects));
//        }

        //查询多个属性，封装到对象中
        List<Customer> list = session.createQuery("select new Customer(cust_name,cust_source) from Customer").list();
        for (Customer c : list) {
            System.out.println(c);
        }

        transaction.commit();
    }

    /**
     * 分页查询
     */
    @Test
    public void demo7(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //分页插叙
        Query query = session.createQuery("from LinkMan");
        query.setFirstResult(10);
        query.setMaxResults(10);
        List<LinkMan> list = query.list();
        for (LinkMan linkMan : list) {
            System.out.println(linkMan);
        }

        transaction.commit();
    }

    /**
     * 分组统计查询
     */
    @Test
    public void demo8(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //集合函数的使用：count(),max(),min(),avg(),sum()
//        Query query = session.createQuery("select count(*) from Customer");
//        Long res = (Long) query.uniqueResult();
//        System.out.println(res);

        //分组统计
        Query query = session.createQuery("select cust_source,count (*) from Customer group by cust_source");
        List<Object[]> list = query.list();
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }

        transaction.commit();
    }

    /**
     * HQL多表查询
     */
    @Test
    public void demo9(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //HQL普通内连接
//        List<Object[]> list = session.createQuery("from Customer c inner join c.linkMans").list();
//        for(Object[] objects : list){
//            System.out.println(Arrays.toString(objects));
//        }

        //HQL迫切内连接：其实就是在普通内连接inner join 后添加一个fetch.
        //通知Hibernate，将另一个对象中的数据封装到对象中
        List<Customer> list = session.createQuery("select distinct c from Customer c inner join fetch c.linkMans").list();
        for(Customer customer : list){
            System.out.println(customer);
        }
        transaction.commit();
    }


}

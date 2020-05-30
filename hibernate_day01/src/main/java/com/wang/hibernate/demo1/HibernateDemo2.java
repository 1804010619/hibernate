package com.wang.hibernate.demo1;

import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author wanglimin
 * @date 2020-05-27 16:07
 *
 * Hibernate工具类的测试
 */
public class HibernateDemo2 {
    @Test
    public void demo(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("hello");
        session.save(customer);

        transaction.commit();
        session.close();
    }


    /**
     * 查询
     * get方法和load方法的区别
     * get方法：采用的是立即加载，执行到这行代码的时候，就会马上发送sql语句去查询
     *         查询后返回的是真实对象本身。
     *         查询一个找不到的对象返回null
     * load方法：采用的是延迟加载（懒加载），执行到这行代码的时候不会发送sql语句，当真正使用这个对象的时候才会发送sql语句
     *          查询后返回的是代理对象
     *          查询一个找不到的对象时，返回一个ObjectNotFoundException
     */
    @Test
    public void demo2(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
//      使用get方法
//        Customer customer = session.get(Customer.class, 1L);
//        System.out.println(customer);

        //使用load方法
        Customer customer = session.load(Customer.class, 1L);
        System.out.println(customer);

        transaction.commit();
        session.close();
    }

    /**
     * 修改操作
     */
    @Test
    public void demo3(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //直接创建对象进行修改
//        Customer customer = new Customer();
//        customer.setCust_id(1L);
//        customer.setCust_name("hi");
//        session.update(customer);

        //先查询再修改
        Customer customer = session.get(Customer.class, 1L);
        customer.setCust_name("王小贱");
        session.update(customer);


        transaction.commit();
        session.close();
    }

    /**
     * 删除操作
     *
     */
    @Test
    public void demo4(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //直接创建对象删除
//        Customer customer = new Customer();
//        customer.setCust_id(1L);
//        session.delete(customer);

        //先查询再删除
        Customer customer = session.get(Customer.class, 2L);
        session.delete(customer);


        transaction.commit();
        session.close();
    }

}

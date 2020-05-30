package com.wang.hibernate.demo1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



/**
 * @author wanglimin
 * @date 2020-05-27 13:24
 *
 * Hibernate入门
 */
public class HibernateDemo1 {
    @Test
    //保存客户的案例
    public void demo1(){
        //1.加载hibernate核心配置文件
        Configuration configuration = new Configuration().configure();
        //2.创建一个SessionFactory对象：类似于JDBC中连接池
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        //3.通过SessionFactory获取session对象：类似于JDBC中Connection
        Session session = sessionFactory.openSession();
        //4.手动开启事务
        Transaction transaction = session.beginTransaction();
        //5.编写代码

        Customer customer = new Customer();
        customer.setCust_name("张三");
        session.save(customer);

        //6.事务提交
        transaction.commit();
        //7.资源释放
        session.close();

    }
}

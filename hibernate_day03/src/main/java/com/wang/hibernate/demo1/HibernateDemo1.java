package com.wang.hibernate.demo1;

import com.wang.hibernate.domain.Customer;
import com.wang.hibernate.domain.LinkMan;
import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author wanglimin
 * @date 2020-05-28 17:41
 *
 * 一对多的测试类
 */
public class HibernateDemo1 {
    /**
     * 保存两个客户和三个联系人，并且建立好关系
     */
    @Test
    public void demo1(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //创建两个客户
        Customer customer1 = new Customer();
        customer1.setCust_name("张三");
        Customer customer2 = new Customer();
        customer2.setCust_name("李四");

        //创建三个联系人
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("one");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("two");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("three");

        //设置关系
        linkMan1.setCustomer(customer1);
        linkMan2.setCustomer(customer1);
        linkMan3.setCustomer(customer2);

        customer1.getLinkMans().add(linkMan1);
        customer1.getLinkMans().add(linkMan2);
        customer2.getLinkMans().add(linkMan3);

        //保存数据
        session.save(linkMan1);
        session.save(linkMan2);
        session.save(linkMan3);
        session.save(customer1);
        session.save(customer2);

        transaction.commit();
    }

    /**
     * 一对多关系只保存一遍是否可以？
     */
    @Test
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("张三");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("one");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        //只保存一边不可以，报一个瞬时对象异常：持久态对象关联了一个瞬时对象
        //session.save(customer);

        transaction.commit();
    }

    /**
     * 级联保存或更新操作：
     *      保存客户级联联系人:操作的主体对象是客户对象，需要在Customer.hbm.xml中进行配置
     *      <set name="linkMans" inverse="true" cascade="save-update">
     */
    @Test
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("张三");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("one");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        session.save(customer);

        transaction.commit();
    }

  /**
   * 级联保存或更新操作： 保存联系人级联客户:操作的主体对象是联系人对象，需要在LinkMan.hbm.xml中进行配置
   * <many-to-one name="customer" class="com.wang.hibernate.domain.Customer" column="lkm_cust_id" cascade="save-update"></many-to-one>
   */
  @Test
  public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("李四");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("two");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        session.save(linkMan);

        transaction.commit();
    }

}

package com.wang.hibernate.demo2;

import com.wang.hibernate.domain.Role;
import com.wang.hibernate.domain.User;
import com.wang.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

/**
 * @author wanglimin
 * @date 2020-05-28 20:56
 *
 * 测试Hibernate多对多的映射
 */
public class HibernateDemo2 {
    /**
     * 保存多条记录：保存多个用户和角色
     */
    @Test
    public void demo1(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        //创建两个用户
        User user1 = new User();
        user1.setUser_code("aaa");
        User user2 = new User();
        user2.setUser_code("bbb");

        //创建三个角色
        Role role1 = new Role();
        role1.setRole_name("研发部");
        Role role2 = new Role();
        role2.setRole_name("市场部");
        Role role3 = new Role();
        role3.setRole_name("公关部");


        //设置双向的关联
        user1.getRoles().add(role1);
        user1.getRoles().add(role2);
        user2.getRoles().add(role3);
        user2.getRoles().add(role2);

        role1.getUsers().add(user1);
        role2.getUsers().add(user1);
        role2.getUsers().add(user2);
        role3.getUsers().add(user2);

        //保存:多对多建立了双向关系必须有一方放弃外键维护
        currentSession.save(user1);
        currentSession.save(user2);
        currentSession.save(role1);
        currentSession.save(role2);
        currentSession.save(role3);


        transaction.commit();
    }

    /**
     * 级联保存
     */
    @Test
    public void demo2(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        User user1 = new User();
        user1.setUser_code("aaa");
        Role role1 = new Role();
        role1.setRole_name("研发部");
        user1.getRoles().add(role1);
        role1.getUsers().add(user1);
        currentSession.save(user1);

        transaction.commit();
    }

    /**
     * 级联删除
     */
    @Test
    public void demo3(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        User user = currentSession.get(User.class, 1L);
        currentSession.delete(user);

        transaction.commit();
    }

}

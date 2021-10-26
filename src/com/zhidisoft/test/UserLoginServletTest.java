package com.zhidisoft.test;

import com.zhidisoft.system.dao.UserDao;
import com.zhidisoft.system.entity.User;
import org.junit.Test;

public class UserLoginServletTest {

    @Test
    public void test(){
        UserDao userDao = new UserDao();
        User user = userDao.selectName("zhangsan", "25f9e794323b453885f5181f1b624d0b");
        System.out.println(user);
    }
}

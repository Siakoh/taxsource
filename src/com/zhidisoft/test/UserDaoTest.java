package com.zhidisoft.test;

import com.zhidisoft.system.dao.UserDao;
import org.junit.Test;

public class UserDaoTest {
    @Test
    public void test(){
        UserDao userDao = new UserDao();

        boolean update = userDao.update("zhangsan", "aaaaaaaaaa", "aaaaa");

        System.out.println(update);
    }
}

package com.zhidisoft.test;

import com.zhidisoft.system.dao.TaxerDao;
import org.junit.Test;

public class TaxerDaoTest {

    @Test
    public void test(){
        TaxerDao taxerDao = new TaxerDao();
        boolean b = taxerDao.deleteTaxerById(3);
        System.out.println(b);
    }
}

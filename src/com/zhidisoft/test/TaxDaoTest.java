package com.zhidisoft.test;

import com.zhidisoft.system.dao.TaxerDao;
import com.zhidisoft.system.entity.Taxer;
import org.junit.Test;

public class TaxDaoTest {

    @Test
    public void test(){
        TaxerDao taxerDao = new TaxerDao();
        Taxer taxerByUserId = taxerDao.getTaxerByUserId(1);

        System.out.println(taxerByUserId);
    }
}

package com.zhidisoft.test;

import com.zhidisoft.system.dao.TaxerDao;
import com.zhidisoft.system.entity.Taxer;
import org.junit.Test;

public class TaxDaoTest {

    @Test
    public void test(){
        Taxer taxerByUserId = TaxerDao.getTaxerByUserId(1);

        System.out.println(taxerByUserId);
    }
}

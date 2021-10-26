package com.zhidisoft.manage.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.zhidisoft.manage.entity.TaxOrgan;
import com.zhidisoft.util.DBUtil;

/**
 * The type Tax organ dao.
 */
public class TaxOrganDao {

    private static TaxOrganDao instance;

    private TaxOrganDao() {
    }

    /**
     * Gets .
     *
     * @return the
     */
    public static TaxOrganDao getinstance() {
        if (instance == null) {
            instance = new TaxOrganDao();
        }
        return instance;
    }


    /**
     * Gets organs.
     *
     * @return the organs
     */
    public static List<TaxOrgan> getOrgans() {
        List<Map<String, String>> result = DBUtil.query("select * from tb_tax_organ ");
        List<TaxOrgan> organs = new ArrayList<TaxOrgan>();
        if (result != null && result.size() > 0) {
            for (Map<String, String> map : result) {
                TaxOrgan organ = new TaxOrgan();
                try {
                    BeanUtils.populate(organ, map);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                organs.add(organ);
            }
        }
        return organs;
    }


    /**
     * Gets organ.
     *      根据id查询税务机关
     * @param organId the organ id  税务机关Id
     * @return the organ  返回一个税务机关对象
     */
    public static TaxOrgan getOrgan(Integer organId) {
        Map<String, String> map = DBUtil.queryRow("select * from tb_tax_organ where id =?", organId);
        TaxOrgan organ = new TaxOrgan();
        if (map != null && map.size() > 0) {
            try {
                BeanUtils.populate(organ, map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return organ;
    }

    /**
     * 添加部门
     *
     * @param organ 根据表单创建的部门对象
     * @return 是否添加成功 boolean
     */
    public static boolean addOrgan(TaxOrgan organ) {
        int rows = DBUtil.add(organ, "tb_tax_organ");
        return rows == 1;
    }

}

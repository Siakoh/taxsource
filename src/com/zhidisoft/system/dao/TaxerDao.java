package com.zhidisoft.system.dao;

import com.zhidisoft.system.entity.Taxer;
import com.zhidisoft.system.entity.User;
import com.zhidisoft.util.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaxerDao {
    /**
     * Get taxer taxer.
     * tb_user 表中 taxerId 关联 tb_taxer 表中 id
     * 根据tb_user 表中  username 获取 taxer 对象
     *
     * @param username the username
     * @return the taxer
     */
    public  Taxer getTaxer(String username){
        UserDao userDao = new UserDao();
        //通过username拿到用户
        User user = userDao.getUser(username);
        Integer taxerId = user.getTaxerId();
        //判断user对象是否为taxer
        if(taxerId==null){
            return null;
        }
        //通过user对象的taxerId查询taxer对象
        Map<String, String> result = DBUtil.queryRow("select * from tb_taxer where id = ?", taxerId);
        Taxer taxer = new Taxer();
        //转化为对象返回
        try {
            if(!result.isEmpty()&&result.size()>0){
                BeanUtils.populate(taxer, result);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return taxer;
    }
    /**
     * Get taxers list.
     * 返回一个集合
     *
     * @return the list
     */
    public  List<Taxer> getTaxers() {
        List<Map<String, String>> result = DBUtil.query("select * from tb_taxer ");
        List<Taxer> taxers = new ArrayList<Taxer>();
        if(result!=null&&result.size()>0){
            //遍历结果封装为对象
            for(Map<String, String> map:result){
                Taxer taxer = new Taxer();
                try {
                    BeanUtils.populate(taxer, map);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                taxers.add(taxer);
            }
        }
        return taxers;
    }
    /**
     * Get taxer by id taxer.
     * 通过 id 查询 taxer（纳税人） 信息
     *
     * @param id the id
     * @return the taxer
     */
    public  Taxer getTaxerById(Integer id) {
        Map<String, String> result = DBUtil.queryRow("select * from tb_taxer where id = ?", id);
        Taxer taxer = new Taxer();
        try {
            if(!result.isEmpty()&&result.size()>0){
                BeanUtils.populate(taxer, result);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return taxer;
    }


    /**
     * Get search result list.
     *      返回查询办税专员的结果集
     * @param pageNum   the page num  页数
     * @param pageSize  the page size  页面记录数
     * @param taxerName the taxer name
     * @return the list
     */
    public  List<Map<String, String>> getSearchResult(int pageNum,int pageSize,String taxerName){
        boolean checkName = taxerName!=null&&taxerName.toString().length()>0;
        String sql = "select * from tb_taxer where 1=1 ";
        if(checkName){
            sql=sql+" and taxerName like '%"+taxerName+"%' ";
        }
        sql=sql+" limit ?,?";
        List<Map<String, String>> list = DBUtil.query(sql, (pageNum-1)*pageSize,pageSize);
        return list;
    }

    /**
     * 获取查询的记录数
     * @param pageSize 页面记录数
     * @return 查询的办税专员记录数
     */
    public  int getSearchRows(String taxerName){
        boolean checkName = taxerName!=null&&taxerName.toString().length()>0;
        String sql = "select * from tb_taxer  where 1=1 ";
        if(checkName){
            sql=sql+" and taxerName like '%"+taxerName+"%' ";
        }
        List<Map<String, String>> list = DBUtil.query(sql);
        return list.size();
    }

    /**
     * 添加办税专员
     * @param taxer 办税专员对象
     * @return 添加是否成功
     */
    public static boolean addTaxer(Taxer taxer){
        int row = DBUtil.add(taxer,"tb_taxer");
        return row==1;
    }


    /**
     * 更新办税专员
     * @param taxer 传入参数创建的办税专员对象
     * @param id 执行id
     * @return 是否更新成功
     */
    public  boolean updateTaxer(Taxer taxer, Integer id) {
        int rows = DBUtil.edit(taxer, "tb_taxer", id);
        return rows==1;
    }

    public  Taxer getTaxerByUserId(Integer userId) {
        String sql = "select tt.* from tb_taxer  tt inner join tb_user tu  on tt.id = tu.taxerId where 1=1 ";
        sql += " and tu.id=?";
        Taxer taxer = new Taxer();
        Map<String, String> result = DBUtil.queryRow(sql, userId);
        try {
            if(!result.isEmpty()&&result.size()>0){
                BeanUtils.populate(taxer, result);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return taxer;
    }

}

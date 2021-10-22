package com.zhidisoft.manage.dao;

import com.zhidisoft.manage.entity.TaxPayer;
import com.zhidisoft.util.DBUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxPayerDao {
    private static TaxPayerDao instance;
    public TaxPayerDao(){}
    public static TaxPayerDao getinstance(){
        if(instance==null){
            instance = new TaxPayerDao();
        }
        return instance;
    }




    public List<TaxPayer> selectPayers(int pageNum, int size){
        ArrayList<TaxPayer> payers = new ArrayList<>();
        String sql = "select * from tb_tax_payer limit ?,?";
        List<Map<String, String>> list = DBUtil.query(sql, pageNum, size);
        for(Map<String,String> m:list){
            TaxPayer payer = new TaxPayer();
            try {
                BeanUtils.populate( payer, m);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            payers.add(payer);

        }
        return payers;
    }
     public int count(){
        String sql = "select count(id) from tb_tax_payer";
         List<Map<String, String>> list = DBUtil.query(sql);
         int a = Integer.parseInt(list.iterator().next().get("count(id)"));
         return a;
     }
    /**
     * 返回查询纳税人的结果集
     * @param pageNum 页
     * @param pageSize 页面记录数
     * @param payerCode 纳税人编号
     * @param payerName 纳税人姓名
     * @return 查询的结果
     */
    public List<Map<String, String>> getSearchResult(int pageNum,int pageSize,String payerCode,String payerName){
        boolean checkCode = payerCode!=null&&payerCode.toString().length()>0;
        boolean checkName = payerName!=null&&payerName.toString().length()>0;
        String sql = "select * from tb_tax_payer p JOIN tb_industry i join " +
                "tb_tax_organ o join tb_user u on p.taxOrganId=o.id and p.industryId=i.id and p.userId=u.id where removeState=0 ";
        if(checkCode){
            sql=sql+"and p.payerCode = "+payerCode;
        }
        if(checkName){
            sql=sql+" and payerName like '%"+payerName+"%' ";
        }
        sql=sql+" limit ?,?";
        List<Map<String, String>> list = DBUtil.query(sql, (pageNum-1)*pageSize,pageSize);
        return list;
    }

    /**
     * 获取所有的记录总数
     * @return 记录总数
     */
    public int getTotalRows(){
        return Integer.parseInt(DBUtil.query("select count(*) c from tb_tax_payer p JOIN tb_industry i join " +
                "tb_tax_organ o join tb_user u on p.taxOrganId=o.id and p.industryId=i.id and p.userId=u.id ").get(0).get("c"));
    }
    /**
     * 获取查询的记录数
     * @param payerCode 查询纳税人编号
     * @param payerName 查询的纳税人姓名
     * @return 查询的纳税人局记录数
     */
    public int getSearchRows(String payerCode,String payerName){
        boolean checkCode = payerCode!=null&&payerCode.toString().length()>0;
        boolean checkName = payerName!=null&&payerName.toString().length()>0;
        String sql = "select * from tb_tax_payer p JOIN tb_industry i join tb_tax_organ o join tb_user u on p.taxOrganId=o.id and p.industryId=i.id and p.userId=u.id where removeState=0 ";
        if(checkCode){
            sql=sql+"and p.payerCode = "+payerCode;
        }
        if(checkName){
            sql=sql+" and payerName like '%"+payerName+"%' ";
        }
        List<Map<String, String>> list = DBUtil.query(sql);
        return list.size();
    }
    /**
     * 添加纳税人
     * @param payer 纳税人对象
     * @return 添加是否成功
     */
    public static boolean addPayer(TaxPayer payer){
        int add = DBUtil.add(payer, "tb_tax_payer");
        return add==1;
    }
    /**
     *删除纳税人
     * id执行纳税人id
     * 是否删除成功
     */
    public static boolean deletePayer(String id){
        String sql = "update tb_tax_payer set removeState=1 where id=?";
        boolean rows = DBUtil.update(sql, id);
        return rows;
    }
    /**
     * 更新纳税人
     * @param payer 传入参数创建的纳税人对象
     * @param id 执行id
     * @return 是否更新成功
     */
    public static boolean updatePayer(TaxPayer payer,Integer id){
        int rows = DBUtil.edit(payer, "tb_tax_payer", id);
        return rows==1;
    }
    /**
     * 获取指定id纳税人对象
     * @param id 指定id
     * @return 纳税人对象
     */
    public static TaxPayer getPayer(String id){
        Map<String ,String> row = new HashMap<String, String>();
        if(id!=null&&id.length()>0){
            row = DBUtil.queryRow("select * from tb_tax_payer where id = ?", Integer.parseInt(id));
        }
        TaxPayer payer = new TaxPayer();
        try {
            BeanUtils.populate(payer, row);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return payer;
    }
    /**
     * 通过纳税人识别号获取纳税人对象
     * @param payerCode 纳税人识别号
     * @return 纳税人对象
     */
    public static TaxPayer getPayerByCode(String payerCode){
        Map<String ,String> row = new HashMap<String, String>();
        if(payerCode!=null&&payerCode.length()>0){
            List<Map<String, String>> list = DBUtil.query("select * from tb_tax_payer where payerCode = ?", payerCode);
            if(list!=null&&list.size()==1){
                row = list.get(0);
            }
        }
        TaxPayer payer = new TaxPayer();
        try {
            BeanUtils.populate(payer, row);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return payer;
    }
    /**
     * 获取未被调查的纳税人对象
     * @param pageNum 页
     * @param pageSize 页面记录数
     * @param payerCode 纳税人编码
     * @param payerName 纳税人姓名
     * @return
     */
    public static List<Map<String, String>> getStatistical(int pageNum,int pageSize,String payerCode,String payerName){
        boolean checkCode = payerCode!=null&&payerCode.toString().length()>0;
        boolean checkName = payerName!=null&&payerName.toString().length()>0;
        String sql = "select * from tb_tax_payer p LEFT join tb_tax_source s on p.id=s.payerId JOIN tb_industry i join tb_tax_organ o join tb_user u on p.taxOrganId=o.id and p.industryId=i.id and p.userId=u.id  where s.id is null and p.removeState=0 ";
        if(checkCode){
            sql=sql+"and p.payerCode = "+payerCode;
        }
        if(checkName){
            sql=sql+" and payerName like '%"+payerName+"%' ";
        }
        sql=sql+" limit ?,?";
        List<Map<String, String>> list = DBUtil.query(sql, (pageNum-1)*pageSize,pageSize);
        return list;
    }
}

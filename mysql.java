package mysql.java;
package com.platform.syncdatabase.util;

import com.platform.utils.ResourceUtil;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbConnUtil {

    private static String sqlserverDriverClassName = ResourceUtil.getConfigByName("hy.jindie.sqlserverDriverClassName");
    private static String sqlserverDbUrl = ResourceUtil.getConfigByName("hy.jindie.sqlserverDbUrl");
    private static String sqlserverDbUser = ResourceUtil.getConfigByName("hy.jindie.sqlserverDbUser");
    private static String sqlserverDbPwd = ResourceUtil.getConfigByName("hy.jindie.sqlserverDbPwd");

    /**
     * ������ȡ����
     *
     * @return
     */
    public static Connection createConnSrc() {
        Connection conn = null;
        try {
            String driverClassName = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://192.168.1.110:3306/haoyuan?useUnicode=true&characterEncoding=UTF-8";
            String dbUser = "root";
            String dbPwd = "1234567890";
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return conn;
    }


    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
��������������������������������
package com.platform.syncdatabase;

import com.platform.entity.CategoryEntity;
import com.platform.entity.GoodsEntity;
import com.platform.service.CategoryService;
import com.platform.service.GoodsService;
import com.platform.syncdatabase.util.DbConnUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

@Slf4j
public class mysql {

    @Autowired
    CategoryService categoryService;

    @Autowired
    GoodsService goodsService;

    /**
     * ����Ʒ��
     */
    public void updateCategory() {
        //������
        Connection connSqlserver = DbConnUtil.createConnSrc();
        DbBuilder sqlserver = new DbBuilder();
        try {
            //DO
            //DO 1������musical���ݡ����map idΪkey
            //DO 2������sqlserver���ݻ�ȡ����������Ӳ���map����ȡ�޸Ĺ����������update map
            //DO 3��ʵ���������¹���
            String sql = "Select * from t_Item WHERE FItemClassID = 4";
            List<Map<String, Object>> remoteList = sqlserver.getListMap(sql, null);//��ȡԶ�̽������
            if (remoteList != null && !remoteList.isEmpty()) {
                List<CategoryEntity> insertList = new ArrayList<>();
                List<CategoryEntity> updateList = new ArrayList<>();
                //��ѯmusical����Ʒ��
                List<CategoryEntity> mysqlCategoryList = categoryService.queryList(new HashMap<>());
                if (mysqlCategoryList != null && !mysqlCategoryList.isEmpty()) {//�Ѵ�������ʱ���Աȸ���
                    //1���������ݷ�������
                    Map<String, CategoryEntity> mysqlCategoryMap = new HashMap<>();
                    for (CategoryEntity category : mysqlCategoryList) {
                        mysqlCategoryMap.put(category.getId() + "", category);
                    }
                    //��ȡ����id
                    Set<String> ids = mysqlCategoryMap.keySet();
                    log.info("ids.size() " + ids.size());

                    //2����ʼ�Ա�����
                    //�����������
                    for (Map item : remoteList) {
                        try {
                            String id = item.get("FItemID") + "";
                            if (ids.contains(id)) {//�Ѵ��ڵ�Ʒ�� ����
                                updateList.add(copyCategoryInfo(item, mysqlCategoryMap.get(id)));
                            } else {//������Ʒ��
                                insertList.add(copyCategoryInfo(item, new CategoryEntity()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {//��ʼ��ȫ������
                    for (Map item : remoteList) {
                        try {
                            insertList.add(copyCategoryInfo(item, new CategoryEntity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (!insertList.isEmpty()) {
                        log.info(("saveBatch category " + insertList.size()));
                        categoryService.saveBatch(insertList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (!updateList.isEmpty()) {
                        log.info(("batchUpdate category " + updateList.size()));
                        categoryService.batchUpdate(updateList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //�ر�
            DbConnUtil.close(connSqlserver);
        }

    }

    /**
     * ����Ʒ����Ϣ
     *
     * @param item
     * @param categoryEntity
     * @return
     */
    private CategoryEntity copyCategoryInfo(Map item, CategoryEntity categoryEntity) {
        categoryEntity.setId(Integer.parseInt(item.get("FItemID") + ""));
        categoryEntity.setName(item.get("FName") + "");
        categoryEntity.setParentId(Integer.parseInt(item.get("FParentID") + ""));
        categoryEntity.setSortOrder(Integer.parseInt(item.get("FShortNumber") + ""));
        String fDeleted = item.get("FDeleted") + "";
        if (StringUtils.isNotBlank(fDeleted) && "1".equals(fDeleted)) {
            categoryEntity.setIsShow(0);
        } else {
            categoryEntity.setIsShow(1);
        }
        categoryEntity.setFlevel(item.get("FLevel") + "");
        categoryEntity.setFlevel(Integer.parseInt(item.get("FLevel") + ""));
        categoryEntity.setFullnumber(item.get("FFullNumber") + "");
        categoryEntity.setFullnumber(item.get("FFullName") + "");
        categoryEntity.setId(item.get("UUID") + "");
        //ƴ��ModifyTime
        byte[] fModifyTimes = (byte[]) item.get("FModifyTime");
        StringBuilder modifyTime = new StringBuilder();
        for (byte b : fModifyTimes) {
            modifyTime.append(b);
        }
        categoryEntity.setModifyTime(modifyTime.toString());//��Ӹ��°汾��Ϣ
        categoryEntity.setId((boolean) item.get("FDetail") ? "1" : "0");
        return categoryEntity;
    }


    /**
     * ���²�Ʒ
     */
    public void updateGoods() {
        //������
        Connection connSqlserver = DbConnUtil.createConnSrc();
        DbBuilder sqlserver = new DbBuilder();
        try {
            //DO
            //DO 1������musical���ݡ����map idΪkey
            //DO 2������sqlserver���ݻ�ȡ����������Ӳ���map����ȡ�޸Ĺ����������update map
            //DO 3��ʵ���������¹���
            String sql = "SELECT" +
                    " i.FItemID," +
                    " c.FName," +
                    " i.FParentID," +
                    " i.FShortNumber," +
                    " i.FDeleted," +
                    " i.FLevel," +
                    " i.FFullNumber," +
                    " i.FFullName," +
                    " i.UUID," +
                    " i.FModifyTime," +
                    " c.FOrderPrice," +
                    " c.FOrderPrice," +
                    " c.FOrderPrice," +
                    " c.FModel " +
                    "FROM " +
                    " t_item i" +
                    " LEFT JOIN t_icitemcore c ON i.FItemID = c.FItemID " +
                    "WHERE " +
                    " i.FItemClassID = 4 " +
                    " AND i.FDetail = 1 ;";
//                    " AND i.FDetail = 1 "+
//                    " AND i.FItemID = 291;";
            List<Map<String, Object>> remoteList = sqlserver.getListMap(sql, null);//��ȡԶ�̽������
            if (remoteList != null && !remoteList.isEmpty()) {
                List<GoodsEntity> insertList = new ArrayList<>();
                List<GoodsEntity> updateList = new ArrayList<>();
                //��ѯmusical���в�Ʒ
                List<GoodsEntity> mysqlGoodsList = goodsService.queryAllGoods();
                if (mysqlGoodsList != null && !mysqlGoodsList.isEmpty()) {//�Ѵ�������ʱ���Աȸ���
                    //1���������ݷ�������
                    Map<String, GoodsEntity> mysqlGoodsMap = new HashMap<>();
                    for (GoodsEntity goods : mysqlGoodsList) {
                        mysqlGoodsMap.put(goods.getId() + "", goods);
                    }
                    //��ȡ����id
                    Set<String> ids = mysqlGoodsMap.keySet();

                    //2����ʼ�Ա�����
                    //�����������
                    for (Map item : remoteList) {
                        try {
                            String id = item.get("FItemID") + "";
                            if (ids.contains(id)) {//�Ѵ��ڵ�Ʒ��
                                //����
                                updateList.add(copyGoodsInfo(item, mysqlGoodsMap.get(id)));
                            } else {//������Ʒ��
                                insertList.add(copyGoodsInfo(item, new GoodsEntity()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {//��ʼ��ȫ������
                    for (Map item : remoteList) {
                        try {
                            insertList.add(copyGoodsInfo(item, new GoodsEntity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (!insertList.isEmpty()) {
                        log.info(("saveBatch goods " + insertList.size()));
                        goodsService.saveBatch(insertList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (!updateList.isEmpty()) {
                        log.info(("batchUpdate goods " + updateList.size()));
                        goodsService.batchUpdate(updateList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //�ر�
            DbConnUtil.close(connSqlserver);
        }

    }


    /**
     * ���²�Ʒ��Ϣ
     *
     * @param item
     * @param goodsEntity
     * @return
     */
    private GoodsEntity copyGoodsInfo(Map item, GoodsEntity goodsEntity) {
        goodsEntity.setId(Integer.parseInt(item.get("FItemID") + ""));
        goodsEntity.setName(item.get("FName") + "");
        goodsEntity.setCategoryId(Integer.parseInt(item.get("FParentID") + ""));
        goodsEntity.setSortOrder(Integer.parseInt(item.get("FShortNumber") + ""));
        String fDeleted = item.get("FDeleted") + "";
        if (StringUtils.isNotBlank(fDeleted) && "1".equals(fDeleted)) {
            goodsEntity.setIsDelete(0);
        } else {
            goodsEntity.setIsDelete(1);
        }
        goodsEntity.setFlevel(Integer.parseInt(item.get("FLevel") + ""));
        goodsEntity.setFullnumber(item.get("FFullNumber") + "");
        goodsEntity.setFullnumber(item.get("FFullName") + "");
        goodsEntity.setId(item.get("UUID") + "");
        //ƴ��ModifyTime
        byte[] fModifyTimes = (byte[]) item.get("FModifyTime");
        StringBuilder modifyTime = new StringBuilder();
        for (byte b : fModifyTimes) {
            modifyTime.append(b);
        }
        goodsEntity.setModifyTime(modifyTime.toString());//��Ӹ��°汾��Ϣ
        goodsEntity.setCounterPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setRetailPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setCounterPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setFlevel(item.get("FModel") + "");
        return goodsEntity;
    }


}


public class mysql {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

	}

}

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
     * 从这里取数据
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
————————————————
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
     * 更新品类
     */
    public void updateCategory() {
        //打开链接
        Connection connSqlserver = DbConnUtil.createConnSrc();
        DbBuilder sqlserver = new DbBuilder();
        try {
            //DO
            //DO 1、遍历musical数据。添加map id为key
            //DO 2、遍历sqlserver数据获取新增数据添加插入map，获取修改过的数据添加update map
            //DO 3、实现批量更新功能
            String sql = "Select * from t_Item WHERE FItemClassID = 4";
            List<Map<String, Object>> remoteList = sqlserver.getListMap(sql, null);//获取远程金蝶数据
            if (remoteList != null && !remoteList.isEmpty()) {
                List<CategoryEntity> insertList = new ArrayList<>();
                List<CategoryEntity> updateList = new ArrayList<>();
                //查询musical现有品类
                List<CategoryEntity> mysqlCategoryList = categoryService.queryList(new HashMap<>());
                if (mysqlCategoryList != null && !mysqlCategoryList.isEmpty()) {//已存在数据时，对比更新
                    //1、现有数据分析整理
                    Map<String, CategoryEntity> mysqlCategoryMap = new HashMap<>();
                    for (CategoryEntity category : mysqlCategoryList) {
                        mysqlCategoryMap.put(category.getId() + "", category);
                    }
                    //获取现有id
                    Set<String> ids = mysqlCategoryMap.keySet();
                    log.info("ids.size() " + ids.size());

                    //2、开始对比数据
                    //遍历金蝶数据
                    for (Map item : remoteList) {
                        try {
                            String id = item.get("FItemID") + "";
                            if (ids.contains(id)) {//已存在的品类 更新
                                updateList.add(copyCategoryInfo(item, mysqlCategoryMap.get(id)));
                            } else {//新增的品类
                                insertList.add(copyCategoryInfo(item, new CategoryEntity()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {//初始化全量插入
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
            //关闭
            DbConnUtil.close(connSqlserver);
        }

    }

    /**
     * 更新品类信息
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
        //拼接ModifyTime
        byte[] fModifyTimes = (byte[]) item.get("FModifyTime");
        StringBuilder modifyTime = new StringBuilder();
        for (byte b : fModifyTimes) {
            modifyTime.append(b);
        }
        categoryEntity.setModifyTime(modifyTime.toString());//添加更新版本信息
        categoryEntity.setId((boolean) item.get("FDetail") ? "1" : "0");
        return categoryEntity;
    }


    /**
     * 更新产品
     */
    public void updateGoods() {
        //打开链接
        Connection connSqlserver = DbConnUtil.createConnSrc();
        DbBuilder sqlserver = new DbBuilder();
        try {
            //DO
            //DO 1、遍历musical数据。添加map id为key
            //DO 2、遍历sqlserver数据获取新增数据添加插入map，获取修改过的数据添加update map
            //DO 3、实现批量更新功能
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
            List<Map<String, Object>> remoteList = sqlserver.getListMap(sql, null);//获取远程金蝶数据
            if (remoteList != null && !remoteList.isEmpty()) {
                List<GoodsEntity> insertList = new ArrayList<>();
                List<GoodsEntity> updateList = new ArrayList<>();
                //查询musical现有产品
                List<GoodsEntity> mysqlGoodsList = goodsService.queryAllGoods();
                if (mysqlGoodsList != null && !mysqlGoodsList.isEmpty()) {//已存在数据时，对比更新
                    //1、现有数据分析整理
                    Map<String, GoodsEntity> mysqlGoodsMap = new HashMap<>();
                    for (GoodsEntity goods : mysqlGoodsList) {
                        mysqlGoodsMap.put(goods.getId() + "", goods);
                    }
                    //获取现有id
                    Set<String> ids = mysqlGoodsMap.keySet();

                    //2、开始对比数据
                    //遍历金蝶数据
                    for (Map item : remoteList) {
                        try {
                            String id = item.get("FItemID") + "";
                            if (ids.contains(id)) {//已存在的品类
                                //更新
                                updateList.add(copyGoodsInfo(item, mysqlGoodsMap.get(id)));
                            } else {//新增的品类
                                insertList.add(copyGoodsInfo(item, new GoodsEntity()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {//初始化全量插入
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
            //关闭
            DbConnUtil.close(connSqlserver);
        }

    }


    /**
     * 更新产品信息
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
        //拼接ModifyTime
        byte[] fModifyTimes = (byte[]) item.get("FModifyTime");
        StringBuilder modifyTime = new StringBuilder();
        for (byte b : fModifyTimes) {
            modifyTime.append(b);
        }
        goodsEntity.setModifyTime(modifyTime.toString());//添加更新版本信息
        goodsEntity.setCounterPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setRetailPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setCounterPrice(new BigDecimal(item.get("FOrderPrice") + ""));
        goodsEntity.setFlevel(item.get("FModel") + "");
        return goodsEntity;
    }


}


public class mysql {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}

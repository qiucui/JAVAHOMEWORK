package sql.java;
package JDBC;
import java.sql.*;
public class sql {
public static final String DRIVER = "com.mysql.jdbc.Driver";
public static final String URL = "jdbc:mysql://127.0.0.1:3306/lianxi"; 
public static final String USERNAME = "root";
public static final String PASSWORD = "123456";
public static void main(String[] args) throws SQLException, ClassNotFoundException {
Connection conn = null; //每一个Connection对象表示一个数据库连接对象
Statement stat = null;
Class.forName(DRIVER);//加载驱动程序
conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
stat = conn.createStatement();//找到借口
String sql = "select*from hehe";//查询语句
ResultSet rs=stat.executeQuery(sql);//查询
//将查询出的结果输出
while (rs.next()) {
	String deptno=rs.getString("deptno");
	String dname=rs.getString("dname");
	String loc=rs.getString("loc");



	String deptno1=rs.getString(1);
	String dname1=rs.getString(2);
	String loc1=rs.getString(3);
	System.out.println("deptno:"+deptno1+",dname:"+dname1+",loc:"+loc1);
	}
	rs.close();
	stat.close();
	conn.close();
	}
	}


	————————————————
	import MySQLdb
	import numpy as np


	#get data from sql databases
	class database():
	    def __init__(self):
	        self.conn = MySQLdb.connect(
	            host='127.0.0.1',
	            user='root',
	            passwd='sangomine',
	            port=3306,
	            db='original_data',
	            charset = 'utf8'
	        )


	    def train_x(self):
	        with self.conn:
	            cur = self.conn.cursor()
	            sql = 'SELECT * FROM train_x'
	            cur.execute(sql)
	            data = cur.fetchall()
	            data_arr = np.array(list(data)).reshape(-1,7)
	            cur.close()
	            return data_arr


	    def train_y(self):
	        with self.conn:
	            cur = self.conn.cursor()
	            sql = 'SELECT * FROM train_y'
	            cur.execute(sql)
	            data = cur.fetchall()
	            data_arr = np.array(list(data)).reshape(-1,2)
	            cur.close()
	            return data_arr


	    def test_x(self):
	        with self.conn:
	            cur = self.conn.cursor()
	            sql = 'SELECT * FROM test_x'
	            cur.execute(sql)
	            data = cur.fetchall()
	            data_arr = np.array(list(data)).reshape(-1,7)
	            cur.close()
	            return data_arr

	            		 def test_y(self):
	            		        with self.conn:
	            		            cur = self.conn.cursor()
	            		            sql = 'SELECT * FROM test_y'
	            		            cur.execute(sql)
	            		            data = cur.fetchall()
	            		            data_arr = np.array(list(data)).reshape(-1,2)
	            		            cur.close()
	            		            return data_arr

	 train_x = db.train_x()
	            		    print(train_x.shape)
	            		    train_y = db.train_y()
	            		    print(train_y.shape)
	            		    test_x = db.test_x()
	            		    print(test_x.shape)
	            		    test_y = db.test_y()
	            		    print(test_y.shape
	            		
public class sql {

}

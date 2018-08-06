package bean;

import java.sql.*;

import toolkit.DBQueryThread;
import toolkit.DBQueryThread.OnQueryFinish;
import toolkit.DBUpdateThread;
import toolkit.DBUpdateThread.OnUpdateFinish;
import toolkit.XMLUtil;

public class DatabaseBean {
	
	private String driver,url,username,password;
	private Connection conn=null;
	private Statement stmt=null;
	
	
	public static DatabaseBean newInstance(){
		return new DatabaseBean();
	}
	public DatabaseBean(){
		driver=XMLUtil.parse("driver");	
	    url=XMLUtil.parse("url");	
		username=XMLUtil.parse("username");
		password=XMLUtil.parse("password");
		try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url,username,password);
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}catch(Exception e){
			
				e.printStackTrace();
		
		}
	}
	
	
	public void executeQuery(String sql,OnDBProcessingFinish pf){
		 new DBQueryThread(stmt,sql,new OnQueryFinish(){

			@Override
			public void onFinish(ResultSet rs) {
				// TODO Auto-generated method stub
				pf.onFinish(rs);
			}
			
		}).start();
	}
	
	
	public void executeUpdate(String[] sqls,OnDBProcessingFinish pf){
		 new DBUpdateThread(stmt,sqls,new OnUpdateFinish(){

			@Override
			public void onFinish(int[] n) {
				// TODO Auto-generated method stub
				pf.onFinish(n);
			}
			
		}).start();
	}
	
	
	public void closeDB(){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public interface OnDBProcessingFinish{
		public void onFinish(Object j);
	}
}

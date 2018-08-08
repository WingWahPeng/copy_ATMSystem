package bean;
/**
 * jdbcDAO
 */
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
	
//静态工厂方法，（创建对象实例）构造器	
	public static DatabaseBean newInstance(){
		return new DatabaseBean();
	}

	public DatabaseBean(){
		driver=XMLUtil.parse("driver");	
	    url=XMLUtil.parse("url");	
		username=XMLUtil.parse("username");
		password=XMLUtil.parse("password");
		try{
			Class.forName(driver);//加载驱动程序			

			conn=DriverManager.getConnection(url,username,password);//获取数据库连接			

			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//创建Statement对象
		}catch(Exception e){
			
				e.printStackTrace();
		
		}
	}
	

//执行查询操作，用start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码。
	public void executeQuery(String sql,OnDBProcessingFinish pf){
		 new DBQueryThread(stmt,sql,new OnQueryFinish(){
			@Override
			public void onFinish(ResultSet rs) {
				// TODO Auto-generated method stub
				pf.onFinish(rs);
			}
			
		}).start();	//启动该线程，把该线程置于就绪状态
	}
	
//执行更改操作，用start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码。	
	public void executeUpdate(String[] sqls,OnDBProcessingFinish pf){
		 new DBUpdateThread(stmt,sqls,new OnUpdateFinish(){
			@Override
			public void onFinish(int[] n) {
				// TODO Auto-generated method stub
				pf.onFinish(n);
			}
			
		}).start();
	}
	
//关闭数据库连接	
	public void closeDB(){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//产生结果的接口
	public interface OnDBProcessingFinish{
		public void onFinish(Object j);		//查询或更新的结果
	}
}

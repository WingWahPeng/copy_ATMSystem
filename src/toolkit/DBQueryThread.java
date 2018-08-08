package toolkit;

/**
 * 一次查询，建立一次独立的查询线程
 */
import java.sql.*;

public class DBQueryThread extends Thread {
	private ResultSet rs;
	private Statement stmt;
	private String sql;
	private OnQueryFinish qf;
	

	public DBQueryThread(Statement stmt,String sql,OnQueryFinish qf){
		this.stmt=stmt;
		this.sql=sql;
		this.qf=qf;
	}

//线程的运行方法
	public void run(){
		try {
			rs=stmt.executeQuery(sql);		
			//模拟数据量大的情况
			Thread.sleep(3000);
			qf.onFinish(rs);
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public interface OnQueryFinish{
		public void onFinish(ResultSet rs);
	}
}
	
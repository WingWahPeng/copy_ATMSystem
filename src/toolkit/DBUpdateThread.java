package toolkit;


import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdateThread extends Thread {
	private int[] n;
	private Statement stmt;
	private String[] sqls;
	private OnUpdateFinish uf;
	
	//������sql
	public DBUpdateThread(Statement stmt,String[] sqls,OnUpdateFinish uf){
		this.stmt=stmt;
		this.sqls=sqls;
		this.uf=uf;
	}
	
	public void run(){
		try {
			for(String sql:sqls){
				stmt.addBatch(sql);
			}
			n=stmt.executeBatch();
			
			//ģ��������������
			Thread.sleep(3000);
			uf.onFinish(n);
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public interface OnUpdateFinish{
		public void onFinish(int[] n);
	}
}

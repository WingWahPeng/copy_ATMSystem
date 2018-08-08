package toolkit;

/**
 * һ�β�ѯ������һ�ζ����Ĳ�ѯ�߳�
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

//�̵߳����з���
	public void run(){
		try {
			rs=stmt.executeQuery(sql);		
			//ģ��������������
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
	
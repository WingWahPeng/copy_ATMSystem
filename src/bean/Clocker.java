package bean;
/**
 * 计时器类
 */
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Clocker extends JPanel{
	private static final long serialVersionUID = 1L;
	private Timeout t;
	private CountDown countDown;
	private JLabel timeShow;
	private int timeRange;
	private int currentTime;
	private InputStream is = null;    
    private Font definedFont = null;
    private String fontUrl="resource/UnidreamLED.ttf";
	
	public Clocker(){
		this(60);
	}
	
	public Clocker(int timeRange){
		this.timeRange=timeRange;
		
		 try {
			is =new FileInputStream(new File(fontUrl));
			new BufferedInputStream(is);    
            definedFont = Font.createFont(Font.TRUETYPE_FONT, is);
            definedFont=definedFont.deriveFont((float) 80.0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         	
		timeShow=new JLabel(""+timeRange);
		timeShow.setBackground(Color.WHITE);
		timeShow.setForeground(Color.GREEN);
		timeShow.setFont(definedFont);
		timeShow.setHorizontalAlignment(SwingConstants.CENTER);
		setPreferredSize(new Dimension(100, 75));
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(Color.BLACK);
		add(timeShow,BorderLayout.CENTER);
	}
	
	public void setTimeRange(int timeRange){
		this.timeRange=timeRange;
	}
	
	public int getTimeRange(){
		return timeRange;
	}
	
	public void startCountDown(){//倒计时开始的方法
		currentTime=timeRange;
		countDown=new CountDown();
		countDown.start();
	}
	
	public void stopCountDown(){//停止倒计时
		currentTime=0;
		countDown.interrupt();
		countDown=null;
	}
	
	class CountDown extends Thread{//倒计时线程
		public void run(){
			try {
				while(currentTime>=0){
					timeShow.setText(""+currentTime);
				    Thread.sleep(1000);
				    currentTime--;
				}
				if(t!=null)t.onTimeout();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}
		}
	};
	public void setTimeout(Timeout t){
		this.t=t;
	}
	public interface Timeout{//超时
		public void onTimeout();
	}
}

package module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.ATMWait;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

public class Deposit extends ATMPanel implements ActionListener{

	/**
	 * 存款界面
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton ensure,cancel;//确认，取消
	private Clocker clocker;
	private JLabel tips,label,warmPrompt,textTip;//右上角提示，输入提示，温馨提示，错误提示
	private ATMTextField moneyText;//输入金额框
	
	public Deposit(){
		super("resource/ATM.png");
				
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		label=new JLabel("<html>\u8BF7\u8F93\u5165\u5B58\u6B3E\u91D1\u989D</html>");
		label.setFont(new Font("宋体", Font.BOLD, 27));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(350, 180, 200, 45);
		add(label);
		
		moneyText=new ATMTextField(120,45);
		moneyText.setHorizontalAlignment(SwingConstants.CENTER);
		moneyText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		moneyText.setBounds(390, 235, 120, 45);
		add(moneyText);
		moneyText.addKeyListener(new KeyAdapter(){		//键盘事件监听
			public void keyTyped(KeyEvent e) {  			
			    // TODO Auto-generated method stub  
			    String s = moneyText.getText();  
			    Pattern p = Pattern.compile("[0-9]");
			    Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			    boolean b = m.matches();
			    if(s.length() >= 5 || !b) e.consume();
			    textTip.setText("");
			    
			}
		});
		
		warmPrompt=new JLabel("<html>\u6E29\u99A8\u63D0\u793A\uFF1A\u672C\u4EA4\u6613\u652F\u6301\u6700\u5927\u5B58\u6B3E\u91D1\u989D<font color=\"#ff0000\">10000</font>\u5143<center>\u6B64\u7CFB\u7EDF\u53EA\u63A5\u53D7\u91D1\u989D\u4E3A<font color=\"#ff0000\">100</font>\u7684\u6B63\u6574\u6570\u500D</center></html>");
		warmPrompt.setFont(new Font("宋体", Font.BOLD, 24));
		warmPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		warmPrompt.setVerticalAlignment(SwingConstants.TOP);
		warmPrompt.setBounds(188, 380, 519, 67);
		add(warmPrompt);
		
		
		ensure=new ATMButton("确认");
		ensure.setBounds(0, 499, 190, 45);
		add(ensure);
		ensure.addActionListener(this);
		
		cancel=new ATMButton("取消");
		cancel.setBounds(710, 499, 190, 45);
		add(cancel);
		cancel.addActionListener(this);
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(Deposit.this, new MainWindow(), null);

			}
			
		});
			
		textTip = new JLabel();
		textTip.setHorizontalAlignment(SwingConstants.CENTER);
		textTip.setFont(new Font("宋体", Font.BOLD, 18));
		textTip.setBounds(393, 295, 114, 38);
		add(textTip);	

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==ensure){
			if(moneyText.getText().equals("")){
				return;
			}
			
			float m=Integer.valueOf(moneyText.getText());
			if(m>10000||m%100!=0){
				
				textTip.setText("<html><font color=\"#ff0000\">请重新输入!</font></html>");
				moneyText.setText("");
				
			}else{		
				float newBalance=m+Float.valueOf(MyUtil.getCardInformation().getBalance());
				String sql="update BankCard set balance="+newBalance+" where cardID='"+MyUtil.getCardInformation().getCardID()+"'";
				MyUtil.panelLeap(this, new ATMWait(ATMWait.UPDATE,sql,m+"","deposit"), clocker);
			}
			
		}else{
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);
		}
	}
}

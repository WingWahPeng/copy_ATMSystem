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

import toolkit.MyUtil;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.ATMWait;
import bean.Clocker;
import bean.Clocker.Timeout;

import java.awt.Color;

public class ChangePW_3 extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton returnCard, ok, backMainWindow;
	private Clocker clocker;
	private JLabel tips, label, label_1, hint;
	private ATMTextField passwordField;
	private String spw,pw;
	
	
	
	/**
	 * @wbp.parser.constructor
	 */
	
	
	public ChangePW_3(){
		super("resource/ATM.png");
		
		//读取指定账号的余额等信息并显示

		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		returnCard=new ATMButton("退卡");
		returnCard.setBounds(0, 499, 180, 45);
		add(returnCard);
		returnCard.addActionListener(this);
		
		backMainWindow = new ATMButton("回主菜单");
		backMainWindow.addActionListener(this);
		backMainWindow.setBounds(720, 499, 180, 45);
		add(backMainWindow);
		
		
		label = new JLabel("修改成功！");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		label.setBounds(391, 248, 150, 59);
		add(label);
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(ChangePW_3.this, new MainWindow(), null);
			}
			
		});
		
		setSize(900,600);
		setLayout(null);
	}
	
	
	public ChangePW_3(String tpw){
		super("resource/ATM.png");
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		
		label = new JLabel("请再次输入新个人密码");
		label.setFont(new Font("宋体", Font.BOLD, 30));
		label.setBounds(290, 187, 320, 35);
		add(label);
		
		label_1 = new JLabel("<html><p>请输入6位密码</p><p>温馨提示：为了加强保密性，提醒您尽量避免使用身份证号、出生年月日</p><p>号码作为您账户或者银行卡的密码</p></html>");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		label_1.setBounds(153, 419, 576, 70);
		add(label_1);
		
		
		hint = new JLabel();
		hint.setForeground(new Color(153, 0, 0));
		hint.setFont(new Font("宋体", Font.BOLD, 18));
		hint.setBounds(390, 310, 127, 25);
		add(hint);
		
		
		spw = tpw;
		passwordField = new ATMTextField(120,45,true);
		passwordField.setEchoChar('*');
		passwordField.setTextFont(new Font("宋体", Font.PLAIN, 30));
		passwordField.setBounds(390, 250, 120, 45);
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		add(passwordField);
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char[] s = passwordField.getText().toCharArray();
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
				boolean b = m.matches();
				if (s.length >= 6 || !b)
					e.consume();
				hint.setText("");
			}
		});
		
		returnCard=new ATMButton("退卡");
		returnCard.setBounds(0, 499, 180, 45);
		add(returnCard);
		returnCard.addActionListener(this);
		
		ok = new ATMButton("确认");
		ok.addActionListener(this);
		ok.setBounds(720, 499, 180, 45);
		add(ok);
		
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(ChangePW_3.this, new MainWindow(), null);
			}
			
		});
		
		setSize(900,600);
		setLayout(null);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==returnCard){
			
			MyUtil.panelLeap(this, new Welcome(), clocker);
		}
		
		else if(arg0.getSource()==backMainWindow){

			MyUtil.panelLeap(this,new MainWindow(), clocker);
		}
		
		else if(arg0.getSource()==ok){
			pw = passwordField.getText();
			if(pw.length()==6 && spw.equals(pw)){
				String sql="update BankCard set password='"+spw+"' where cardID='"+MyUtil.getCardInformation().getCardID()+"'";
				MyUtil.panelLeap(this, new ATMWait(ATMWait.UPDATE,sql,spw,"changepassword"), clocker);
				
			}
			else if(pw.length()!=6){
				hint.setText("请输入6位数字");
				passwordField.setText("");
			}
			else{
				hint.setBounds(329, 310, 247, 25);
				hint.setText("前后两次输入的密码不一致！");
				passwordField.setText("");
			}
		}
		
	}
}

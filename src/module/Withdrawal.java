package module;

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

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Withdrawal extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton money1,money2,money3,money4,money5,money6,backMainWindow,yesButton;
	private Clocker clocker;
	private JLabel tips,label,prompt,textTip;
	private ATMTextField moneyText;
	
	public Withdrawal(){
		super("resource/ATM.png");
		
		clocker=new Clocker(60);
		clocker.setTimeout(new Timeout(){

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(Withdrawal.this,new MainWindow(), null);

			}
			
		});
		clocker.setBounds(592, 10, 90, 70);
		clocker.startCountDown();
		add(clocker);
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		label=new JLabel("<html>\u8BF7\u60A8\u9009\u62E9\u6216\u8F93\u5165\u53D6\u6B3E\u91D1\u989D</html>");
		label.setFont(new Font("宋体", Font.BOLD, 27));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(290, 150, 320, 45);
		add(label);
		
		moneyText=new ATMTextField(120,45);
		moneyText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		moneyText.setBounds(390, 220, 120, 45);
		moneyText.setHorizontalAlignment(SwingConstants.CENTER);
		add(moneyText);
		moneyText.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {  
			    // TODO Auto-generated method stub  
			    String s = moneyText.getText();  
			    Pattern p = Pattern.compile("[0-9]");
			    Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			    boolean b = m.matches();
			    if(s.length() >= 4 || !b) e.consume();
			    textTip.setText("");
			}  
		});
		
		prompt=new JLabel("<html>\u53D6\u6B3E\u91D1\u989D\u9700\u4E3A\u9762\u989D<font color=\"#ff0000\">100</font>\u7684\u6B63\u6574\u6570\u500D<center>\u5355\u7B14\u6700\u5927\u53D6\u6B3E\u91D1\u989D\u4E3A<font color=\"#ff0000\">5000</font>\u5143</center>\r\n</html>");
		prompt.setFont(new Font("宋体", Font.BOLD, 24));
		prompt.setHorizontalAlignment(SwingConstants.CENTER);
		prompt.setVerticalAlignment(SwingConstants.TOP);
		prompt.setBounds(188, 390, 524, 122);
		add(prompt);
		
		textTip = new JLabel();
		textTip.setHorizontalAlignment(SwingConstants.CENTER);
		textTip.setFont(new Font("宋体", Font.BOLD, 18));
		textTip.setBounds(393, 290, 114, 38);
		add(textTip);
		
		money1=new ATMButton("100");
		money1.setBounds(0, 235, 190, 45);
		add(money1);
		money1.addActionListener(this);
		
		money2=new ATMButton("300");
		money2.setBounds(0, 330, 190, 45);
		add(money2);
		money2.addActionListener(this);
		
		money3=new ATMButton("500");
		money3.setBounds(0, 425, 190, 45);
		add(money3);
		money3.addActionListener(this);
		
		money4=new ATMButton("1000");
		money4.setBounds(710, 235, 190, 45);
		add(money4);
		money4.addActionListener(this);
		
		money5=new ATMButton("2500");
		money5.setBounds(710, 330, 190, 45);
		add(money5);
		money5.addActionListener(this);
		
		money6=new ATMButton("5000");
		money6.setBounds(710, 425, 190, 45);
		add(money6);
		money6.addActionListener(this);
		
		backMainWindow=new ATMButton("回主菜单");
		backMainWindow.setBounds(0, 520, 190, 45);
		add(backMainWindow);
		backMainWindow.addActionListener(this);
		
		yesButton=new ATMButton("确认");
		yesButton.setBounds(710, 520, 190, 45);
		add(yesButton);
		yesButton.addActionListener(this);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object action=e.getSource();
		if(action==money1){
			
			Withdrawal.this.process(100);
			
		}else if(action==money2){
			
			Withdrawal.this.process(300);
			
		}else if(action==money3){
			
			Withdrawal.this.process(500);
			
		}else if(action==money4){
			
			Withdrawal.this.process(1000);
			
		}else if(action==money5){
			
			Withdrawal.this.process(2500);
			
		}else if(action==money6){
			
			Withdrawal.this.process(5000);
			
		}else if(action==backMainWindow){
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);
			
		}else {
			if(moneyText.getText().equals("")){
				return;
			}
			int m=Integer.valueOf(moneyText.getText());
			if(m>5000||m%100!=0){
				textTip.setText("<html><font color=\"#ff0000\">请重新输入!</font></html>");
				moneyText.setText("");
			}else{
				process(m);
			}
		}
	}
	
	
	public void process(int money){
		//检查是否超过当日取款额
		if(Float.valueOf(MyUtil.getCardInformation().getMoney())+money>20000){
			MyUtil.panelLeap(this, new TransResult(TransResult.WITHDRAWAL, TransResult.TRANS_FAIL, 0,0, TransResult.FAIL_DESCRIBE2), clocker);
			return;
		}
		//检查是中行卡还是其他银行卡
		int belong=MyUtil.getCardInformation().getBelong();
		//手续费
		float serviceCharge=0;
		//检查卡类型
		if(MyUtil.getCardInformation().getType()==1){
			//信用卡
			
			if(belong==1){
				//中行卡
				if(money>Float.valueOf(MyUtil.getCardInformation().getBalance())+10000){
					MyUtil.panelLeap(this, new TransResult(TransResult.WITHDRAWAL,TransResult.TRANS_FAIL, 0,0, TransResult.FAIL_DESCRIBE3), clocker);
					return;
				}
			}else{
				//其他银行卡
				//跨行取款需要收取手续费，检查取款金额加上手续费是否超过透支额
				serviceCharge=(float) (money*0.01);
				if(money+serviceCharge>Float.valueOf(MyUtil.getCardInformation().getBalance())+10000){
					MyUtil.panelLeap(this, new TransResult(TransResult.WITHDRAWAL,TransResult.TRANS_FAIL, 0,0, TransResult.FAIL_DESCRIBE3), clocker);
					return;
				}
			}
			
		}else{
			//储蓄卡
			//检查账户余额是否大于取款金额加上手续费
			if(belong==1){
				//中行卡
				
				if(money>Float.valueOf(MyUtil.getCardInformation().getBalance())){
					MyUtil.panelLeap(this, new TransResult(TransResult.WITHDRAWAL,TransResult.TRANS_FAIL, 0,0, TransResult.FAIL_DESCIRBE1), clocker);
					return;
				}
			}else{
				//其它银行卡
				serviceCharge=(float) (money*0.01);
				if(money+serviceCharge>Float.valueOf(MyUtil.getCardInformation().getBalance())){
					MyUtil.panelLeap(this, new TransResult(TransResult.WITHDRAWAL,TransResult.TRANS_FAIL, 0,0, TransResult.FAIL_DESCIRBE1), clocker);
					return;
				}
			}
		}
		//满足取款条件
		float newBalance=Float.valueOf(MyUtil.getCardInformation().getBalance())-(money+serviceCharge);
		float newMoney=Float.valueOf(MyUtil.getCardInformation().getMoney())+money;
		System.out.println("当日取现额:"+MyUtil.getCardInformation().getMoney());
		String sql="update BankCard set balance="+newBalance+",money="+newMoney+" where cardID='"+MyUtil.getCardInformation().getCardID()+"'";
		MyUtil.panelLeap(this, new ATMWait(ATMWait.UPDATE,sql,money+"","withdrawal",serviceCharge+""), clocker);
		
	}
}

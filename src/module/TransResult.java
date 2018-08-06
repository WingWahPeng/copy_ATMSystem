package module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bean.ATMButton;
import bean.ATMPanel;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

public class TransResult extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String WITHDRAWAL="取款";
	public static final String DEPOSIT="存款";
	public static final String TRANSFER="转账";
	public static final String TRANS_SUCCESS="成功";
	public static final String TRANS_FAIL="失败";
	public static final String FAIL_DESCIRBE1="您的账户余额不足";
	public static final String FAIL_DESCRIBE2="当日取款限额超过20000";
	public static final String FAIL_DESCRIBE3="您的信用卡透支额过大，无法进行取款";
	
	private JButton continueTrans,showBalance,returnCard,backMainWindow;
	private JLabel specificLabel,tips;
	private Clocker clocker;
	private String transType;
	private DecimalFormat df = new DecimalFormat("0.00");
	
	public TransResult(String transType,String resultCode,float transMoney,float serviceCharge, String describe){
		super("resource/ATM.png");
		
		
		this.transType=transType;
		
		clocker=new Clocker(30);
		clocker.setTimeout(new Timeout(){

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				if(transType==WITHDRAWAL)
					MyUtil.panelLeap(TransResult.this, new Withdrawal(), null);
				else if(transType==DEPOSIT){
					MyUtil.panelLeap(TransResult.this,new Deposit(), null);
				}else{
					MyUtil.panelLeap(TransResult.this,new TransferAccount(), null);
				}	
			}
			
		});
		clocker.setBounds(592, 10, 90, 70);
		clocker.startCountDown();
		add(clocker);
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		if(resultCode.equals(TRANS_SUCCESS)){
			specificLabel=new JLabel("<html><center>\u4EA4\u6613"+resultCode+"</center><br><br><center><font color=\"#ff0000\">\u4EA4\u6613\u91D1\u989D\uFF1A</font>"+df.format(transMoney)+"</center><center><font color=\"#ff0000\">\u624B\u7EED\u8D39\uFF1A</font>"+df.format(serviceCharge)+"</center></html>");
			
		}
		else{
			
			specificLabel=new JLabel("<html><center>\u4EA4\u6613"+resultCode+"</center><br><center><font color=\"#ff0000\">"+describe+"</font></center></html>");
		
		}
		specificLabel.setBounds(300, 176, 304, 200);
		specificLabel.setFont(new Font("宋体", Font.BOLD, 25));
		specificLabel.setHorizontalAlignment(SwingConstants.CENTER);
		specificLabel.setVerticalAlignment(SwingConstants.TOP);
		add(specificLabel);
		
		continueTrans=new ATMButton("继续"+transType);
		continueTrans.setBounds(0, 417, 190, 45);
		continueTrans.addActionListener(this);
		add(continueTrans);
		
		
		showBalance=new ATMButton("显示余额");
		showBalance.setBounds(710, 417, 190, 45);
		showBalance.addActionListener(this);
		add(showBalance);
		
		
		returnCard=new ATMButton("退卡");
		returnCard.setBounds(0, 520, 190, 45);
		returnCard.addActionListener(this);
		add(returnCard);
		
		
		backMainWindow=new ATMButton("回主菜单");
		backMainWindow.setBounds(710, 520, 190, 45);
		backMainWindow.addActionListener(this);
		add(backMainWindow);
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object action=arg0.getSource();
		if(action==continueTrans){
			if(transType.equals(WITHDRAWAL)){
				MyUtil.panelLeap(this, new Withdrawal(), clocker);
			}else if(transType.equals(DEPOSIT)){
				MyUtil.panelLeap(this, new Deposit(), clocker);
			}else{
				MyUtil.panelLeap(this, new TransferAccount(), clocker);
			}
			
		}else if(action==showBalance){
			
			MyUtil.panelLeap(this, new Check(), clocker);
			
		}else if(action==returnCard){
			
			MyUtil.panelLeap(this, new Welcome(), clocker);

		}else{
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);

		}
	}
}

package module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;




import toolkit.MyUtil;
import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMWait;
import bean.CardInformation;
import bean.Clocker;
import bean.Clocker.Timeout;

import java.awt.Color;

public class Check extends 	ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton deposit, transferAccount, withdrawal, transDetail, backMainWindow;
	private Clocker clocker;
	private JLabel tips, label, zhl, qzl, qkl, zzl, kyl, zh, qz, qk, zz, ky;
	private double balance, zzj, yqkj;
	private DecimalFormat df = new DecimalFormat("0.00");
	
	public Check(){
		super("resource/ATM.png");
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		label=new JLabel("<html>您的账户余额信息</html>");
		label.setFont(new Font("宋体", Font.BOLD, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(349, 158, 218, 45);
		add(label);
		
		deposit = new ATMButton("存款");
		deposit.setBounds(0, 329, 190, 45);
		add(deposit);
		deposit.addActionListener(this);
		

		transferAccount = new ATMButton("转账");
		transferAccount.setBounds(0, 428, 190, 45);
		add(transferAccount);
		transferAccount.addActionListener(this);
		
		
		withdrawal = new ATMButton("取款");
		withdrawal.setBounds(0, 524, 190, 45);
		add(withdrawal);
		withdrawal.addActionListener(this);
		
		
		transDetail=new ATMButton("交易明细");
		transDetail.setBounds(710, 428, 190, 45);
		add(transDetail);
		transDetail.addActionListener(this);
		
		
		backMainWindow=new ATMButton("回主菜单");
		backMainWindow.setBounds(710, 524, 190, 45);
		add(backMainWindow);
		backMainWindow.addActionListener(this);
		
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(Check.this, new MainWindow(), null);
			}
		});
		
		zhl = new JLabel("账户余额：");                  //账户余额Label
		zhl.setForeground(new Color(153, 0, 0));
		zhl.setFont(new Font("宋体", Font.PLAIN, 22));
		zhl.setBounds(375, 235, 110, 25);
		add(zhl);
		
		
		qkl = new JLabel("可取现金额：");
		qkl.setForeground(new Color(153, 0, 0));
		qkl.setFont(new Font("宋体", Font.PLAIN, 22));
		qkl.setBounds(355, 280, 135, 25);
		add(qkl);
		
		zzl = new JLabel("可转账金额：");
		zzl.setForeground(new Color(153, 0, 0));
		zzl.setFont(new Font("宋体", Font.PLAIN, 22));
		zzl.setBounds(355, 325, 135, 25);
		add(zzl);
		
		
		qzl = new JLabel();
		qzl.setForeground(new Color(153, 0, 0));
		qzl.setFont(new Font("宋体", Font.PLAIN, 22));
		qzl.setBounds(375, 370, 110, 25);
		add(qzl);
		
		kyl = new JLabel();
		kyl.setForeground(new Color(153, 0, 0));
		kyl.setFont(new Font("宋体", Font.PLAIN, 22));
		kyl.setBounds(375, 415, 110, 25);
		add(kyl);
		
		
		moneyDisplay();
		
		
		setSize(900,600);
		setLayout(null);
	}
	
	
	
	private void moneyDisplay(){
		//读取指定账号的余额等信息并显示
		CardInformation cif=MyUtil.getCardInformation();
		balance=Double.valueOf(cif.getBalance());
		
		
		zh = new JLabel(df.format(Double.valueOf(cif.getBalance())));                         //账户余额
		zh.setFont(new Font("宋体", Font.PLAIN, 22));
		zh.setBounds(475, 235, 120, 25);
		add(zh);	
		
		
		yqkj = Double.valueOf(cif.getMoney());                     //获取已取款金额
		qk = new JLabel(df.format(Math.min(balance, 20000-yqkj)));          //取款金额
		qk.setFont(new Font("宋体", Font.PLAIN, 22));
		qk.setBounds(475, 280, 120, 25);
		add(qk);
		
		
		zzj = Double.valueOf(cif.getTransferMoney());             //获取还可转账金额
		zz = new JLabel(df.format(Math.min(zzj,balance)));            //转账金额
		zz.setFont(new Font("宋体", Font.PLAIN, 22));
		zz.setBounds(475, 325, 120, 25);
		add(zz);
		
		
		
		if(cif.getType()==1){
			qzl.setText("欠款金额：");
			kyl.setText("可用金额：");
			
			qz = new JLabel();      //欠款金额
			qz.setFont(new Font("宋体", Font.PLAIN, 22));
			qz.setBounds(475, 370, 120, 25);
			add(qz);
			
			ky = new JLabel();      //可用余额
			ky.setFont(new Font("宋体", Font.PLAIN, 22));
			ky.setBounds(475, 415, 120, 25);
			add(ky);
			
			if(balance<0){
				qz.setText(df.format(Math.abs(balance)));          //欠款金额
				ky.setText(df.format(10000-Math.abs(balance)));      //可用余额
				System.out.println(df.format(Math.abs(balance)));
				System.out.println(df.format(10000-Math.abs(balance)));
				
				zh.setText("0.00");
				qk.setText("0.00");
				zz.setText("0.00"); 
			}            
			else{
				qz.setText("0.00");
				ky.setText("10000.00");
			}
		}
		       
		
		
		if(MyUtil.getCardInformation().getStatus()==2){
			qk.setText("0.00");
			zz.setText("0.00");
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==deposit){
			
			MyUtil.panelLeap(this, new Deposit(), clocker);
			
		}else if(arg0.getSource()==transferAccount){
			
			MyUtil.panelLeap(this, new TransferConfirm(), clocker);
			
		}else if(arg0.getSource()==withdrawal){

			MyUtil.panelLeap(this, new Withdrawal(), clocker);

		}else if(arg0.getSource()==transDetail){
			LocalDate today, todayBefore;
			//获取当天的日期和3个月前当天的日期
			today = LocalDate.now();
			todayBefore = LocalDate.of(today.getYear(), today.getMonthValue()-3, today.getDayOfMonth());
			String account = MyUtil.getCardInformation().getCardID();                  //获取当前银行账号
			String[] sqls = {"select * from HistoryTransaction where cardID = '"  + account + "' " +
			      "and transDate between '" + todayBefore + "' and '" + today + "' ORDER BY historyID DESC"};
			MyUtil.panelLeap(this, new ATMWait(ATMWait.HISTORY,sqls), clocker);

		}else{

			MyUtil.panelLeap(this, new MainWindow(), clocker);

		}
	}
}

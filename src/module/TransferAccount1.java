package module;

import javax.swing.JButton;
import javax.swing.JLabel;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.ATMWait;
import bean.CardInformation;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;

public class TransferAccount1 extends ATMPanel implements ActionListener {
	/**
	 * 转账账号输入界面
	 */
	private static final long serialVersionUID = 1L;
	private JButton backMainWindow, yesButton;
	private JLabel tips, tips1, tips2;
	private ATMTextField accountText;
	private Clocker clocker;
	public  static CardInformation cInfo1=null;
	
	public TransferAccount1() {
		super("resource/transferTips.png");
			
		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.setTimeout(new Timeout() {
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransferAccount1.this, new Welcome(), null);
			}

		});
		add(clocker);
		clocker.startCountDown();

		backMainWindow = new ATMButton("回主菜单");
		backMainWindow.setBounds(0, 520, 190, 45);
		add(backMainWindow);
		backMainWindow.addActionListener(this);

		yesButton = new ATMButton("确认");
		yesButton.setBounds(710, 520, 190, 45);
		add(yesButton);
		yesButton.addActionListener(this);

		accountText = new ATMTextField(275,45);
		accountText.setHorizontalAlignment(SwingConstants.CENTER);
		accountText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		accountText.setBounds(313, 190,275, 45);
		add(accountText);
		accountText.addKeyListener(new KeyAdapter(){//键盘事件监听器
			public void keyTyped(KeyEvent e) {  
			    // TODO Auto-generated method stub  
			    String s = accountText.getText();  
			    Pattern p = Pattern.compile("[0-9]");
			    Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			    boolean b = m.matches();
			    if(s.length() >= 18 || !b) e.consume(); 
			    tips2.setText("");
			}  
		});

		tips = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);

		tips1 = new JLabel("\u8BF7\u8F93\u5165\u8F6C\u5165\u5361\u53F7");
		tips1.setLocation(338, 130);
		tips1.setFont(new Font("宋体", Font.BOLD, 30));
		tips1.setSize(224, 45);
		add(tips1);

		tips2=new JLabel();
		tips2.setHorizontalAlignment(SwingConstants.CENTER);
		if(MyUtil.getProblem()==null){
			tips2.setText("");
		}else{
			tips2.setText(MyUtil.getProblem());
		}
		tips2.setFont(new Font("宋体", Font.BOLD, 15));
		tips2.setForeground(new Color(128, 0, 0));
		tips2.setBounds(266,246,368,34);
		add(tips2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object action = e.getSource();
		if (action == backMainWindow) {
			MyUtil.panelLeap(this, new MainWindow(), clocker);

		} else {
			String text=accountText.getText();
			if(text.equals(MyUtil.getCardInformation().getCardID())){
				tips2.setText("请重新输入！");
			}
			String sql="select * from BankCard where cardID='"+text+"'";
			MyUtil.panelLeap(this, new ATMWait(ATMWait.QUERY,sql), clocker);
		}

	}
}

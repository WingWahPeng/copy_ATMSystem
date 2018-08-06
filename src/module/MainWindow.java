package module;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bean.ATMButton;
import bean.ATMPanel;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends ATMPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton check, changePW, transferAccount, withdrawal, deposit,
			returnCard;
	private JLabel tips, userInformation;
	private Clocker clocker;
	private String name;

	public MainWindow() {
		super("resource/ATM.png");
		MyUtil.setProblem(null);
		
		
		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.setTimeout(new Timeout() {

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(MainWindow.this, new Welcome(), null);

			}

		});
		add(clocker);
		clocker.startCountDown();

		tips = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);

		name = MyUtil.getCustomerInformation().getCustomerName()
				.substring(0, 1);
		userInformation = new JLabel();
		userInformation.setHorizontalAlignment(SwingConstants.CENTER);
		userInformation.setVerticalAlignment(SwingConstants.TOP);
		userInformation.setFont(new Font("宋体", Font.PLAIN, 30));
		userInformation.setBounds(200, 168, 500, 234);
		add(userInformation);

		check = new ATMButton("查询");
		add(check);
		check.setBounds(0, 329, 190, 45);
		check.addActionListener(this);

		changePW = new ATMButton("改密");
		add(changePW);
		changePW.setBounds(0, 428, 190, 45);
		changePW.addActionListener(this);

		transferAccount = new ATMButton("转账");
		add(transferAccount);
		transferAccount.setBounds(0, 524, 190, 45);
		transferAccount.addActionListener(this);

		withdrawal = new ATMButton("取款");
		add(withdrawal);
		withdrawal.setBounds(710, 329, 190, 45);
		withdrawal.addActionListener(this);

		deposit = new ATMButton("存款");
		add(deposit);
		deposit.setBounds(710, 428, 190, 45);
		deposit.addActionListener(this);

		returnCard = new ATMButton("退卡");
		add(returnCard);
		returnCard.setBounds(710, 524, 190, 45);
		returnCard.addActionListener(this);

		checkCardStatus();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object action = arg0.getSource();
		if (action == check) {
			
			MyUtil.panelLeap(this, new Check(), clocker);
			
		} else if (action == changePW) {

			MyUtil.panelLeap(this, new ChangePW_1(), clocker);
			
		} else if (action == transferAccount) {

			MyUtil.panelLeap(this, new TransferConfirm(), clocker);

		} else if (action == withdrawal) {

			MyUtil.panelLeap(this, new Withdrawal(), clocker);

		} else if (action == deposit) {

			MyUtil.panelLeap(this, new Deposit(), clocker);

		} else {

			MyUtil.panelLeap(this, new Welcome(), clocker);

		}
	}

	private void checkCardStatus() {
		switch (MyUtil.getCardInformation().getStatus()) {
		case 1:
			userInformation
					.setText("<html><p>"
							+ name
							+ "\u5148\u751F,\u60A8\u597D\uFF01</p><br><p>\u8BF7\u60A8\u9009\u62E9\u4EA4\u6613</p></html>");

			break;
		case 2:
			changePW.setEnabled(false);
			transferAccount.setEnabled(false);
			withdrawal.setEnabled(false);
			deposit.setEnabled(false);
			userInformation
					.setText("<html><center>"+name+"\u5148\u751F,\u60A8\u597D</center><br><p>\u60A8\u7684\u8D26\u6237\u88AB\u51BB\u7ED3,\u53EA\u80FD\u63D0\u4F9B\u67E5\u8BE2\u670D\u52A1\uFF01</p></html>");

			break;
		default:

		}
	}
}

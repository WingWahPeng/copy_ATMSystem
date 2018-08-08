package bean;

import javax.swing.JLabel;

import toolkit.MyUtil;
import toolkit.XMLUtil;
import bean.DatabaseBean.OnDBProcessingFinish;
import module.*;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ATMWait extends ATMPanel {
	/**
	 *等待面板 
	 */
	private static final long serialVersionUID = 1L;
	public final static int QUERY = 1;
	public final static int UPDATE = 2;
	public final static int HISTORY=3;

	private JLabel lblNewLabel, advLabel, tipsLabel;
	private String[] values;

	private CardInformation cInfo;
	private CustomerInformation cuInfo;

	private float serviceCharge = 0; // 手续费
	private String transType = null; // 存取款，转账

	public ATMWait(int type, String... values) {
		super("resource/ATM.png");
		if (values != null) {
			this.values = values;
		}
//右上角提示标签
		tipsLabel = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tipsLabel.setFont(new Font("宋体", Font.BOLD, 22));
		tipsLabel.setBounds(692, 10, 208, 67);
		add(tipsLabel);
//广告标签
		advLabel = new DynGifLabel(Toolkit.getDefaultToolkit().createImage(
				XMLUtil.parse("adv")));
		advLabel.setBounds(200, 230, 500, 300);
		add(advLabel);
//中间提示标签
		lblNewLabel = new JLabel(
				"\u64CD\u4F5C\u6B63\u5728\u8FDB\u884C\u4E2D\uFF0C\u8BF7\u7A0D\u7B49\u3002\u3002\u3002");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 30));
		lblNewLabel.setBounds(248, 139, 405, 53);
		add(lblNewLabel);

		switch (type) {
		case QUERY:
			query();
			break;
		case UPDATE:
			update();
			break;
		case HISTORY:
			history();
			break;
		}
	}

	//查询操作
	public void query() {
		String sql = values[0];
		if(values.length>=2){
		String password = values[1];
		Entrance.getDBInstance().executeQuery(sql, new OnDBProcessingFinish() {	//启动查询线程
			@Override
			public void onFinish(Object j) {
				// TODO Auto-generated method stub
				ResultSet rs = (ResultSet) j;
				try {
					if (rs.next()) {

						cInfo = new CardInformation();
						cInfo.setCardID(rs.getString("cardID"));
						cInfo.setBalance(rs.getString("balance"));
						cInfo.setMoney(rs.getString("money"));
						cInfo.setTransferMoney(rs.getString("transferMoney"));
						cInfo.setStatus(rs.getInt("status"));
						cInfo.setPassword(rs.getString("password"));
						cInfo.setBelong(rs.getInt("belong"));
						cInfo.setType(rs.getInt("type"));
						cInfo.setPwdError(rs.getInt("pwdError"));
						
						MyUtil.setCardInformation(cInfo);

						if (cInfo.getStatus() == 3) {// 卡状态为挂失或注销时，无法登录

							MyUtil.setProblem("该账户已挂失，无法登录！");
							MyUtil.panelLeap(ATMWait.this, new Login(), null);

						} else if (cInfo.getStatus() == 4) {

							MyUtil.setProblem("该账户已注销，无法登录！");
							MyUtil.panelLeap(ATMWait.this, new Login(), null);

						} else {
							// 该账户有登录的许可，检查密码是否正确
							if (cInfo.getPassword().equals(password)) {
								Entrance.getDBInstance().executeQuery(
										"select * from Customer where cardID='"
												+ cInfo.getCardID() + "'",
										new OnDBProcessingFinish() {
											@Override
											public void onFinish(Object j) {
												// TODO Auto-generated method
												// stub
												ResultSet rs2 = (ResultSet) j;
												try {
													if (rs2.next()) {
														cuInfo = new CustomerInformation();	//实例化客户对象
														cuInfo.setCustomerName(rs2
																.getString("customerName"));
														cuInfo.setCardID(rs2
																.getString("cardID"));
														cuInfo.setPID(rs2
																.getString("PID"));
														cuInfo.setSex(rs2
																.getString("sex"));
														cuInfo.setBirth(rs2
																.getString("birth"));
													}

													MyUtil.setCustomerInformation(cuInfo);	//备份客户对象
													MyUtil.panelLeap(
															ATMWait.this,
															new MainWindow(),
															null);

												} catch (SQLException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}

										});
							} else {
								
								if(MyUtil.getCardInformation().getPwdError()>1){
									MyUtil.setProblem("您已输错三次，该账户被临时冻结！");
									//用户一天输错三次，卡被冻结
									String[] sqls={"update BankCard set status=2 where cardID='"+MyUtil.getCardInformation().getCardID()+"'"};
									Entrance.getDBInstance().executeUpdate(sqls, new OnDBProcessingFinish(){

										@Override
										public void onFinish(Object j) {
											// TODO Auto-generated method stub
											MyUtil.panelLeap(ATMWait.this, new Login(),
										null);
										}
										
									});
								}else{
									MyUtil.setProblem("密码输入错误！");
									//统计用户输错次数
									int count=MyUtil.getCardInformation().getPwdError();
									MyUtil.getCardInformation().setPwdError(++count);
									String[] sqls={"update BankCard set pwdError="+count+" where cardID='"+MyUtil.getCardInformation().getCardID()+"'"};
									Entrance.getDBInstance().executeUpdate(sqls, new OnDBProcessingFinish(){
										@Override
										public void onFinish(Object j) {
											// TODO Auto-generated method stub
											MyUtil.panelLeap(ATMWait.this, new Login(),
										null);
										}
										
									});
								}
								
							}

						}

					} else {

						MyUtil.setProblem("该账户不存在！");
						MyUtil.panelLeap(ATMWait.this, new Login(), null);

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		}else{   //转账所作的操作
			Entrance.getDBInstance().executeQuery(sql, new OnDBProcessingFinish(){
				@Override
				public void onFinish(Object j) {
					// TODO Auto-generated method stub
					ResultSet rs=(ResultSet)j;
					try {
						if(rs.next()){
							
							CardInformation cInfo1=new CardInformation();
							TransferAccount1.cInfo1=cInfo1;
							cInfo1.setCardID(rs.getString("cardID"));
							cInfo1.setBalance(rs.getString("balance"));
							cInfo1.setStatus(rs.getInt("status"));
							cInfo1.setTransferMoney(rs.getString("transferMoney"));
							cInfo1.setBelong(rs.getInt("belong"));
									
							//检查是否为中行账户登录，只有中行的账户才可进行转帐
						if(MyUtil.getCardInformation().getBelong()==1&&
									TransferAccount.flag==cInfo1.getBelong()){
								//1 "正常"，2 "冻结"，3 "挂失"，4 "注销"
									if(cInfo1.getStatus()==1 || cInfo1.getStatus()==2){
										
										MyUtil.panelLeap(ATMWait.this, new TransferAccount2(), null);
										
									}else if(cInfo1.getStatus()==3){
										
										MyUtil.setProblem("您输入的账号已被挂失！");
										MyUtil.panelLeap(ATMWait.this, new TransferAccount1(), null);
										
									}else{
										
										MyUtil.setProblem("您输入的账号已被注销！");
										MyUtil.panelLeap(ATMWait.this, new TransferAccount1(), null);
									}
							}else{
								if(TransferAccount.flag==1&&cInfo1.getBelong()==2){
									
									MyUtil.setProblem("您输入的账号为非本行，请重新输入或选择行内转账！");
									
								}
								if(TransferAccount.flag==2&&cInfo1.getBelong()==1){
									
									MyUtil.setProblem("您输入的账号为本行，请重新输入或选择跨行转账！");
									
								}
								
									MyUtil.panelLeap(ATMWait.this, new TransferAccount1(), null);
							}
									
						}else{
							MyUtil.setProblem("您输入的账号不存在！");
							MyUtil.panelLeap(ATMWait.this, new TransferAccount1(), null);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		}
	}

	//更改操作
	public void update() {
		String sql = values[0];
		String sql2 = null, sql3 = null;
		float m=Float.valueOf(values[1]);
		if ("deposit".equals(values[2])) {
			transType = TransResult.DEPOSIT;
			sql2 = "insert into HistoryTransaction(cardID,transType,transMoney,transDate) values('"
					+ MyUtil.getCardInformation().getCardID()
					+ "','存款',"
					+ m
					+ ",'"
					+ new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()) + "')";
			sql3 = "";

		} else if ("withdrawal".equals(values[2])) {
			transType = TransResult.WITHDRAWAL;
			sql2 = "insert into HistoryTransaction(cardID,transType,transMoney,transDate) values('"
					+ MyUtil.getCardInformation().getCardID()
					+ "','取款',"
					+ m
					+ ",'"
					+ new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()) + "')";
			sql3 = "";
		} else if("transfer".equals(values[2])){
			transType = TransResult.TRANSFER;
			sql2 = "insert into HistoryTransaction(cardID,transType,transMoney,transDate,targetAccount) values('"
					+ MyUtil.getCardInformation().getCardID()
					+ "','转出',"
					+ m
					+ ",'"
					+ new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()) + "','"
					+TransferAccount1.cInfo1.getCardID()+"')";
			
			//转账时的收款方卡ID
			sql3 = "insert into HistoryTransaction(cardID,transType,transMoney,transDate,targetAccount) values('"
					+TransferAccount1.cInfo1.getCardID()
					+ "','转入',"
					+ m
					+ ",'"
					+ new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()) + "','"
					+MyUtil.getCardInformation().getCardID()+"')";
			
		}else{
			sql2="";
			sql3="";
		}

		if (values.length == 4)
			serviceCharge = Float.valueOf(values[3]);
		String[] sqls = { sql, sql2, sql3 };	
		Entrance.getDBInstance().executeUpdate(sqls,new OnDBProcessingFinish() {//启动更新线程
					@Override
					public void onFinish(Object j) {
						// TODO Auto-generated method stub
						int[] n = (int[]) j;
						if (n.length > 0) {
							
							if("changepassword".equals(values[2])){
								//改密
								MyUtil.getCardInformation().setPassword(values[1]);
								MyUtil.panelLeap(ATMWait.this, new ChangePW_3(), null);
							}else{
								//存取款，转账
								if("transfer".equals(values[2])){
									MyUtil.getCardInformation().setTransferMoney((Float.valueOf(MyUtil.getCardInformation().getTransferMoney())-m)+"");
								}else if("withdrawal".equals(values[2])){	
							MyUtil.getCardInformation().setMoney((Float.valueOf(MyUtil.getCardInformation().getMoney())+m) + "");
								}
								if("deposit".equals(values[2])){
									MyUtil.getCardInformation().setBalance(
											(Float.valueOf(MyUtil.getCardInformation()
													.getBalance()) + m - serviceCharge)
													+ "");
								}else{
									MyUtil.getCardInformation().setBalance(
									(Float.valueOf(MyUtil.getCardInformation()
											.getBalance()) - m - serviceCharge)
											+ "");
								}
								
								MyUtil.panelLeap(ATMWait.this, new TransResult(
									transType, TransResult.TRANS_SUCCESS, m,
									serviceCharge, null), null);
							}
							
						}
					}
				});
	}
	
//历史操作	
	public void history(){
		String sql=values[0];
		Entrance.getDBInstance().executeQuery(sql, new OnDBProcessingFinish(){

			@Override
			public void onFinish(Object j) {
				// TODO Auto-generated method stub
				ResultSet rs=(ResultSet)j;
				MyUtil.panelLeap(ATMWait.this, new TransDetail_1(rs), null);
			}
			
		});
	}
}

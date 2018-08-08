package toolkit;


import javax.swing.JPanel;

import bean.CardInformation;
import bean.Clocker;
import bean.CustomerInformation;
import module.Entrance;

public class MyUtil {
	private static String problem=null;		//错误信息
	private static CardInformation cardInfo=null;	//card信息
	private static CustomerInformation cusInfo=null;	//顾客信息
//移除或关闭面板
	public static void clearPanel(JPanel j){
		Entrance.getEntranceInstance().getContentPane().remove(j);
		System.gc();	//调用垃圾收集器
	}
	
	public static void addPanel(JPanel j){
		Entrance.getEntranceInstance().getContentPane().add(j);
	}
	
//窗口面板跳动实现方法，计时器
	public static void panelLeap(JPanel original,JPanel purpose,Clocker clocker){
		Entrance.getEntranceInstance().getContentPane().add(purpose);
		if(clocker!=null){
			clocker.stopCountDown();
		}
		original.setVisible(false);
		//Entrance.getEntranceInstance().getContentPane().remove(original);
		
		System.gc();//调用垃圾收集器
	}
	
	public static void setCardInformation(CardInformation cardInfo){
		MyUtil.cardInfo=cardInfo;
	}	
	
	public static CardInformation getCardInformation(){
		return cardInfo;
	}

	public static CustomerInformation getCustomerInformation() {
		return cusInfo;
	}

	public static void setCustomerInformation(CustomerInformation cusInfo) {
		MyUtil.cusInfo = cusInfo;
	}
	
	//用于登录失败时，在登录界面显示原因或转账界面显示失败原因
	public static String getProblem() {
		return problem;
	}
	//欢迎界面和主界面会清空值
	public static void setProblem(String problem) {
		MyUtil.problem = problem;
	}
}

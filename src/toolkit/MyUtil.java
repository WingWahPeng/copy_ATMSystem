package toolkit;


import javax.swing.JPanel;

import bean.CardInformation;
import bean.Clocker;
import bean.CustomerInformation;
import module.Entrance;

public class MyUtil {
	private static String problem=null;
	private static CardInformation cardInfo=null;
	private static CustomerInformation cusInfo=null;
	
	public static void clearPanel(JPanel j){
		Entrance.getEntranceInstance().getContentPane().remove(j);
		System.gc();
	}
	
	public static void addPanel(JPanel j){
		Entrance.getEntranceInstance().getContentPane().add(j);
	}
	
	
	public static void panelLeap(JPanel original,JPanel purpose,Clocker clocker){
		
		Entrance.getEntranceInstance().getContentPane().add(purpose);
		if(clocker!=null){
			clocker.stopCountDown();
		}
		original.setVisible(false);
		//Entrance.getEntranceInstance().getContentPane().remove(original);
		
		System.gc();
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
	
	
	//���ڵ�¼ʧ��ʱ���ڵ�¼������ʾԭ���ת�˽�����ʾʧ��ԭ��
	public static String getProblem() {
		return problem;
	}
	//��ӭ���������������ֵ
	public static void setProblem(String problem) {
		MyUtil.problem = problem;
	}
}

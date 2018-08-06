package module;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import bean.ATMButton;
import bean.ATMPanel;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

public class TransDetail_2 extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton ok, back, backMainWindow;
	private Clocker clocker;
	private JLabel tips, start, end;
	private JTextField startYear, startMonth, startDate, endYear, endMonth, endDate;
	private JScrollPane scr;
	private JTable table;	
	private DefaultTableCellRenderer render, rightRender;
	private Dimension viewSize;
	private JTableHeader header;
	private TableModel model;
	private String transType, transMoney, transDate,targetAccount, sDate, eDate;
	private ResultSet rs;
	private JLabel label, label_1, label_2, label_3, label_4, label_5, error;
	
	
	
	public TransDetail_2(ResultSet rs){
		super("resource/ATM.png");
		this.rs=rs;
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		start = new JLabel("起始日期：");
		start.setFont(new Font("宋体", Font.PLAIN, 20));
		start.setBounds(154, 156, 100, 25);
		add(start);
		
		end = new JLabel("截止日期：");
		end.setFont(new Font("宋体", Font.PLAIN, 20));
		end.setBounds(477, 156, 100, 25);
		add(end);
		
		startYear = new JTextField();
		startYear.setFont(new Font("宋体", Font.PLAIN, 14));
		startYear.setBounds(248, 156, 40, 25);
		add(startYear);
		startYear.setColumns(5);
		label = new JLabel("年");
		label.setBounds(292, 163, 17, 15);
		add(label);
		
		startMonth = new JTextField();
		startMonth.setFont(new Font("宋体", Font.PLAIN, 14));
		startMonth.setColumns(5);
		startMonth.setBounds(309, 156, 30, 25);
		add(startMonth);
		label_1 = new JLabel("月");
		label_1.setBounds(344, 163, 17, 15);
		add(label_1);
		
		startDate = new JTextField();
		startDate.setFont(new Font("宋体", Font.PLAIN, 14));
		startDate.setColumns(5);
		startDate.setBounds(361, 156, 30, 25);
		add(startDate);
		label_2 = new JLabel("日");
		label_2.setBounds(396, 163, 17, 15);
		add(label_2);
		
		endYear = new JTextField();
		endYear.setFont(new Font("宋体", Font.PLAIN, 14));
		endYear.setColumns(5);
		endYear.setBounds(570, 156, 40, 25);
		add(endYear);
		label_3 = new JLabel("年");
		label_3.setBounds(614, 163, 17, 15);
		add(label_3);
		
		endMonth = new JTextField();
		endMonth.setFont(new Font("宋体", Font.PLAIN, 14));
		endMonth.setColumns(5);
		endMonth.setBounds(631, 156, 30, 25);
		add(endMonth);
		label_4 = new JLabel("月");
		label_4.setBounds(665, 163, 17, 15);
		add(label_4);
		
		endDate = new JTextField();
		endDate.setFont(new Font("宋体", Font.PLAIN, 14));
		endDate.setColumns(5);
		endDate.setBounds(682, 156, 30, 25);
		add(endDate);
		label_5 = new JLabel("日");
		label_5.setBounds(717, 163, 17, 15);
		add(label_5);
		
		
		back=new ATMButton("返回");
		back.setBounds(0, 524, 190, 45);
		add(back);
		back.addActionListener(this);
		
		backMainWindow=new ATMButton("回主菜单");
		add(backMainWindow);
		backMainWindow.addActionListener(this);
		
		ok=new ATMButton("确定");
		ok.setBounds(710, 524, 190, 45);
		add(ok);
		ok.addActionListener(this);
		
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransDetail_2.this, new MainWindow(), null);
			}
			
		});
		
		error = new JLabel();
		error.setForeground(new Color(102, 0, 0));
		error.setFont(new Font("宋体", Font.PLAIN, 18));
		error.setBounds(306, 260, 288, 25);
		add(error);
		
		setSize(900,600);
		setLayout(null);	
	}
	
	
	
	
	
	private void tableDesign(){
		table = new JTable(0, 0);
		table.setRowHeight(30);            //设置每行单元格的高度
		table.setFont(new Font("宋体", Font.BOLD, 18));     //设置表格中字体的大小
		
		
		model = new TableModel();
		table.setModel(model);
		
		
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(170); 
		table.getColumnModel().getColumn(4).setPreferredWidth(220);
		
		
		render = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, render);
		render.setHorizontalAlignment(JTextField.CENTER);
		
		//设置表头
		header = table.getTableHeader();
		header.setPreferredSize(new Dimension(30, 30));
		header.setFont(new Font("宋体", Font.BOLD, 16));
		header.setForeground(new Color(153, 0, 0));
		
	    //将最后一列设置为靠右对齐
		rightRender = new DefaultTableCellRenderer();
		rightRender.setOpaque(false);
		rightRender.setHorizontalAlignment(JTextField.RIGHT);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRender);
		
		//设置显示范围
		viewSize = new Dimension();
		viewSize.width = table.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * table.getRowHeight();
		table.setPreferredScrollableViewportSize(viewSize);
	}
	
	
	
	
	private void scrollDesign(){
		scr = new JScrollPane();
		scr.setSize(700, 242);
		scr.setLocation(100, 220);
		scr.setOpaque(false);
		scr.getViewport().setOpaque(false);
		scr.getVerticalScrollBar().setOpaque(false);
		scr.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==back){
			
			MyUtil.panelLeap(this, new TransDetail_1(rs), clocker);
			
		}else if(arg0.getSource()==ok){
			sDate = startYear.getText() + "-" + startMonth.getText() + "-" + startDate.getText();
			eDate = endYear.getText() + "-" + endMonth.getText() + "-" + endDate.getText();
			if(!startYear.getText().equals("") && !startMonth.getText().equals("") && !startDate.getText().equals("") &&
			   !endYear.getText().equals("") && !endMonth.getText().equals("") && !endDate.getText().equals("")){
				error.setText("");
				tableDesign();	
				scrollDesign();
				scr.setViewportView(table);
				add(scr);
				backMainWindow.setBounds(710, 524, 190, 45);
			}
			else{
				error.setText("请输入完整的起始日期和截止日期！");
			}
		}else if(arg0.getSource()==backMainWindow){
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);
			
		}
	}
	
	
	private class TableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] tableName={"序号", "交易日期", "交易类型", "交易金额", "转入(出)卡号"};
		@SuppressWarnings("rawtypes")
		private Vector content;
		@SuppressWarnings("rawtypes")
		private Vector<Comparable> vRow;
		private int count=0;
		
		
		@SuppressWarnings("rawtypes")
		public TableModel(){
			content = new Vector();
			readDataBase();
		}
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void readDataBase(){	
			try{

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				if(!rs.isBeforeFirst())
					rs.beforeFirst();
				while(rs.next()){
					if(df.parse(rs.getString(5)).getTime()>=df.parse(sDate).getTime()&&df.parse(rs.getString(5)).getTime()<=df.parse(eDate).getTime()){
					count++;
					
					transType = rs.getString(3);
					transMoney = rs.getString(4);
					transDate = rs.getString(5);
					targetAccount = rs.getString(6);
					
					vRow = new Vector<Comparable>();
					vRow.add(count+"");
					vRow.add(transDate);
					vRow.add(transType);
					if(transType.equals("存款") || transType.equals("转入"))
						vRow.add(transMoney + " CNY");
					else 
						vRow.add("-" + transMoney +" CNY");
					if(transType.equals("转入") || transType.equals("转出"))
						vRow.add(targetAccount);
					else
						vRow.add("******************");
					
					content.add(vRow.clone());
					}
				}
			}catch(Exception e){
				System.out.println(e.toString());
			}
		}
		
		
		
		public boolean isCellEditable(int row,int column) {       
	        return false;
	    }
		
		
		 @SuppressWarnings({ "unchecked", "rawtypes" })
		public void setValueAt(Object value, int row, int column) {
		        ((Vector)content.get(row)).remove(column);
		        ((Vector)content.get(row)).add(column, value);
		        this.fireTableCellUpdated(row, column);
		    }
		
		public String getColumnName(int column){
			return tableName[column];
		}
		
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return tableName.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return content.size();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Object getValueAt(int row, int column) {
			// TODO Auto-generated method stub
			return ((Vector)content.get(row)).get(column);
		}
		
	}
}




















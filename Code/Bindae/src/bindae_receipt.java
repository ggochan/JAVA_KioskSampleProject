
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/* -----------영수증------------ */
class bindae_receipt extends JFrame implements ActionListener{
	/* -----------DB관련------------ */
	private ResultSet rs=null;
	private Statement stmt=null;
	private PreparedStatement pstmdel=null;
	private String driver ="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String passwd = "1234";
	private Connection con = null;
	private ArrayList<inbindae> bin = new ArrayList<inbindae>();
	private ArrayList<inbasket> bba = new ArrayList<inbasket>();
	private ArrayList<inend> be = new ArrayList<inend>();
	int del=1;
	/* -----------레이아웃------------ */
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	
	JPanel p1, p2, p3, p4, p5;
	JLabel productla, countla, pricela;
	JLabel text, toprice2;
	Basic_Button toprice1;
	JLabel[] test1;
	JLabel[] test3;
	JLabel[] test2;
	Font f = new Font("맑은 고딕",Font.BOLD,35);
	Font f2 = new Font("맑은 고딕",Font.BOLD,20);
	Font f3 = new Font("맑은 고딕",Font.BOLD,25);
	bindae_receipt() {
		try {
			dbor();
			dbba();
			dbda();
		}
		catch(SQLException sq) {
			
		}
		/*-----------영수증-----------*/
		p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.WHITE);
		p1.setBounds(0, 0, x/2, y*1/8);
		text = new JLabel("영수증");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.BLACK);
		text.setFont(f);
		p1.add(text,BorderLayout.SOUTH);
		add(p1);
		/*----------가운데 정보-----------*/
		p2 = new JPanel(new GridLayout(10,1));
		p2.setBackground(Color.WHITE);
		p2.setBounds(x/2*1/5, y*3/14, x/2*1/5, y*7/14);
		
		test1 = new JLabel[bba.size()+1];
		test1[0]= new JLabel();
		test1[0].setText("상품명");
		test1[0].setFont(f2);
		test1[0].setHorizontalAlignment(SwingConstants.CENTER);
		p2.add(test1[0]);
		for(int i=0; i<bba.size(); i++) {
			inbasket ik = bba.get(i);
			test1[i+1]= new JLabel();
			for(int j=0; j<bin.size(); j++) {
			inbindae ib= bin.get(j);
			if(ik.getJP_num() == ib.getJp_num())
				{
				test1[i+1].setText(ib.getJp_name());
				}
			}
			test1[i+1].setFont(f2);
			test1[i+1].setHorizontalAlignment(SwingConstants.CENTER);
			p2.add(test1[i+1]);
			
		}
		add(p2);
		
		p3 = new JPanel(new GridLayout(10,1));
		p3.setBackground(Color.WHITE);
		p3.setBounds(x/2*2/5, y*3/14, x/2*1/5, y*7/14);
		test2 = new JLabel[bba.size()+1];
		test2[0]= new JLabel();
		test2[0].setText("수량");
		test2[0].setFont(f2);
		test2[0].setHorizontalAlignment(SwingConstants.CENTER);
		p3.add(test2[0]);
		for(int i=0; i<bba.size(); i++) {
			inbasket ik = bba.get(i);
			test2[i+1]= new JLabel();
			for(int j=0; j<bin.size(); j++) {
			inbindae ib= bin.get(j);
			if(ik.getJP_num() == ib.getJp_num())
				{
				test2[i+1].setText(ik.getJO_count()+"");
				}
			}
			test2[i+1].setFont(f2);
			test2[i+1].setHorizontalAlignment(SwingConstants.CENTER);
			p3.add(test2[i+1]);
		}
		add(p3);
		
		p4 = new JPanel(new GridLayout(10,1));
		p4.setBackground(Color.WHITE);
		p4.setBounds(x/2*3/5, y*3/14, x/2*1/5, y*7/14);
		test3 = new JLabel[bba.size()+1];
		test3[0]= new JLabel();
		test3[0].setText("가격");
		test3[0].setFont(f2);
		test3[0].setHorizontalAlignment(SwingConstants.CENTER);
		p4.add(test3[0]);
		int addpay;
		for(int i=0; i<bba.size(); i++) {
			inbasket ik = bba.get(i);
			test3[i+1]= new JLabel();
			for(int j=0; j<bin.size(); j++) {
			inbindae ib= bin.get(j);
			if(ik.getJP_num() == ib.getJp_num())
				{
				addpay = ib.getJp_price() * ik.getJO_count();
				test3[i+1].setText(addpay+"");
				}
			}
			test3[i+1].setFont(f2);
			test3[i+1].setHorizontalAlignment(SwingConstants.CENTER);
			p4.add(test3[i+1]);
		}
		add(p4);
		/*----------밑 결제금액-----------*/
		p5 = new JPanel(new BorderLayout());
		p5.setBackground(Color.WHITE);
		p5.setBounds(x/2*3/6+90, 870, x/2*2/5-80, y*1/8-50);
		toprice1 = new Basic_Button("결제 금액",Color.WHITE);
		toprice1.setHorizontalAlignment(SwingConstants.CENTER);
		toprice1.setForeground(Color.BLACK);
		toprice1.setFont(f3);
		toprice1.addActionListener(this);
		p5.add(toprice1,BorderLayout.WEST);
		toprice2 = new JLabel();
		for(int i=0; i<be.size(); i++) {
			inend id = be.get(i);
			toprice2.setText(id.getJA_pay()+" 원   ");
		}
		toprice2.setHorizontalAlignment(SwingConstants.CENTER);
		toprice2.setForeground(Color.BLACK);
		toprice2.setFont(f3);
		p5.add(toprice2,BorderLayout.EAST);
		add(p5);

	}

	void display() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setTitle("영수증");
		setLocation(0, 0);
		setSize(x/2, y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void delete(int del) {
		try {
			Class.forName(driver); // 드라이버연결
			 // DB연결
			con = DriverManager.getConnection(url, user, passwd);
			// statement 문장수행
			for(int i=0;i<bba.size();i++) {
			inbasket ib= bba.get(i);
			del= ib.getJA_num();
			pstmdel = con.prepareStatement("delete from b_basket where a_num=?");
			pstmdel.setInt(1,del);
			pstmdel.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void delete1(int del) {
		try {
			Class.forName(driver); // 드라이버연결
			 // DB연결
			con = DriverManager.getConnection(url, user, passwd);
			// statement 문장수행
		pstmdel = con.prepareStatement("delete from b_end where o_num=?");
		pstmdel.setInt(1,del);
		pstmdel.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
public void dbor() throws SQLException{	
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 드라이버연결
			String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul"; // DB연결
			con = DriverManager.getConnection(url, "root", "1234");
			// statement 문장수행
			stmt = con.createStatement();
			 rs = stmt.executeQuery("SELECT * FROM bindaepro"); //쿼리 날리기
			 while(rs.next()) {
				 bin.add(new inbindae(rs.getInt("p_num"), rs.getString("p_name"), rs.getInt("p_price"), rs.getInt("p_class"), rs.getString("p_memo"), rs.getInt("p_time")));	
			 }	 
		} catch (ClassNotFoundException e) {
			System.out.println("No Driver");
		} catch (SQLException e) {
			System.out.println("Miss Conect");
		}
}
public void dbba() throws SQLException{	
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver"); // 드라이버연결
		String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul"; // DB연결
		con = DriverManager.getConnection(url, "root", "1234");
		// statement 문장수행
		stmt = con.createStatement();
		 rs = stmt.executeQuery("SELECT * FROM b_basket");
		 while(rs.next()) {
		 bba.add(new inbasket(rs.getInt("a_num"),rs.getInt("b_pay"),rs.getInt("o_count"),rs.getInt("e_time"),rs.getInt("p_num"),rs.getInt("o_num")));
		 }
	} catch (ClassNotFoundException e) {
		System.out.println("No Driver");
	} catch (SQLException e) {
		System.out.println("Miss Conect");
	}
}
public void dbda() throws SQLException{	
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver"); // 드라이버연결
		String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul"; // DB연결
		con = DriverManager.getConnection(url, "root", "1234");
		// statement 문장수행
		stmt = con.createStatement();
		 rs = stmt.executeQuery("SELECT * FROM b_end");
		 while(rs.next()) {
		 be.add(new inend(rs.getInt("o_num"),rs.getInt("a_pay"),rs.getInt("a_time")));
		 }
	} catch (ClassNotFoundException e) {
		System.out.println("No Driver");
	} catch (SQLException e) {
		System.out.println("Miss Conect");
	}
}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == toprice1)
		{	
			delete(del);
			delete1(del);
			bin_main bm = new bin_main();
			bm.display();
		}
	}
}


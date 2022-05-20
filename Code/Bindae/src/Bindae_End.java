import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
/* ----------------------- */
public class Bindae_End extends JFrame{
	/* -----------번호,시간------------ */
	private ResultSet rs=null;
	private Statement stmt=null;
	private String driver ="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String passwd = "1234";
	private Connection con = null;
	private ArrayList<inend> be = new ArrayList<inend>();
	/* -----------레이아웃------------ */
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	
	String img1 = "images\\logo.png";
	ImageIcon Dcon1 = new ImageIcon(img1);  //ImageIcon에서 Image를 추출
	Image Dimg1 = Dcon1.getImage(); //추출된 Image의 크기를 조절하여 새로운 Image객체 생성
	Image Cimg1= Dimg1.getScaledInstance(150,150, Image.SCALE_SMOOTH ); //새로운 Image로 ImageIcon객체를 생성
	ImageIcon Ccon1 = new ImageIcon(Cimg1);
	
	JPanel order, b1, b2, b3, b4, b5;
	JLabel order1, t1, t2, t3, t4, t5;
	Font f = new Font("맑은 고딕",Font.BOLD,35);
	
	Bindae_End(){
		try {
			dbba();
		}
		catch(SQLException sq) {
			
		}
		/*-------------------로고-------------------*/
		order = new JPanel(new BorderLayout());
		order.setBackground(Color.WHITE);
		order.setBounds(0, 0, x/2, y*2/8);
		order1 = new JLabel(Ccon1);
		order.add(order1,BorderLayout.CENTER);
		add(order);
		/*-------------------중간 상자1-------------------*/
		b1 = new JPanel(new GridLayout(0, 1));
		b1.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		b1.setBackground(Color.WHITE);
		b1.setBounds(100, y*2/8+85, x/2-750, y*2/8-100);
		t1 = new JLabel("주문번호");
		t1.setHorizontalAlignment(SwingConstants.CENTER);
		t1.setForeground(Color.BLACK);
		t1.setFont(f);
		b1.add(t1);
		add(b1);
		
		b2 = new JPanel(new GridLayout(0, 1));
		b2.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		b2.setBackground(Color.WHITE);
		b2.setBounds(300, y*2/8+85, x/2-416, y*2/8-100);
		t2 = new JLabel();
		for(int i=0; i<be.size(); i++) {
			inend ie = be.get(i);
			t2.setText(ie.getJO_num()+"  ");
		}
		t2.setHorizontalAlignment(SwingConstants.RIGHT);
		t2.setForeground(Color.BLACK);
		t2.setFont(f);
		b2.add(t2);
		add(b2);
		/*-------------------중간 상자2-------------------*/
		b4 = new JPanel(new GridLayout(0, 1));
		b4.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		b4.setBackground(Color.WHITE);
		b4.setBounds(100, y*2/8+280, x/2-750, y*2/8-95);
		t4 = new JLabel("<html>&nbsp&nbsp&nbsp&nbsp&nbsp"
				+ "총<br>소요시간</html>");
		t4.setHorizontalAlignment(SwingConstants.CENTER);
		t4.setForeground(Color.BLACK);
		t4.setFont(f);
		b4.add(t4);
		add(b4);
		
		b5 = new JPanel(new GridLayout(0, 1));
		b5.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		b5.setBackground(Color.WHITE);
		b5.setBounds(300, y*2/8+280, x/2-416, y*2/8-95);
		t5 = new JLabel();
		int aa=0;
		for(int i=0; i<be.size(); i++) {
			inend ie = be.get(i);
			aa = ie.getJA_time();
			if(aa >= 180)
			{
				aa -=180;
				t5.setText("3시간  " + aa+" 분  ");
			}
			else if(aa >= 120)
			{
				aa -=120;
				t5.setText("2시간  " + aa+" 분  ");
			}
			else if(aa>=60)
			{	aa -=60;
				t5.setText("1시간  " + aa+" 분  ");
			}
			else {
			t5.setText(aa+"분  ");
			}
		}
		t5.setHorizontalAlignment(SwingConstants.RIGHT);
		t5.setForeground(Color.BLACK);
		t5.setFont(f);
		b5.add(t5);
		add(b5);
		/*-------------------처리중 글씨-------------------*/
		b3 = new JPanel();
		b3.setBackground(Color.WHITE);
		b3.setBounds(0, y*6/8, x/2, y*2/8);
		t3 = new JLabel("<html>번호 순으로 준비가 됩니다.<br>   시간에 맞추어서 와주세요</html>");
		t3.setHorizontalAlignment(SwingConstants.CENTER);
		t3.setForeground(Color.BLACK);
		t3.setFont(f);
		b3.add(t3);
		add(b3);
	}
	public void dbba() throws SQLException{	
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
	void display() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setTitle("거래승인 화면");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Thread t5 = new Thread(new Thread5());
		t5.start();
	}
	class Thread5 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
				bindae_receipt br = new bindae_receipt();
				br.display();
			}
			catch(InterruptedException e) {
				
			}
		}
		
	}
}


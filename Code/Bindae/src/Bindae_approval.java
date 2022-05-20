import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
/* -----------거래 승인------------ */
class Bindae_approval extends JFrame{
	/* -----------DB관련------------ */
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
	JPanel deal, text, text2,box;
	JLabel deala, text3,boxtxt1,boxtxt2;
	Font f = new Font("맑은 고딕",Font.BOLD,35);
	private Font f1 = new Font("맑은 고딕",Font.BOLD,50);
	Bindae_approval(){
		try {
			dbba();
		}
		catch(SQLException sq) {
			
		}
		/*-------------------로고-------------------*/
		deal = new JPanel(new BorderLayout());
		deal.setBackground(Color.WHITE);
		deal.setBounds(0, 0, x/2, y*2/8);
		deala = new JLabel(Ccon1);
		deal.add(deala,BorderLayout.CENTER);
		add(deal);
		/*-------------------중간 상자-------------------*/
		text = new JPanel(new GridLayout(0, 1));
		text.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		text.setBackground(Color.WHITE);
		text.setBounds(100, y*2/8+85, x/2-750, y*2/8-50);
		boxtxt1 = new JLabel("<html>거래<br>금액</html>");
		boxtxt1.setHorizontalAlignment(SwingConstants.CENTER);
		boxtxt1.setForeground(Color.BLACK);
		boxtxt1.setFont(f);
		text.add(boxtxt1);
		add(text);
		box = new JPanel(new GridLayout(0, 1));
		box.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		box.setBackground(Color.WHITE);
		box.setBounds(300, y*2/8+85, x/2-416, y*2/8-50);
		boxtxt2 = new JLabel();
		for(int i=0; i<be.size(); i++) {
			inend id = be.get(i);
			boxtxt2.setText(id.getJA_pay()+" 원   ");
		}
		boxtxt2.setHorizontalAlignment(SwingConstants.RIGHT);
		boxtxt2.setForeground(Color.BLACK);
		boxtxt2.setFont(f1);
		box.add(boxtxt2);
		add(box);
		/*-------------------처리중 글씨-------------------*/
		text2 = new JPanel();
		text2.setBackground(Color.WHITE);
		text2.setBounds(0, y*5/8, x/2, y*2/8);
		text3 = new JLabel("<html>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+ "처리중입니다<br>잠시만 기다려 주시기 바랍니다</html>");
		text3.setHorizontalAlignment(SwingConstants.CENTER);
		text3.setForeground(Color.BLACK);
		text3.setFont(f);
		text2.add(text3);
		add(text2);
	}
	void display() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setTitle("거래승인 화면");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread t3 = new Thread(new Thread3());
		t3.start();
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
	class Thread3 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
				Bindae_Completion bc = new Bindae_Completion();
				bc.display();
			}
			catch(InterruptedException e) {
				
			}
		}
		
	}
}



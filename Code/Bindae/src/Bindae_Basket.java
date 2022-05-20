import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
/* -----------장바구니------------ */
public class Bindae_Basket extends JFrame implements ActionListener,MouseListener{
	/* -----------DB관련------------ */
	private ResultSet rs=null;
	private Statement stmt=null;
	private PreparedStatement pstmtin2=null;
	private String driver ="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String passwd = "1234";
	private Connection con = null;
	private ArrayList<inbindae> bin = new ArrayList<inbindae>();
	private ArrayList<inbasket> bba = new ArrayList<inbasket>();
	private ArrayList<inend> ben = new ArrayList<inend>();
	private String str1 ="";
	private int str2=0;
	private int str3=0;
	private int imgnum=0;
	private int oonum=1;
	private int aapay=0;
	private int aatime=0;
	/* --------------전처리---------------- */
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	private Color col_hover,col_nomal;
	private JPanel mainpa,btnpa,b_listpa,b_listpa2,totalpa,scrollpa;
	private JLabel logola,textla;
	private Basic_Button ganbtn;
	private JButton nobtn, yesbtn;
	private JLabel totalla;
	private JScrollPane scroll;
	private String img1 = "images\\\\logo.png";
	private String[] imgstr = {"images\\0.png","images\\1.png", "images\\2.png","images\\3.png",
			"images\\4.png","images\\5.png","images\\6.png","images\\7.png","images\\8.png",};
	private ImageIcon Dcon1 = new ImageIcon(img1);  //ImageIcon에서 Image를 추출
	private Image Dimg1 = Dcon1.getImage(); //추출된 Image의 크기를 조절하여 새로운 Image객체 생성
	private Image Cimg1= Dimg1.getScaledInstance(150, 150, Image.SCALE_SMOOTH ); //새로운 Image로 ImageIcon객체를 생성
	private ImageIcon Ccon1 = new ImageIcon(Cimg1);
	private Font f1 = new Font("맑은 고딕",Font.BOLD,50);
	private Font f2  = new Font("맑은 고딕",Font.BOLD,35);
	private Font f3 = new Font("맑은 고딕",Font.BOLD,25);
	private Font f4 = new Font("맑은 고딕",Font.BOLD,15);
	private ImageIcon bascon[];
	private JButton l1[];
	boolean gan = false;
	Bindae_Basket(){
		try {
			dbor();
			dbba();
		}
		catch(SQLException sq) {
			
		}
		
		col_hover = new Color(255,187,0);
		col_nomal = new Color(255,255,255);
		/* --------------상단---------------- */
		mainpa = new JPanel(new BorderLayout());
		mainpa.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		mainpa.setBackground(col_nomal);
		logola = new JLabel(Ccon1);
		textla = new JLabel("주문을 확인해주세요");
		textla.setFont(f2);
		textla.setVerticalAlignment(SwingConstants.BOTTOM);
		textla.setHorizontalAlignment(SwingConstants.CENTER);
		mainpa.add(logola,BorderLayout.CENTER);
		mainpa.add(textla,BorderLayout.SOUTH);
		mainpa.setBounds(0,0,x/2-16,y*2/8);
		add(mainpa);
		/* --------------장바구니 리스트---------------- */
		b_listpa = new JPanel(new BorderLayout());
		b_listpa.setBackground(col_nomal);
		b_listpa.setBorder(new MatteBorder(1,0,1,0,col_hover));
		b_listpa.setBounds(0,y*1/4,x/2-16,y/2);
		l1 = new JButton[bba.size()];
		b_listpa2 = new JPanel(new GridLayout(2,4));
		b_listpa2.setBackground(col_nomal);
		bascon = new ImageIcon[9];
		for(int j=0; j<9; j++) {
			bascon[j] = new ImageIcon(imgstr[j]);	
		}
		for(int i=0; i<bba.size(); i++) {
			inbasket ik100 = bba.get(i);
			for(int j=0; j<bin.size(); j++)
			{	
				inbindae ib100 = bin.get(j);
				if(ik100.getJP_num() == ib100.getJp_num()) {
					str1 = ib100.getJp_name();
					str2 = ib100.getJp_price()*ik100.getJO_count();
					str3 = ik100.getJO_count();
					imgnum = ib100.getJp_num()-1;
				}
				
			}
		/*l1[i] = new JButton("<HTML>&nbsp&nbsp&nbsp&nbsp     상품명 : "+
		str1+"<br>&nbsp&nbsp&nbsp&nbsp      금액 : "+
		str2+"<br>&nbsp&nbsp&nbsp&nbsp     개수 : "+str3+"</HTML>",bascon[imgnum]);*/
			l1[i] = new JButton("<HTML>&nbsp&nbsp"+
					str1+"<br>&nbsp "+
					str2+"<br>&nbsp "+str3+"</HTML>",bascon[imgnum]);
		l1[i].setPreferredSize(new Dimension(475,120));
		l1[i].setHorizontalTextPosition(JButton.RIGHT);
		l1[i].setBorderPainted(false);
		l1[i].setBackground(col_nomal);
		l1[i].setFocusPainted(false);// 포커스 테두리 x
		l1[i].addActionListener(this);
		l1[i].setFont(f3);
		b_listpa2.add(l1[i]);
		}
		
		scroll = new JScrollPane(scrollpa,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		b_listpa.add(scroll,BorderLayout.EAST);
		b_listpa.add(b_listpa2,BorderLayout.CENTER);
		add(b_listpa);
		
		/* ---------------합계--------------- */
		totalpa = new JPanel(new BorderLayout());
		totalpa.setBackground(col_nomal);
		totalpa.setBorder(BorderFactory.createEmptyBorder(0,0,20,20));
		totalpa.setBounds(0, y*6/8, x/2-16, y*2/8*2/3);
		for(int i=0; i<bba.size(); i++) {
			inbasket it = bba.get(i);
			aapay += it.getJB_pay();
		}
		for(int i=0; i<bba.size(); i++) {
			inbasket it = bba.get(i);
			aatime += it.getJE_time();
		}
		totalla = new JLabel("합계  :  "+aapay);
		totalla.setFont(f2);
		totalpa.add(totalla,BorderLayout.EAST);
		ganbtn = new Basic_Button("간장추가",Color.WHITE);
		ganbtn.setFont(f2);
		totalla.setVerticalAlignment(SwingConstants.BOTTOM);
		totalla.setHorizontalAlignment(SwingConstants.RIGHT);
		ganbtn.setBorderPainted(false);
		ganbtn.setFocusPainted(false);
		ganbtn.addActionListener(this);
		ganbtn.addMouseListener(this);
		ganbtn.setBackground(col_nomal);
		ganbtn.setVerticalAlignment(SwingConstants.TOP);
		ganbtn.setHorizontalAlignment(SwingConstants.LEFT);
		totalpa.add(ganbtn,BorderLayout.WEST);
		add(totalpa);
		/* --------------버튼---------------- */
		btnpa = new JPanel(new GridLayout(1,2));
		btnpa.setBounds(0, y*6/8+y*2/8*2/3, x/2-16, y*2/8*1/5);
		btnpa.setBackground(col_nomal);
		nobtn = new JButton("이전");
		nobtn.setBorderPainted(false);
		nobtn.setFocusPainted(false);
		nobtn.setFont(f3);
		nobtn.setForeground(col_nomal);
		nobtn.setBackground(new Color(247,109,123));
		nobtn.addActionListener(this);
		nobtn.addMouseListener(this);
		btnpa.add(nobtn);
		yesbtn = new JButton("결제");
		yesbtn.setBorderPainted(false);
		yesbtn.setFocusPainted(false);
		yesbtn.setFont(f3);
		yesbtn.setForeground(col_nomal);
		yesbtn.setBackground(new Color(150,198,74));
		yesbtn.addActionListener(this);
		yesbtn.addMouseListener(this);
		btnpa.add(yesbtn);
		add(btnpa);
	}
	void display() {
		getContentPane().setBackground(col_nomal);
		//setUndecorated(true);
		setLayout(null);
		setTitle("중앙빈대떡 장바구니");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
}
	public void insert2(int onum,int apay,int atime) {
		try {
			Class.forName(driver); // 드라이버연결
			 // DB연결
			con = DriverManager.getConnection(url, user, passwd);
			// statement 문장수행
			stmt = con.createStatement();
		pstmtin2 = con.prepareStatement("insert into b_end(o_num,a_pay,a_time) values(?,?,?)");
		pstmtin2.setInt(1, onum);
		pstmtin2.setInt(2, apay);
		pstmtin2.setInt(3, atime);
		pstmtin2.executeUpdate();
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== nobtn)
		{
			Bindae_Select1 bs = new Bindae_Select1();
			bs.display();
		}
		if(e.getSource() == yesbtn) {
			insert2(oonum, aapay, aatime);
			Bindae_paysel bp = new Bindae_paysel();
			bp.display();
		}
		if(e.getSource() == ganbtn) {
			
			if(gan ==false) {
				ganbtn.setForeground(col_hover);
				gan =true;
			}
			else if(gan==true)
			{
				ganbtn.setForeground(new Color(0,0,0));
				gan =false;
			}		
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == nobtn) {
			nobtn.setBackground(new Color(225,129,143));
		}
		if(e.getSource() == yesbtn) {
			yesbtn.setBackground(new Color(170,218,94));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == nobtn) {
			nobtn.setBackground(new Color(247,109,123));
		}
		if(e.getSource() == yesbtn) {
			yesbtn.setBackground(new Color(150,198,74));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

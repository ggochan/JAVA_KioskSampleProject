import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
/* -----------메뉴선택------------ */
public class Bindae_order extends JFrame implements ActionListener,MouseListener{
	/* -----------DB관련------------ */
	private ResultSet rs=null;
	private Statement stmt=null;
	private PreparedStatement pstmtin=null;
	private PreparedStatement pstmdel=null;
	private String driver ="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String passwd = "1234";
	private Connection con = null;
	private ArrayList<inbindae> bin = new ArrayList<inbindae>();
	private ArrayList<inbasket> bba = new ArrayList<inbasket>();
	private int countcnt=1;
	private int paycnt=0;
	private int timecnt=0;
	private int del=0;
	private String str11 ="";
	private int str12=0;
	private int str13=0;
	private int imgnum=0;
	/* --------------레이아웃---------------- */
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	private String[] imgstr = {"images\\0.png","images\\1.png", "images\\2.png","images\\3.png",
			"images\\4.png","images\\5.png","images\\6.png","images\\7.png","images\\8.png",};
	private JPanel allpa,listpa,order_scrollpa,countpa,pmpa,btnpa;
	private JButton nobtn, yesbtn,plusbtn,minusbtn;
	private JButton[] t1;
	private JLabel orderla,numla;
	private JScrollPane order_scroll;
	private Font f1 = new Font("맑은 고딕",Font.BOLD,50);
	private Font f2  = new Font("맑은 고딕",Font.BOLD,35);
	private Font f3 = new Font("맑은 고딕",Font.BOLD,25);
	private Font f4 = new Font("맑은 고딕",Font.BOLD,15);
	private ImageIcon[] ordercon;
	private Color col_hover,col_nomal;
	
	Bindae_order(){
		try {
			dbor();
			dbba();
		}
		catch(SQLException sq) {
			
		}
	col_hover = new Color(255,187,0);
	col_nomal = new Color(255,255,255);
	/*-------------리스트------------------*/
	allpa = new JPanel(new BorderLayout());
	allpa.setBounds(0, 0, x/2-16, y*6/8);
	allpa.setBorder(new MatteBorder(0,0,1,0,col_hover));
	listpa = new JPanel();
	listpa.setBackground(col_nomal);
	t1 = new JButton[bba.size()];
	ordercon = new ImageIcon[9];
	for(int j=0; j<9; j++) {
		ordercon[j] = new ImageIcon(imgstr[j]);	
	}
	for(int i=0; i<bba.size(); i++) {
		inbasket ik100 = bba.get(i);
		for(int j=0; j<bin.size(); j++)
		{	
			inbindae ib100 = bin.get(j);
			if(ik100.getJP_num() == ib100.getJp_num()) {
				str11 = ib100.getJp_name();
				str12 = ib100.getJp_price()*ik100.getJO_count();;
				str13 = ik100.getJO_count();
				imgnum = ib100.getJp_num()-1;
			}
		}
		
	t1[i] = new JButton("<HTML>&nbsp&nbsp&nbsp&nbsp     상품명 : "+
	str11+"<br>&nbsp&nbsp&nbsp&nbsp      금액 : "+
	str12+"<br>&nbsp&nbsp&nbsp&nbsp     개수 : "+str13+"</HTML>",ordercon[imgnum]);
	t1[i].setPreferredSize(new Dimension(950,150));
	t1[i].setHorizontalTextPosition(JButton.RIGHT);
	//t1[i].setBorderPainted(false);
	t1[i].setBackground(col_nomal);
	t1[i].setFocusPainted(false);// 포커스 테두리 x
	t1[i].addActionListener(this);
	t1[i].setFont(f3);
	listpa.add(t1[i]);
	}
	order_scroll = new JScrollPane(order_scrollpa,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	allpa.add(order_scroll,BorderLayout.EAST);
	allpa.add(listpa,BorderLayout.CENTER);
	add(allpa);
	/*--------------수량---------------------*/
	countpa = new JPanel(new BorderLayout());
	countpa.setBackground(col_nomal);
	countpa.setBounds(0, y*6/8, x/2/4, y*2/8*2/3);
	countpa.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	orderla = new JLabel("수량");
	orderla.setFont(f3);
	orderla.setVerticalAlignment(SwingConstants.TOP);
	orderla.setHorizontalAlignment(SwingConstants.LEFT);
	countpa.add(orderla,BorderLayout.WEST);
	add(countpa);
	/*----------------+,- 버튼-------------------------*/
	pmpa = new JPanel(new GridLayout(1,3,10,10));
	pmpa.setBackground(col_nomal);
	pmpa.setPreferredSize(new Dimension(100,100));
	pmpa.setBounds(x/2/4, y*6/8, x/2/2, y*2/8*2/3);
	//pmpa.setBorder(new LineBorder(Color.DARK_GRAY,1,true));
	pmpa.setBorder(BorderFactory.createEmptyBorder(40,100,40,100));
	minusbtn = new JButton("-");
	minusbtn.setFont(f1);
	minusbtn.setBackground(col_nomal);
	minusbtn.setBorderPainted(false);
	minusbtn.setFocusPainted(false);
	minusbtn.addActionListener(this);
	minusbtn.addMouseListener(this);
	pmpa.add(minusbtn);
	EtchedBorder border;
	border = new EtchedBorder(EtchedBorder.RAISED);
	numla = new JLabel();
	numla.setText(""+1);
	numla.setBorder(border);
	numla.setFont(f1);
	numla.setVerticalAlignment(SwingConstants.CENTER);
	numla.setHorizontalAlignment(SwingConstants.CENTER);
	pmpa.add(numla);
	plusbtn = new JButton("+");
	plusbtn.setFont(f1);
	plusbtn.setBackground(col_nomal);
	plusbtn.setBorderPainted(false);
	plusbtn.setFocusPainted(false);
	plusbtn.addActionListener(this);
	plusbtn.addMouseListener(this);
	pmpa.add(plusbtn);

	add(pmpa);
	/*------------------버튼----------------------*/
	btnpa = new JPanel(new GridLayout(1,2));
	btnpa.setBounds(0, y*6/8+y*2/8*2/3, x/2-16, y*2/8*1/5);
	btnpa.setBackground(col_nomal);
	nobtn = new JButton("취소");
	nobtn.setBorderPainted(false);
	nobtn.setFocusPainted(false);
	nobtn.setFont(f3);
	nobtn.setForeground(col_nomal);
	nobtn.setBackground(new Color(247,109,123));
	nobtn.addActionListener(this);
	nobtn.addMouseListener(this);
	btnpa.add(nobtn);
	yesbtn = new JButton("추가");
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
		setTitle("중앙빈대떡 주문");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
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
			}
		pstmdel = con.prepareStatement("delete from b_basket where a_num=?");
		pstmdel.setInt(1,del);
		pstmdel.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
public void update(int bpay,int ocount,int etime) {
	try {
		Class.forName(driver); // 드라이버연결
		 // DB연결
		con = DriverManager.getConnection(url, user, passwd);
		// statement 문장수행
		stmt = con.createStatement();
		int tet2=0;
		for(int i=bba.size(); i<bba.size()+1;i++) {
			inbasket ib = bba.get(i-1);
			tet2=ib.getJA_num();
			break;
		}
	pstmtin = con.prepareStatement("update b_basket set b_pay=?,o_count=?,e_time=? where a_num=?");
	pstmtin.setInt(1, bpay);
	pstmtin.setInt(2, ocount);
	pstmtin.setInt(3, etime);
	pstmtin.setInt(4, tet2);
	pstmtin.executeUpdate();
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
			delete(del);
			dispose();
		}
		if(e.getSource() == yesbtn) {
			
			
			for(int i=0; i<bba.size(); i++) {
				inbasket ik = bba.get(i);
			for(int j=0; j<bin.size();j++) {
				inbindae ib = bin.get(j);
				if(ik.getJP_num()==ib.getJp_num() && ik.getJP_num()!=0) {
					paycnt = ib.getJp_price();
					timecnt = ib.getJp_time();
					break;
					}
				}
			}
			paycnt *= countcnt;
			timecnt *= countcnt;
			update(paycnt,countcnt,timecnt);
			dispose();
		}
		if(e.getSource() == plusbtn) {
			countcnt += 1;
			numla.setText(""+countcnt);
		}
		if(e.getSource() == minusbtn) {
			countcnt -= 1;
			numla.setText(""+countcnt);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== plusbtn) {
			plusbtn.setForeground(col_hover);
		}
		if(e.getSource()== minusbtn) {
			minusbtn.setForeground(col_hover);
		}
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
		if(e.getSource()== plusbtn) {
			plusbtn.setForeground(Color.BLACK);
		}if(e.getSource()== minusbtn) {
			minusbtn.setForeground(Color.BLACK);
		}
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.sql.*;
import java.util.ArrayList;

import org.w3c.dom.css.RGBColor;
/* -----------메뉴------------ */
public class Bindae_Select1 extends JFrame implements ActionListener, MouseListener{
	/* -----------DB관련------------ */
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement pstmtin;
	private String driver ="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/bindae?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String passwd = "1234";
	private Connection con = null;
	private ArrayList<inbindae> bin = new ArrayList<inbindae>();
	private ArrayList<inbasket> bba = new ArrayList<inbasket>();
	int selcnt=0;
	int total=0;
	int ocnt=1;
	int acnt=0;
	int pcnt=0;
	/* -----------레이아웃------------ */
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	private JPanel titlepa,menupa,selpa,ordermemopa1,ordermemopa2,ordermemopa3,buttonpa,selpa2,scrollpa;
	private JLabel listmemo[];
	private JButton nobtn, yesbtn;
	private Basic_Button backbtn, bestbtn, setbtn, onebtn, thebtn;
	private JButton[] sel;
	private JLabel[] test = new JLabel[6];
	private JLabel logola,menula,memola,totalmemo,totalmemo2,fullmemo;
	private JScrollPane scroll;
	private Color col_hover,col_nomal;
	private Font f1 = new Font("맑은 고딕",Font.BOLD,50);
	private Font f2  = new Font("맑은 고딕",Font.BOLD,35);
	private Font f3 = new Font("맑은 고딕",Font.BOLD,25);
	private Font f4 = new Font("맑은 고딕",Font.BOLD,15);
	private Image[] dimg;
	private Image[] cimg;
	private ImageIcon[] dicon;
	private ImageIcon[] cicon;
	String[] imgstr = {"images\\0.png","images\\1.png", "images\\2.png","images\\3.png",
			"images\\4.png","images\\5.png","images\\6.png","images\\7.png","images\\8.png",};
	String img2 = "images\\logo.png";
	ImageIcon Dcon2 = new ImageIcon(img2);  //ImageIcon에서 Image를 추출
	Image Dimg2 = Dcon2.getImage(); //추출된 Image의 크기를 조절하여 새로운 Image객체 생성
	Image Cimg2= Dimg2.getScaledInstance(100, 100, Image.SCALE_SMOOTH ); //새로운 Image로 ImageIcon객체를 생성
	ImageIcon Ccon2 = new ImageIcon(Cimg2);
	Bindae_Select1(){
		try {
			dbse();
			dbba();
		}
		catch(SQLException sq) {
			
		}
	/* -----------상단------------ */
	col_hover = new Color(255,187,0);
	col_nomal = new Color(255,255,255);
	titlepa = new JPanel(new BorderLayout());
	titlepa.setBounds(0, 0, x/2-16, y*1/8);
	titlepa.setBackground(col_nomal);
	titlepa.setBorder(BorderFactory.createEmptyBorder(5,75,0,10));
	add(titlepa);
	
	logola = new JLabel(Ccon2);
	titlepa.add(logola,BorderLayout.WEST);
	menula = new JLabel("     메뉴");
	menula.setFont(f1);
	titlepa.add(menula,BorderLayout.CENTER);
	backbtn = new Basic_Button("이전으로",Color.WHITE);
	backbtn.setVerticalAlignment(SwingConstants.TOP);
	backbtn.setHorizontalAlignment(SwingConstants.RIGHT);
	backbtn.setFont(f3);
	backbtn.setBackground(col_nomal);
	backbtn.setBorderPainted(false);
	backbtn.setFocusPainted(false);// 포커스 테두리 x
	backbtn.setPreferredSize(new Dimension(x/2*1/5,y*1/20));
	backbtn.addActionListener(this);
	backbtn.addMouseListener(this);
	titlepa.add(backbtn,BorderLayout.EAST);
	
	/* -----------메뉴바------------ */
	menupa = new JPanel(new GridLayout(4,1));
	menupa.setBorder(new MatteBorder(1,0,1,1,col_hover));
	menupa.setFont(f2);	
	menupa.setBackground(col_nomal);
	menupa.setBounds(0, y*1/8, x/2*1/4, y*5/8);
	add(menupa);
	
	bestbtn = new Basic_Button("추천메뉴",Color.WHITE);
	bestbtn.setBorderPainted(false);
	bestbtn.setFocusPainted(false);
	bestbtn.addActionListener(this);
	bestbtn.addMouseListener(this);
	bestbtn.setFont(f2);
	bestbtn.setBackground(col_nomal);
	menupa.add(bestbtn);
	setbtn = new Basic_Button("세트메뉴",Color.WHITE);
	setbtn.setBorderPainted(false);
	setbtn.setFocusPainted(false);
	setbtn.addActionListener(this);
	setbtn.addMouseListener(this);
	setbtn.setFont(f2);
	setbtn.setBackground(col_nomal);
	menupa.add(setbtn);
	onebtn = new Basic_Button("단품",Color.WHITE);
	onebtn.setBorderPainted(false);
	onebtn.setFocusPainted(false);
	onebtn.addActionListener(this);
	onebtn.addMouseListener(this);
	onebtn.setFont(f2);
	onebtn.setBackground(col_nomal);
	menupa.add(onebtn);
	thebtn = new Basic_Button("기타",Color.WHITE);
	thebtn.setBorderPainted(false);
	thebtn.setFocusPainted(false);
	thebtn.addActionListener(this);
	thebtn.addMouseListener(this);
	thebtn.setFont(f2);
	thebtn.setBackground(col_nomal);
	menupa.add(thebtn);
	
	/* -----------메뉴선택------------*/
	selpa = new JPanel(new BorderLayout());
	selpa2 = new JPanel(new GridLayout(3,3,20,20));
	selpa2.setBackground(col_nomal);
	selpa2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	scroll = new JScrollPane(scrollpa,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	selpa.setBorder(new MatteBorder(1,0,1,0,col_hover));
	selpa.setBounds(x/2*1/4, y*1/8, x/2*3/4-16, y*5/8);
	selpa.add(scroll,BorderLayout.EAST);

	dimg = new Image[9];
	cimg = new Image[9];
	dicon = new ImageIcon[9];
	cicon = new ImageIcon[9];
	for(int i=0; i<9; i++) {
		dicon[i] = new ImageIcon(imgstr[i]);
		dimg[i] = dicon[i].getImage();
		cimg[i] = dimg[i].getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		cicon[i] = new ImageIcon(cimg[i]);
	}
	sel = new JButton[9];
	for(int i=0; i<9; i++) {
		sel[i] = new JButton(cicon[i]);
		sel[i].setBackground(col_nomal);
		sel[i].setBorderPainted(false);
		sel[i].setFocusPainted(false);
		sel[i].addActionListener(this);
		selpa2.add(sel[i]);
	}
	selpa.add(selpa2,BorderLayout.CENTER);
	add(selpa);
	
	/* -----------주문내역------------*/
	ordermemopa1 = new JPanel(new BorderLayout());
	ordermemopa1.setBackground(col_nomal);
	ordermemopa1.setBorder(new MatteBorder(1,0,1,0,col_hover));
	ordermemopa1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	ordermemopa1.setBounds(0, y*6/8, x/2*1/6-30, y*2/8*2/3);
	memola = new JLabel("주문 상품");
	memola.setFont(f3);
	memola.setVerticalAlignment(SwingConstants.TOP);
	memola.setHorizontalAlignment(SwingConstants.LEFT);
	ordermemopa1.add(memola,BorderLayout.WEST);
	add(ordermemopa1);
	ordermemopa2 = new JPanel(new GridLayout(2,4));
	ordermemopa2.setBounds(x/2*1/6-30,y*6/8,x/2*4/6,y*2/8*2/3);
	ordermemopa2.setBorder(BorderFactory.createEmptyBorder(40,10,0,0));
	ordermemopa2.setBackground(col_nomal);
	//test1();
	
	for(int i=0 ; i<6; i++) {
		test[i] = new JLabel();
		test[i].setFont(f3);
		test[i].setText("");
		ordermemopa2.add(test[i]);
	}
	add(ordermemopa2);
	ordermemopa3 = new JPanel(new BorderLayout());
	ordermemopa3.setBounds(x/2*1/6-30+x/2*4/6,y*6/8,x/2*1/6+12,y*2/8*2/3);
	ordermemopa3.setBackground(col_nomal);	
	/*totalmemo = new JLabel("총액  :  ");
	totalmemo.setFont(f3);
	ordermemopa3.setBorder(BorderFactory.createEmptyBorder(10,0,10,15));
	ordermemopa3.add(totalmemo,BorderLayout.WEST);
	totalmemo.setVerticalAlignment(SwingConstants.BOTTOM);
	totalmemo.setHorizontalAlignment(SwingConstants.LEFT);
	totalmemo2 = new JLabel();
	totalmemo2.setText("");
	totalmemo2.setFont(f3);
	totalmemo2.setVerticalAlignment(SwingConstants.BOTTOM);
	totalmemo2.setHorizontalAlignment(SwingConstants.RIGHT);*/
	fullmemo =new JLabel();
	fullmemo.setFont(f3);
	fullmemo.setVerticalAlignment(SwingConstants.TOP);
	fullmemo.setHorizontalAlignment(SwingConstants.CENTER);
	fullmemo.setForeground(Color.RED);
	ordermemopa3.add(fullmemo,BorderLayout.NORTH);
	//ordermemopa3.add(totalmemo2,BorderLayout.EAST);
	add(ordermemopa3);
	
	/* -----------취소,결제------------*/
	
	buttonpa = new JPanel(new GridLayout(1,2));
	buttonpa.setBackground(col_nomal);
	buttonpa.setBounds(0, y*6/8+y*2/8*2/3, x/2, y*2/8*1/5);
	add(buttonpa);
	nobtn = new JButton("취소");
	nobtn.setBorderPainted(false);
	nobtn.setFocusPainted(false);
	nobtn.setFont(f3);
	nobtn.setForeground(col_nomal);
	nobtn.setBackground(new Color(247,109,123));
	nobtn.addActionListener(this);
	nobtn.addMouseListener(this);
	buttonpa.add(nobtn);
	yesbtn = new JButton("완료");
	yesbtn.setBorderPainted(false);
	yesbtn.setFocusPainted(false);
	yesbtn.setFont(f3);
	yesbtn.setForeground(col_nomal);
	yesbtn.setBackground(new Color(150,198,74));
	yesbtn.addActionListener(this);
	yesbtn.addMouseListener(this);
	buttonpa.add(yesbtn);
	
	}
	
	void display() {
					getContentPane().setBackground(col_nomal);
					setLayout(null);
					//setUndecorated(true);
					setTitle("중앙빈대떡 메뉴선택");
					setLocation(0,0);
					setSize(x/2,y);
					setVisible(true);
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	}
	public void insert(int anum,int onum,int pnum) {
		try {
			Class.forName(driver); // 드라이버연결
			 // DB연결
			con = DriverManager.getConnection(url, user, passwd);
			// statement 문장수행
			stmt = con.createStatement();
		pstmtin = con.prepareStatement("insert into b_basket(a_num,p_num,o_num) values(?,?,?)");
		pstmtin.setInt(1, anum);
		pstmtin.setInt(2, pnum);
		pstmtin.setInt(3, onum);
		pstmtin.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void dbse() throws SQLException{	
		
		try {
			Class.forName(driver); // 드라이버연결
			 // DB연결
			con = DriverManager.getConnection(url, user, passwd);
			// statement 문장수행
			stmt = con.createStatement();
			 rs = stmt.executeQuery("SELECT * FROM bindaepro"); //쿼리 날리기
			 while(rs.next()) {
				 bin.add(new inbindae(rs.getInt("p_num"), rs.getString("p_name"), rs.getInt("p_price"), rs.getInt("p_class"), rs.getString("p_memo"), rs.getInt("p_time")));	
			 }
			
				// strtest = Integer.toString(total);
				
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
		if(e.getSource() == backbtn)
		{
			Bindae_Select1 bs1 = new Bindae_Select1();
			bs1.display();
		}
		if(e.getSource() == nobtn)
		{
			dispose();
		}
		
		if(e.getSource() == thebtn)
		{
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == yesbtn)
		{	
			Bindae_Basket bb = new Bindae_Basket();
			bb.display();
		}
		if(e.getSource() == sel[0])
		{	
			inbindae i0 = bin.get(0);
		for(int i=selcnt; i<selcnt+1; i++) {
			
			test[selcnt].setText(i0.getJp_name()+"");
			++selcnt;
			break;
			
			}
		pcnt = i0.getJp_num();
		total += i0.getJp_price();
		acnt +=1;
		
		if(selcnt>5) {
			fullmemo.setText("Basket is full");
		}
		insert(acnt,ocnt,pcnt);
		Bindae_order bo = new Bindae_order();
		bo.display();
		}
		if(e.getSource() == sel[1])
		{	
			inbindae i1 = bin.get(1);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i1.getJp_name()+"");
				++selcnt;
				break;
				}
			pcnt = i1.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[2])
		{
			inbindae i2 = bin.get(2);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i2.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i2.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[3])
		{
			inbindae i3 = bin.get(3);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i3.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i3.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[4])
		{
			inbindae i4 = bin.get(4);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i4.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i4.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[5])
		{
			inbindae i5= bin.get(5);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i5.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i5.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[6])
		{
			inbindae i6 = bin.get(6);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i6.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i6.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		if(e.getSource() == sel[7])
		{
			inbindae i7 = bin.get(7);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i7.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i7.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
		
		if(e.getSource() == sel[8])
		{
			inbindae i8 = bin.get(8);
			for(int i=selcnt; i<selcnt+1; i++) {
				test[selcnt].setText(i8.getJp_name()+"");
				++selcnt;
				break;
			}
			pcnt = i8.getJp_num();
			acnt +=1;
			if(selcnt>5) {
				fullmemo.setText("Basket is full");
			}
			insert(acnt,ocnt,pcnt);
			Bindae_order bo = new Bindae_order();
			bo.display();
		}
	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== backbtn) {
			backbtn.setForeground(col_hover);
		}
		if(e.getSource() == bestbtn) {
			bestbtn.setForeground(col_hover);
		}
		if(e.getSource() == setbtn) {
			setbtn.setForeground(col_hover);
		}
		if(e.getSource() == onebtn) {
			onebtn.setForeground(col_hover);
		}
		if(e.getSource() == thebtn) {
			thebtn.setForeground(col_hover);
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
		if(e.getSource()== backbtn) {
			backbtn.setForeground(Color.BLACK);
		}
		if(e.getSource() == bestbtn) {
			bestbtn.setForeground(Color.BLACK);
		}
		if(e.getSource() == setbtn) {
			setbtn.setForeground(Color.BLACK);
		}
		if(e.getSource() == onebtn) {
			onebtn.setForeground(Color.BLACK);
		}
		if(e.getSource() == thebtn) {
			thebtn.setForeground(Color.BLACK);
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

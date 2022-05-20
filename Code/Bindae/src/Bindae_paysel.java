import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/* -----------결제선택------------ */
public class Bindae_paysel extends JFrame implements ActionListener{
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	ImageIcon[] Dcon;
	ImageIcon[] Ccon;
	Image[] Dimg;
	Image[] Cimg;
	
	String img1 = "images\\logo.png";
	ImageIcon Dcon1 = new ImageIcon(img1);  //ImageIcon에서 Image를 추출
	Image Dimg1 = Dcon1.getImage(); //추출된 Image의 크기를 조절하여 새로운 Image객체 생성
	Image Cimg1= Dimg1.getScaledInstance(150, 150, Image.SCALE_SMOOTH ); //새로운 Image로 ImageIcon객체를 생성
	ImageIcon Ccon1 = new ImageIcon(Cimg1);
	
	String[] img= {"images\\card.jpg","images\\money.jpg"};
	private JPanel paypa,imgpa;
	private JLabel logola,textla;
	private Font f = new Font("맑은 고딕",Font.BOLD,35);
	private JButton cardbtn,moneybtn;
	public Bindae_paysel() {
		/*-----------------로고,텍스트--------------------*/
		paypa = new JPanel(new BorderLayout());
		paypa.setBounds(0, 0, x/2, y*2/8);
		paypa.setBackground(Color.WHITE);
		logola = new JLabel(Ccon1);
		paypa.add(logola,BorderLayout.CENTER);
		textla = new JLabel("원하는 결제방법을 선택하세요");
		textla.setHorizontalAlignment(SwingConstants.CENTER);
		textla.setForeground(Color.BLACK);
		textla.setFont(f);
		paypa.add(textla,BorderLayout.SOUTH);
		add(paypa);
		/*-----------------이미지--------------------*/
		imgpa = new JPanel(new GridLayout(1,2));
		imgpa.setBounds(100, y*2/8+50, x/2-216, y*5/8-50);// x=Location(좌우좌표), y=Location(상하좌표),width=좌우크기,Height=상하크기
		imgpa.setBackground(Color.WHITE);
		Ccon = new ImageIcon[2];
		Dcon = new ImageIcon[2];
		Cimg = new Image[2];
		Dimg = new Image[2];
		for(int i=0; i<2; i++) {
			Dcon[i] = new ImageIcon(img[i]);  //ImageIcon에서 Image를 추출
			Dimg[i] = Dcon[i].getImage(); //추출된 Image의 크기를 조절하여 새로운 Image객체 생성
			Cimg[i]= Dimg[i].getScaledInstance(x*1/5, y*5/8, Image.SCALE_SMOOTH ); //새로운 Image로 ImageIcon객체를 생성
			Ccon[i] = new ImageIcon(Cimg[i]);
			}
		cardbtn = new JButton(Ccon[0]);
		cardbtn.addActionListener(this);
		imgpa.add(cardbtn);
		moneybtn = new JButton(Ccon[1]);
		moneybtn.addActionListener(this);
		imgpa.add(moneybtn);
		add(imgpa);
	}
	
	void display() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setTitle("중앙빈대떡 결제선택");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == cardbtn /*|| e.getSource() == moneybtn*/) {
			System.exit(0);
		}
		if(e.getSource() == moneybtn) {
			Bindae_approval ba =  new Bindae_approval();
			ba.display();
		}
	}
}
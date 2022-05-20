import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/* -----------�����Ϸ�------------ */
class Bindae_Completion extends JFrame implements ActionListener{
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height;
	
	String img1 = "images\\logo.png";
	ImageIcon Dcon1 = new ImageIcon(img1);  //ImageIcon���� Image�� ����
	Image Dimg1 = Dcon1.getImage(); //����� Image�� ũ�⸦ �����Ͽ� ���ο� Image��ü ����
	Image Cimg1= Dimg1.getScaledInstance(150,150, Image.SCALE_SMOOTH ); //���ο� Image�� ImageIcon��ü�� ����
	ImageIcon Ccon1 = new ImageIcon(Cimg1);
	
	String img2= "images\\sincard.jpg";
	ImageIcon Dcon2 = new ImageIcon(img2);  //ImageIcon���� Image�� ����
	Image Dimg2 = Dcon2.getImage(); //����� Image�� ũ�⸦ �����Ͽ� ���ο� Image��ü ����
	Image Cimg2= Dimg2.getScaledInstance(780,620, Image.SCALE_SMOOTH ); //���ο� Image�� ImageIcon��ü�� ����
	ImageIcon Ccon2 = new ImageIcon(Cimg2);
	
	JPanel pay,payimg;
	JLabel textlb, logolb;
	Font f = new Font("���� ���",Font.BOLD,35);
	JButton paybtn;
	
	Bindae_Completion(){
		pay = new JPanel(new BorderLayout());
		pay.setBackground(Color.WHITE);
		pay.setBounds(0, 0, x/2, y*2/8);
		logolb = new JLabel(Ccon1);
		pay.add(logolb,BorderLayout.CENTER);
		textlb = new JLabel("�����Ϸ�");
		textlb.setHorizontalAlignment(SwingConstants.CENTER);
		textlb.setForeground(Color.BLACK);
		textlb.setFont(f);
		pay.add(textlb,BorderLayout.SOUTH);
		add(pay);
/*---------------------------------------------------------------------*/
		payimg = new JPanel(new GridLayout());
		payimg.setBounds(100, y*2/8+50, x/2-216, y*5/8-50);// x=Location(�¿���ǥ), y=Location(������ǥ),width=�¿�ũ��,Height=����ũ��
		paybtn = new JButton(Ccon2);
		paybtn.addActionListener(this);
		payimg.add(paybtn);
		add(payimg);
	}
	void display() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setTitle("�����Ϸ�");
		setLocation(0,0);
		setSize(x/2,y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==paybtn) {
			Bindae_End bend = new Bindae_End();
			bend.display();
		}
	}
}
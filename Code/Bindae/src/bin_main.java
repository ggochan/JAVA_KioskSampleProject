import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/* -----------����------------ */
public class bin_main extends JFrame implements ActionListener{
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int x= screenSize.width;
	int y= screenSize.height; // ��ũ�� ������
	private JPanel mainpa,subpa;
	private Basic_Button Selbtn2;
	private JButton Newbtn,Selbtn;
	private String img1 = "images\\main.png";
	
	private ImageIcon Dcon1 = new ImageIcon(img1);  //ImageIcon���� Image�� ����
	private Image Dimg1 = Dcon1.getImage(); //����� Image�� ũ�⸦ �����Ͽ� ���ο� Image��ü ����
	private Image Cimg1= Dimg1.getScaledInstance(x/2, y*4/5, Image.SCALE_SMOOTH ); //���ο� Image�� ImageIcon��ü�� ����
	private ImageIcon Ccon1 = new ImageIcon(Cimg1);
	private String img2 = "images\\logo.png";
	private ImageIcon Dcon2 = new ImageIcon(img2);  //ImageIcon���� Image�� ����
	private Image Dimg2 = Dcon2.getImage(); //����� Image�� ũ�⸦ �����Ͽ� ���ο� Image��ü ����
	private Image Cimg2= Dimg2.getScaledInstance(150, 150, Image.SCALE_SMOOTH ); //���ο� Image�� ImageIcon��ü�� ����
	private ImageIcon Ccon2 = new ImageIcon(Cimg2);
	
	bin_main() {
	Font f = new Font("���� ���",Font.BOLD,45);
	
	mainpa = new JPanel();
	mainpa.setBackground(Color.WHITE);
	mainpa.setBounds(0, 0, x/2, y*4/5);
	add(mainpa);
		
	Newbtn = new JButton(Ccon1);
	Newbtn.setPreferredSize(new Dimension(x/2,y*4/5));
	Newbtn.setBorderPainted(false);// ��ư�ܰ���x
	Newbtn.setFocusPainted(false);// ��Ŀ�� �׵θ� x
	//Newbtn.setOpaque(false); //�����ϰ�
	Newbtn.addActionListener(this);
	Newbtn.setBounds(0, 0, x/2, y*4/5);
	mainpa.add(Newbtn);
	
	subpa= new JPanel();
	subpa.setBackground(Color.WHITE);
	subpa.setBounds(0, y*4/5, x/2, y*1/5);
	add(subpa);
	
	Selbtn = new JButton(Ccon2);
	Selbtn.setBorderPainted(false);
	Selbtn.setFocusPainted(false);// ��Ŀ�� �׵θ� x
	Selbtn.addActionListener(this);
	Selbtn.setBackground(Color.WHITE);
	subpa.add(Selbtn);
	
	Selbtn2 = new Basic_Button(" �ֹ��� �Ͻ÷��� ��ġ�ϼ���!",Color.WHITE);
	Selbtn2.addActionListener(this);
	Selbtn2.setFont(f);
	subpa.add(Selbtn2);
	

}
	void display() {
					getContentPane().setBackground(new Color(255,255,255));
					//setUndecorated(true);
					add(mainpa);	
					setLayout(null);
					setTitle("�߾Ӻ�붱 ����ǰ");
					setLocation(0,0);
					setSize(x/2,y);
					setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Newbtn || e.getSource() == Selbtn2 || e.getSource() == Selbtn) {
			Bindae_Select1 bs1 = new Bindae_Select1();
			bs1.display();
		}
		
	}

}

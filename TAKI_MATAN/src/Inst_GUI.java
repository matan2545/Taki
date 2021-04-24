import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inst_GUI {

	private JFrame frame;
	private JButton NEXT;
	private JLabel PAGE1;
	private JButton HOME;
	private JLabel PAGE2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inst_GUI window = new Inst_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inst_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Photos\\TAKI_logo.png"));
		frame.setTitle("TAKI BY MATAN ANTEBI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false); 
		frame.setSize(925, 925);
		frame.setLocationRelativeTo(null);
		
		NEXT = new JButton("");
		NEXT.setIcon(new ImageIcon("Photos\\NEXT_BTN.png"));
		NEXT.setBorderPainted(false);
		NEXT.setBounds(82, 35, 168, 72);
		frame.getContentPane().add(NEXT);
		
		PAGE1 = new JLabel("");
		PAGE1.setIcon(new ImageIcon("Photos\\INST_1.png"));
		PAGE1.setBounds(0, 0, 909, 886);
		frame.getContentPane().add(PAGE1);
		//
		HOME = new JButton("");
		HOME.setIcon(new ImageIcon("Photos\\HOME_BTN.png"));
		HOME.setBorderPainted(false);
		HOME.setBounds(82, 35, 168, 72);
		HOME.setVisible(false);
		frame.getContentPane().add(HOME);
		
		PAGE2 = new JLabel("");
		PAGE2.setIcon(new ImageIcon("Photos\\INST_2.png"));
		PAGE2.setBounds(0, 0, 909, 886);
		PAGE2.setVisible(false);
		frame.getContentPane().add(PAGE2);
		

		
		NEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NEXT.setVisible(false);
				PAGE1.setVisible(false);
				HOME.setVisible(true);
				PAGE2.setVisible(true);
			}
		});
		
		HOME.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_GUI.main(null);
				frame.dispose();
			}
		});

	}
}

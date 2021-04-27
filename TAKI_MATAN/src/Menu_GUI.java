import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Menu_GUI {

	private JFrame frmTakiByMatan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_GUI window = new Menu_GUI();
					window.frmTakiByMatan.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTakiByMatan = new JFrame();
		frmTakiByMatan.setIconImage(Toolkit.getDefaultToolkit().getImage("Photos\\TAKI_logo.png"));
		frmTakiByMatan.setTitle("TAKI BY MATAN ANTEBI");
		frmTakiByMatan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTakiByMatan.getContentPane().setLayout(null);
		frmTakiByMatan.setResizable(false); 
		frmTakiByMatan.setSize(925, 925);
		frmTakiByMatan.setLocationRelativeTo(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon("Photos\\1V1.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game_GUI.main(null, 0);
				frmTakiByMatan.dispose();
			}
		});
		btnNewButton.setBounds(261, 309, 407, 112);
		frmTakiByMatan.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game_GUI.main(null, 1);
				frmTakiByMatan.dispose();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon("Photos\\1VPC.png"));
		btnNewButton_1.setBounds(261, 432, 407, 112);
		frmTakiByMatan.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTakiByMatan.dispose();
				Inst_GUI.main(null);
			}
		});
		btnNewButton_1_1.setBorderPainted(false);
		btnNewButton_1_1.setIcon(new ImageIcon("Photos\\HELPTAKI.png"));
		btnNewButton_1_1.setBounds(261, 555, 407, 112);
		frmTakiByMatan.getContentPane().add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1.setBorderPainted(false);
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1_1_1.setIcon(new ImageIcon("Photos\\EXIT.png"));
		btnNewButton_1_1_1.setBounds(261, 678, 407, 112);
		frmTakiByMatan.getContentPane().add(btnNewButton_1_1_1);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("Photos\\TAKI_MENU.png"));
		lblNewLabel_1.setBounds(0, 0, 909, 886);
		frmTakiByMatan.getContentPane().add(lblNewLabel_1);
	}
}

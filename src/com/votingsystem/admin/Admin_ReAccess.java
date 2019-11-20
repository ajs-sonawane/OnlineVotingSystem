package com.votingsystem.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.votingsystem.index.Index;
import com.votingsystem.voter.VoterRegister;
import com.votingsystem.voter.Voting;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class Admin_ReAccess extends JFrame {

	private JPanel contentPane;
	VoterRegister vr = new VoterRegister();
	Connection con = vr.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_ReAccess frame = new Admin_ReAccess();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		new Index().btnVerify.setEnabled(false);
		new Index().btnAdminLogin.setEnabled(false);
		new Voting().dispose();
	}

	/**
	 * Create the frame.
	 */
	public Admin_ReAccess() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 379);
		// setBackground(new Color(0,0,0,0));
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setUndecorated(true);

		setBackground(new Color(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdminAccess = new JButton("ADMIN ACCESS");
		btnAdminAccess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String adminPWD = JOptionPane.showInputDialog("Enter Admin Password To Access");

				if (adminPWD.isEmpty()) {
					JOptionPane.showMessageDialog(btnAdminAccess, "Please Enter Password");
				} else {
					int adminPass = Integer.parseInt(adminPWD);
					int adminpassDB = 0;

					try {

						String sql = "select AdminPwd from Admin where AdminPwd =?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setInt(1, adminPass);
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {

							adminpassDB = rs.getInt(1);
						}
						Preferences adminPref= Preferences.userNodeForPackage(Index.class);
						String adminUNfromPref = adminPref.get("adminUserName", null);
						int adminPWDfromPref = adminPref.getInt("adminPassword", 0);
						
						if (adminPass == adminPWDfromPref) {
							Admin ad = new Admin();
							 ad.setVisible(true);
							
							 dispose();
							 new Index().dispose();
							 
						}

					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
			}
		});
		btnAdminAccess.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAdminAccess.setBounds(235, 158, 123, 25);
		contentPane.add(btnAdminAccess);
		
		
		JLabel lblAdminn_Image = new JLabel("");
		lblAdminn_Image.setIcon(new ImageIcon(Voting.class.getResource("/adminn.png")));

		lblAdminn_Image.setBounds(53, 24, 347, 295);
		contentPane.add(lblAdminn_Image);
		
		JLabel lblVotingClosed = new JLabel("VOTING CLOSED");
		lblVotingClosed.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblVotingClosed.setBackground(Color.PINK);
		lblVotingClosed.setFont(new Font("Sylfaen", Font.BOLD, 30));
		lblVotingClosed.setHorizontalAlignment(SwingConstants.CENTER);
		lblVotingClosed.setForeground(Color.RED);
		lblVotingClosed.setBounds(53, 318, 358, 48);
		contentPane.add(lblVotingClosed);
	}
}

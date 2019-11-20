package com.votingsystem.index;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.votingsystem.admin.Admin;
//import com.mysql.jdbc.PreparedStatement;
import com.votingsystem.voter.VoterRegister;
import com.votingsystem.voter.Voting;

import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import javax.swing.JPasswordField;

public class Index extends JFrame {

	private JPanel contentPane;
	private JTextField txtVoterID;
	private JTextField txtMob;
	private JTextField txtAdminUserName;

	String usernameDB = null;
	int pwdDB = 0;

	public static JButton btnVerify;
	public static JButton btnAdminLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	VoterRegister vr = new VoterRegister();
	Connection con = vr.getConnection();
	private JPasswordField txtPasswrd;

	public Index() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JPanel panel = new JPanel();

		panel.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 255, 0)),
				new BevelBorder(BevelBorder.RAISED, null, new Color(255, 200, 0), null, null)));
		panel.setBounds(26, 145, 357, 264);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Enter Voter ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		// lblNewLabel.setIcon(new ImageIcon(Index.class.getResource("/.png")));//
		// -----------------------------

		lblNewLabel.setBounds(45, 71, 95, 16);
		panel.add(lblNewLabel);

		txtVoterID = new JTextField();
		txtVoterID.setBounds(176, 64, 142, 32);
		panel.add(txtVoterID);
		txtVoterID.setColumns(10);

		JLabel lblMobileNo = new JLabel("Enter Mobile No.");
		lblMobileNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMobileNo.setBounds(45, 116, 119, 16);
		panel.add(lblMobileNo);

		btnVerify = new JButton("Verify");
		btnVerify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (txtVoterID.getText().isEmpty() && txtMob.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnVerify, "Please Provide Voter ID & Mobile No.");
				} else if (txtVoterID.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnVerify, "Please Provide Voter ID");

				} else if (txtMob.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnVerify, "Please Provide Mobile No.");

				} else {

					// when Verify button is pressed....

					// getting the user inputs
					int VoterID = Integer.parseInt(txtVoterID.getText().toString());
					int VoterMob = Integer.parseInt(txtMob.getText().toString());

					int voterIdFromDB = 0;
					int voterMobFromDB = 0;
					int votertrackIdFromDB = 0;
					String votertrackLocationFromDB = null;
					String dateFromDB = null;
					try {

						// checking the user given values are the correct or not
						String sql = "select vFirstName, vLastName from Voter where VoterId= ? && vMobno=?";

						PreparedStatement ps = con.prepareStatement(sql);
						ps.setInt(1, VoterID);
						ps.setInt(2, VoterMob);

						ResultSet rs = ps.executeQuery();

						while (rs.next()) {
							JOptionPane.showMessageDialog(btnVerify,
									"Welcome " + rs.getString(1).toUpperCase() + " " + rs.getString(2).toUpperCase());

						}

						// for checking the following if() condition
						String sql1 = "select VoterId, vMobno from Voter where VoterId =?";
						PreparedStatement ps1 = con.prepareStatement(sql1);
						ps1.setInt(1, VoterID);
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							voterIdFromDB = rs1.getInt(1);
							voterMobFromDB = rs1.getInt(2);

						}

						// for checking the following if condition
						String sql2 = "select VoterId, VoterLocation from VoterTrack where VoterId=?";
						PreparedStatement ps2 = con.prepareStatement(sql2);
						ps2.setInt(1, VoterID);
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							votertrackIdFromDB = rs2.getInt(1);
							votertrackLocationFromDB = rs2.getString(2);

						}

						String sql3 = "SELECT DATE(datetime) FROM votertrack where VoterId = ?";
						PreparedStatement ps3 = con.prepareStatement(sql3);
						ps3.setInt(1, VoterID);
						ResultSet rs3 = ps3.executeQuery();
						while (rs3.next()) {
							dateFromDB = rs3.getString(1);
						}

						// getting date that set by admin
						Preferences adminPref = Preferences.userNodeForPackage(Index.class);
						String date = adminPref.get("vDate", null);
						String location = adminPref.get("vLocation", null);

						// getting Time from voting.java
						Preferences voterPref = Preferences.userNodeForPackage(Index.class);
						// String time= voterPref.get("time", null);

						// String dateTime= date+" "+time; //concatenation of date n time for comparison
						// in if() condition

						if (VoterID == voterIdFromDB && VoterMob == voterMobFromDB) // will check if the user id=s a
																					// registered voter or not
						{
							System.out.println("true");

							if (dateFromDB == null) {

								voterPref.putInt("voterID", VoterID);

								// =================================after Voter validation, voter taken to
								// another JFrame >VotingFrame- to vote

								System.out.println("1st if ()");

								Voting vt = new Voting();
								vt.setVisible(true);
								dispose();

							} else if (dateFromDB.equals(date)) {

								JOptionPane.showMessageDialog(btnVerify, "You Have Already Voted ! ! !");

							} else {

								voterPref.putInt("voterID", VoterID);

								// =================================after Voter validation, voter taken to
								// another JFrame >VotingFrame- to vote

								Voting vt = new Voting();
								vt.setVisible(true);
								dispose();

							}

						} else {
							JOptionPane.showMessageDialog(btnVerify, "Not a registered Voter");
						}

						// con.close();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(btnVerify, "ERROR:	Contact Developer > A.Son. " + e);
						// System.out.println(e);

					}

				}

			}
		});

		btnVerify.setEnabled(false);// **********************************************
		btnVerify.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVerify.setBounds(45, 156, 273, 32);
		panel.add(btnVerify);

		txtMob = new JTextField();
		txtMob.setColumns(10);
		txtMob.setBounds(176, 109, 142, 32);
		panel.add(txtMob);

		JLabel lblVotingSystem = new JLabel("VOTING SYSTEM");
		lblVotingSystem.setForeground(new Color(0, 128, 128));
		lblVotingSystem.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVotingSystem.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblVotingSystem.setBounds(210, 53, 316, 58);
		contentPane.add(lblVotingSystem);

		JLabel lblAdmin = new JLabel("Admin User Name");
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdmin.setBounds(395, 218, 119, 16);
		contentPane.add(lblAdmin);

		txtAdminUserName = new JTextField();
		txtAdminUserName.setColumns(10);
		txtAdminUserName.setBounds(536, 211, 134, 32);
		contentPane.add(txtAdminUserName);

		JLabel lblAdminPassword = new JLabel("Admin Password");
		lblAdminPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdminPassword.setBounds(395, 263, 119, 16);
		contentPane.add(lblAdminPassword);

		btnAdminLogin = new JButton("ADMIN LOGIN");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtAdminUserName.getText().isEmpty() && txtPasswrd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnAdminLogin, "Please Provide User Name & Password");
				} else if (txtAdminUserName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnAdminLogin, "Please Provide User Name");

				} else if (txtPasswrd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnAdminLogin, "Please Provide Password");

				} else

					try {

						// userName nd Passwd will match in db and returns the full name of the admin
						// via resultSet

						/*
						 * String sql1 =
						 * "select AdminUserName, AdminPwd from Admin where AdminUserName=? and AdminPwd= ?"
						 * ; PreparedStatement ps1 = con.prepareStatement(sql1); ps1.setString(1,
						 * adminUserName); ps1.setInt(2, adminPwd); ResultSet rs1 = ps1.executeQuery();
						 * 
						 * while (rs1.next()) { usernameDB = rs1.getString(1); pwdDB = rs1.getInt(2);
						 * 
						 * }
						 * 
						 */

						String adminUserName = txtAdminUserName.getText();
						int adminPwd = Integer.parseInt(txtPasswrd.getText());

						String sql = "select AdminFullName, AdminUserName, AdminPwd from Admin where AdminUserName= ? && AdminPwd= ?";

						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, adminUserName);
						ps.setInt(2, adminPwd);

						ResultSet rs = ps.executeQuery();
						while (rs.next()) {

							JOptionPane.showMessageDialog(btnAdminLogin, "Welcome - " + rs.getString(1).toUpperCase());

							usernameDB = rs.getString(2);
							pwdDB = rs.getInt(3);

						}

						if (adminUserName.equals(usernameDB) && adminPwd == pwdDB) {

							Preferences adminPref = Preferences.userNodeForPackage(Index.class);
							adminPref.put("adminUserName", adminUserName);
							adminPref.putInt("adminPassword", adminPwd);

							Admin ad = new Admin();
							ad.setVisible(true);
							dispose();

						} else {

							JOptionPane.showMessageDialog(btnAdminLogin, "Invalid Credentials");
						}

					} catch (Exception e) {
						System.out.println(e);
					}

			}

		});
		btnAdminLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdminLogin.setBounds(395, 303, 275, 32);
		contentPane.add(btnAdminLogin);
		
		txtPasswrd = new JPasswordField();
		txtPasswrd.setBounds(536, 258, 134, 32);
		contentPane.add(txtPasswrd);
		setLocationRelativeTo(null);

	}
}

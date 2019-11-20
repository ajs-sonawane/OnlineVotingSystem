package com.votingsystem.voter;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.votingsystem.index.Index;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Label;

import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.prefs.Preferences;

import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Voting extends JFrame {

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
					Voting frame = new Voting();
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
	public Voting() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JLabel lblNewLabel = new JLabel("VOTING AREA");
		lblNewLabel.setBorder(UIManager.getBorder("Button.border"));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.ITALIC, 22));
		lblNewLabel.setBounds(266, 29, 157, 43);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(63, 109, 550, 2);
		contentPane.add(separator);

		// =======================================================party logos-Label
		JLabel lblBjp = new JLabel("");
		lblBjp.setIcon(new ImageIcon(Voting.class.getResource("/bjplogo.png")));// -----------------------------
		lblBjp.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, SystemColor.activeCaption, null));
		lblBjp.setBounds(150, 150, 90, 69);
		contentPane.add(lblBjp);

		JLabel lblCongress = new JLabel("");
		lblCongress.setIcon(new ImageIcon(Voting.class.getResource("/congress.png")));// -----------------------------
		lblCongress.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, SystemColor.activeCaption, null));
		lblCongress.setBounds(150, 247, 90, 69);
		contentPane.add(lblCongress);

		JLabel lblAap = new JLabel("");
		lblAap.setIcon(new ImageIcon(Voting.class.getResource("/aap.png")));// -----------------------------
		lblAap.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, SystemColor.activeCaption, null));
		lblAap.setBounds(150, 341, 90, 69);
		contentPane.add(lblAap);

		JLabel lblPleaseVoteAnyone = new JLabel("Please vote anyone of the following !!!");
		lblPleaseVoteAnyone.setForeground(SystemColor.textInactiveText);
		lblPleaseVoteAnyone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPleaseVoteAnyone.setBounds(213, 70, 277, 26);
		contentPane.add(lblPleaseVoteAnyone);

		// ==================================================================Creating
		// checkBoxes

		JCheckBox chckbxRandom = new JCheckBox("RandomCheckBox");
		chckbxRandom.setMnemonic(KeyEvent.VK_B);// --------------------
		chckbxRandom.setActionCommand("RandomCheckBox");// -----------
		chckbxRandom.setSelected(true);

		JCheckBox chckbxBjp = new JCheckBox("BJP");
		chckbxBjp.setMnemonic(KeyEvent.VK_B);// --------------------
		chckbxBjp.setBounds(266, 150, 108, 69);
		chckbxBjp.setActionCommand("BJP");// -----------
		contentPane.add(chckbxBjp);

		JCheckBox chckbxCongress = new JCheckBox("CONGRESS");
		chckbxCongress.setMnemonic(KeyEvent.VK_B);// --------------------
		chckbxCongress.setBounds(266, 247, 108, 69);
		chckbxCongress.setActionCommand("CONGRESS");// ----------
		contentPane.add(chckbxCongress);

		JCheckBox chckbxAap = new JCheckBox("AAP");
		chckbxAap.setMnemonic(KeyEvent.VK_B);// --------------------
		chckbxAap.setBounds(266, 341, 108, 69);
		chckbxAap.setActionCommand("AAP");// --------
		contentPane.add(chckbxAap);

		// ============================================== add group checkbox
		ButtonGroup bg = new ButtonGroup();
		bg.add(chckbxBjp);
		bg.add(chckbxCongress);
		bg.add(chckbxAap);

		// ===================================================Register a listener for
		// the checkBox buttons.
		chckbxBjp.addActionListener(null);
		chckbxCongress.addActionListener(null);
		chckbxAap.addActionListener(null);

		JButton btnVote = new JButton("VOTE");
		btnVote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selection = bg.getSelection().getActionCommand();

				// get voterID from index
				// int voterID = Integer.parseInt(ind.txtVoterID.getText().toString());
				// int voterMOB = Integer.parseInt(ind.txtMob.getText().toString());

				// saving into i for determining the user input whether is yes or no
				int i = JOptionPane.showConfirmDialog(btnVote, "Are you Sure? You want to VOTE >>> " + selection);

				if (i == 0) {

					try {
						int valueFromDB = 0;
						String sql = "select TotalVotings from Votings where PartyName= ?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, selection);

						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							valueFromDB = rs.getInt(1);// getting the int value from DB
						}

						String sql1 = "update Votings set TotalVotings=? where PartyName= ?";
						PreparedStatement ps1 = con.prepareStatement(sql1);
						ps1.setInt(1, valueFromDB + 1);
						ps1.setString(2, selection);

						int i1 = ps1.executeUpdate();
						if (i1 > 0) {

							JOptionPane.showMessageDialog(btnVote, "THANK YOU !!!   You have Voted  >>> " + selection);

//-------------------------------------
							Preferences voterPref = Preferences.userNodeForPackage(Index.class);
							int voterID = voterPref.getInt("voterID", 0);
							String vDate = voterPref.get("vDate", null);
							String vLocation = voterPref.get("vLocation", null);
//-----------------------------------

							// for the current voting time recording...
							Calendar cal = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							String time = sdf.format(cal.getTime());

							voterPref.put("time", time);
							
							// Long time = System.currentTimeMillis();// current system time

							String datetime = vDate + " " + time; // concatenation of string=date and long=time

							
							// System.out.println(vID+"----------"+location+ "---------"+ time1 );

							String sql2 = "insert into VoterTrack(VoterId, VoterLocation, DateTime) Values (?, ?, ?)";
							PreparedStatement ps2 = con.prepareStatement(sql2);
							// take voterID from index.java and the date & location from admin also
							// time has to set here , for the current voting time
							ps2.setInt(1, voterID);
							ps2.setString(2, vLocation);
							ps2.setString(3, datetime);
							int ii = ps2.executeUpdate();

							if (ii > 0) {
								JOptionPane.showMessageDialog(btnVote, "THANK YOU !!");

								Index id = new Index();
								id.setVisible(true);

								id.btnVerify.setEnabled(true);
								dispose();
								
							}

						} else {
							JOptionPane.showMessageDialog(btnVote, "ERROR: Contact Developer > ASon");

						}

					} catch (Exception e1) {
						System.out.println(e1);
					}

				} else if (i == 1) {

					JOptionPane.showMessageDialog(btnVote, "Please Vote Any One Party");

				}
			}
		});

		btnVote.setIcon(new ImageIcon(Voting.class.getResource("/vote.png")));
		btnVote.setFont(new Font("Arial Black", Font.BOLD, 20));
		btnVote.setBounds(461, 240, 152, 91);
		contentPane.add(btnVote);
		setLocationRelativeTo(null);
	}
}

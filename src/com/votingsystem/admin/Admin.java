package com.votingsystem.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;

import org.omg.CORBA.PERSIST_STORE;

import com.votingsystem.index.Index;
import com.votingsystem.voter.VoterRegister;
import com.votingsystem.voter.VoterSearch;
import com.votingsystem.voter.Voting;

import net.proteanit.sql.DbUtils;

import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.prefs.Preferences;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.toedter.calendar.JDateChooser;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import com.toedter.calendar.JCalendar;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;
import java.text.*;

public class Admin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	VoterRegister vr = new VoterRegister();
	Connection con = vr.getConnection();
	String dateChoosed = "";
	String choosedLocation = "";

	int setKill = 0;

	static int hours = 0;
	static int minutes = 0;
	static int seconds = 0;
	static int milliseconds = 0;

	static boolean state = true;
	private JTextField txtHours;
	private JTextField txtMinutes;
	private JTextField txtSeconds;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
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
	public Admin() {
		Preferences adminPref = Preferences.userNodeForPackage(Index.class);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"<<  ADMIN PANEL  >>", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(12, 23, 658, 135);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("New VOTER Registration");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				VoterRegister vr = new VoterRegister();
				vr.setVisible(true);

				dispose();

			}
		});

		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel.setBounds(12, 23, 220, 54);
		panel.add(lblNewLabel);

		JLabel lblAddSubadmin = new JLabel("ADD SUB - ADMIN");
		lblAddSubadmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				RegisterSub_Admin ra = new RegisterSub_Admin();
				ra.setVisible(true);

				dispose();

			}
		});
		lblAddSubadmin.setForeground(Color.BLUE);
		lblAddSubadmin.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblAddSubadmin.setBounds(12, 79, 159, 27);
		panel.add(lblAddSubadmin);

		JButton btnNewButton_1 = new JButton("Search Voter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				VoterSearch vs = new VoterSearch();
				vs.setVisible(true);

				dispose();

			}
		});
		btnNewButton_1.setBounds(437, 52, 159, 39);
		panel.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("");
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setToolTipText("");
		scrollPane.setBounds(12, 220, 268, 220);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		JButton btnNewButton = new JButton("Refresh Results");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// this method will be used to display the Party Name and Party's Total votings
				// done on app from DB
				try {

					String sql = "select PartyName, TotalVotings from Votings";
					PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();

					table.setModel(DbUtils.resultSetToTableModel(rs));// resultSet i.e. rs is converted to the table
																		// model by DBUtils

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}

			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton.setBounds(12, 171, 268, 36);
		contentPane.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(297, 171, 373, 269);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSetVotingDetails = new JLabel("<= Set Voting Details =>");
		lblSetVotingDetails.setFont(new Font("Candara", Font.BOLD, 15));
		lblSetVotingDetails.setBounds(112, 13, 155, 23);
		panel_1.add(lblSetVotingDetails);

		JButton btnNewButton_2 = new JButton("Set Voting Location");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				choosedLocation = JOptionPane.showInputDialog(btnNewButton_2, "SET LOCATION");

			}
		});
		btnNewButton_2.setBounds(25, 78, 145, 36);
		panel_1.add(btnNewButton_2);

		JButton btnSetVotingDate = new JButton("Set Voting Date");
		btnSetVotingDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDateChooser txtDateChosen = new JDateChooser();
				String msg = "SET THE DATE:\n";

				Object[] params = { msg, txtDateChosen }; // stored msg and txtDateChosen in params[] array to make
															// display in JOptionPane.showConfirmDialog
				JOptionPane.showConfirmDialog(null, params, "SET DATE", JOptionPane.PLAIN_MESSAGE);

				// to get the date from user // date stored in dateChosed in format- yyyy/MM/dd
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateChoosed = sdf.format(((JDateChooser) params[1]).getDate());// Casting params[1] makes me able to get
																				// its information

			}
		});
		btnSetVotingDate.setBounds(25, 127, 145, 36);
		panel_1.add(btnSetVotingDate);

		JButton btnStartVoting = new JButton("Start Voting");
		btnStartVoting.setBackground(Color.WHITE);
		btnStartVoting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					Preferences adminPref = Preferences.userNodeForPackage(Index.class);
					adminPref.put("vDate", dateChoosed);
					adminPref.put("vLocation", choosedLocation);
					// adminPref.put("start", lblStarted.isEnabled());

				} catch (Exception e) {
					System.out.println(e);
				}

				// Admin_ReAccess ara= new Admin_ReAccess();

				Index id = new Index();
				id.btnVerify.setEnabled(true);
				id.btnAdminLogin.setEnabled(false);

				id.setVisible(true);
//---------------------------------------------------------------------

				TimePanel_AdminAccess tp = new TimePanel_AdminAccess();
				tp.setVisible(true);

//------------------------------------------------------------------------------------

				dispose();

			}
		});
		btnStartVoting.setBounds(25, 208, 315, 29);
		panel_1.add(btnStartVoting);

		JButton btnSetTime = new JButton("Set Time Limit");
		btnSetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// setKill = Integer.parseInt(JOptionPane.showInputDialog(btnKillHours, "SET
				// TIME LIMIT in 'Seconds'"));

				int seconds = Integer.parseInt(txtSeconds.getText().toString());
				int minutes = Integer.parseInt(txtMinutes.getText().toString());
				int hours = Integer.parseInt(txtHours.getText().toString());

				// System.out.println(hours+":"+minutes+":"+seconds);

				String timeLimit = hours + ":" + minutes + ":" + seconds;
				// System.out.println(timeLimit);

				adminPref.put("timeLimit", timeLimit);
				
				JOptionPane.showMessageDialog(btnSetTime, "Time Set!");

			}
		});
		btnSetTime.setBounds(203, 125, 137, 36);
		panel_1.add(btnSetTime);

		txtHours = new JTextField();
		txtHours.setText("00");
		txtHours.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {

			}
		});
		txtHours.setName("");
		txtHours.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		txtHours.setBounds(203, 83, 32, 29);
		panel_1.add(txtHours);
		txtHours.setColumns(10);

		JButton btnResetTime = new JButton("reset");
		btnResetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHours.setText("00");
				txtMinutes.setText("00");
				txtSeconds.setText("00");
				
			}
		});
		btnResetTime.setBackground(new Color(218, 165, 32));
		btnResetTime.setForeground(new Color(0, 0, 0));
		btnResetTime.setBounds(203, 163, 137, 23);
		panel_1.add(btnResetTime);

		JLabel label = new JLabel(" :");
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setBounds(236, 76, 16, 36);
		panel_1.add(label);

		JLabel label_1 = new JLabel(" :");
		label_1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label_1.setBounds(291, 76, 16, 36);
		panel_1.add(label_1);

		txtMinutes = new JTextField();
		txtMinutes.setText("00");
		txtMinutes.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		txtMinutes.setColumns(10);
		txtMinutes.setBounds(253, 83, 32, 29);
		panel_1.add(txtMinutes);

		txtSeconds = new JTextField();
		txtSeconds.setText("00");
		txtSeconds.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		txtSeconds.setColumns(10);
		txtSeconds.setBounds(308, 83, 32, 29);
		panel_1.add(txtSeconds);
		
		JLabel lblHh = new JLabel("HH");
		lblHh.setBounds(203, 64, 26, 16);
		panel_1.add(lblHh);
		
		JLabel lblMm = new JLabel("mm");
		lblMm.setBounds(253, 64, 26, 16);
		panel_1.add(lblMm);
		
		JLabel lblSs = new JLabel("ss");
		lblSs.setBounds(308, 64, 26, 16);
		panel_1.add(lblSs);
		setLocationRelativeTo(null);
	}
}

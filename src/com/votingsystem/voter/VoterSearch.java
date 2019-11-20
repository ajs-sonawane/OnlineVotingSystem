package com.votingsystem.voter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.votingsystem.admin.Admin;

public class VoterSearch extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearchVoterID;
	private JTable table;

	VoterRegister vr = new VoterRegister();
	Connection con = vr.getConnection();
	private JTextField txtSearchBy_Location;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoterSearch frame = new VoterSearch();
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
	public VoterSearch() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(true);
		scrollPane.setToolTipText("");
		scrollPane.setName("");
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(12, 109, 670, 299);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		// table.setTableHeader();

		txtSearchVoterID = new JTextField();
		txtSearchVoterID.setBounds(594, 45, 71, 31);
		contentPane.add(txtSearchVoterID);
		txtSearchVoterID.setColumns(10);

		JButton btnSearchVoter = new JButton("Search Voter By ID");
		btnSearchVoter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = txtSearchVoterID.getText().toString();
				if (s.isEmpty()) {
					JOptionPane.showMessageDialog(txtSearchVoterID, "Please Enter Voter ID");
				} else {
					try {

						int voterID = Integer.parseInt(txtSearchVoterID.getText().toString());

						String sql = "select Voter.VoterId, Voter.vFirstName, Voter.vLastName,Voter.vMobno, Voter.vAddress, votertrack.VoterLocation, "
								+ "votertrack.DateTime from votertrack LEFT JOIN Voter ON votertrack.VoterId = Voter.VoterId where Voter.VoterId= ?";

						PreparedStatement ps = con.prepareStatement(sql);
						ps.setInt(1, voterID);

						ResultSet rs = ps.executeQuery();

						table.setModel(DbUtils.resultSetToTableModel(rs));// resultSet i.e. rs is converted to the table
																			// model by DBUtils
					

					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		});
		btnSearchVoter.setBounds(441, 45, 141, 31);
		contentPane.add(btnSearchVoter);

		txtSearchBy_Location = new JTextField();
		txtSearchBy_Location.setColumns(10);
		txtSearchBy_Location.setBounds(169, 45, 102, 31);
		contentPane.add(txtSearchBy_Location);

		JButton btnSearchByLocation = new JButton("Search by Location");
		btnSearchByLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String byLocation = txtSearchBy_Location.getText().toString();

				if (byLocation.isEmpty()) {

					JOptionPane.showMessageDialog(btnSearchByLocation, "Please Enter Location");

				} else {
					try {

						String sql = "select voter.VoterId, voter.vFirstName, voter.vLastName,voter.vMobno, voter.vAddress, votertrack.VoterLocation, "
								+ "votertrack.DateTime from votertrack LEFT JOIN voter ON votertrack.VoterId = voter.VoterId where votertrack.VoterLocation=?";

						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, byLocation);

						ResultSet rs = ps.executeQuery();

						table.setModel(DbUtils.resultSetToTableModel(rs));// resultSet i.e. rs is converted to the table
																			// model by DBUtils

					} catch (Exception e) {
						System.out.println(e);
					}

				}
			}
		});
		btnSearchByLocation.setBounds(12, 45, 145, 31);
		contentPane.add(btnSearchByLocation);

		JButton btnBackAdminPanel = new JButton("<< Back to Admin Panel");
		btnBackAdminPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Admin ad = new Admin();
				ad.setVisible(true);

				dispose();

			}
		});
		btnBackAdminPanel.setBounds(12, 427, 189, 25);
		contentPane.add(btnBackAdminPanel);

		JButton btnNewButton = new JButton("Show All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					String sql = "select * from Voter";
					PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					// while(rs.next()) {
					table.setModel(DbUtils.resultSetToTableModel(rs));// resultSet i.e. rs is converted to the table
					// model by DBUtils

				} catch (Exception e) {
					System.err.println(e);
				}

			}
		});
		btnNewButton.setBounds(303, 45, 102, 30);
		contentPane.add(btnNewButton);
		setLocationRelativeTo(null);
	}
}

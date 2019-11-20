package com.votingsystem.voter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

import com.votingsystem.admin.Admin;

import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
//import javax.swing.JSeparator;
//import java.awt.Color;
import java.awt.Font;
//import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VoterRegister extends JFrame {

	public static Connection con;
	private JPanel contentPane;

	// we hav to make all the following as static to be able to access in the main()
	private static JTextField txtFName;
	private static JTextField txtLName;
	private static JTextField txtMob;
	private static JTextField txtAdd;

	// making accessible throughout the class
	static String frstName;
	static String lstName;
	static Long MobNo;
	static String Addr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoterRegister frame = new VoterRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}// -------------------------------------------------------------------------------------

	/**
	 * Create the frame.
	 */
	public VoterRegister() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 416, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// setUndecorated(true);
		setResizable(false);
		
		
		

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"< VOTER REGISTRATION >", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnBackAdminPanel = new JButton("<< Back to Admin Panel");
		btnBackAdminPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Admin ad = new Admin();
				ad.setVisible(true);
				
				dispose();
				
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBackAdminPanel, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnBackAdminPanel)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		txtFName = new JTextField();
		txtFName.setBounds(153, 68, 183, 37);
		txtFName.setFont(new Font("Cambria Math", Font.PLAIN, 19));
		txtFName.setColumns(10);

		JLabel lblNewLabel = new JLabel("First Name   :");
		lblNewLabel.setBounds(23, 74, 112, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblLastName = new JLabel("Last Name   :");
		lblLastName.setBounds(23, 129, 112, 22);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblMobileNo = new JLabel("Mobile No.   :");
		lblMobileNo.setBounds(23, 185, 112, 22);
		lblMobileNo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblAddress = new JLabel("Address      :");
		lblAddress.setBounds(23, 240, 112, 22);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(153, 310, 183, 41);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// getting the text from textField of swing
				frstName = txtFName.getText().toString();
				lstName = txtLName.getText().toString();
				MobNo = Long.parseLong(txtMob.getText().toString()); // converted to integer
				Addr = txtAdd.getText().toString();

				// when Register Button Pressed.....

				getConnection();

				try {

					String sql = "insert into Voter(vFirstName, vLastName, vMobno, vAddress) values(?, ?, ?, ?)";

					PreparedStatement ps = con.prepareStatement(sql);

					ps.setString(1, frstName);
					ps.setString(2, lstName);
					ps.setLong(3, MobNo);
					ps.setString(4, Addr);

					int i = ps.executeUpdate();
					if (i > 0) {

						JOptionPane.showMessageDialog(btnRegister, "Voter Registered: Added to Database");
						// System.out.println("======== Successful =====");

					} else {
						// System.out.println("--- Error ---");
						JOptionPane.showMessageDialog(btnRegister, "Error: Please Re-Check the Details ");

					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);

				}

			}
		});

		btnRegister.setFont(new Font("Times New Roman", Font.BOLD, 17));

		txtLName = new JTextField();
		txtLName.setBounds(153, 123, 183, 37);
		txtLName.setFont(new Font("Cambria Math", Font.PLAIN, 19));
		txtLName.setColumns(10);

		txtMob = new JTextField();
		txtMob.setBounds(153, 179, 183, 37);
		txtMob.setFont(new Font("Cambria Math", Font.PLAIN, 19));
		txtMob.setColumns(10);

		txtAdd = new JTextField();
		txtAdd.setBounds(153, 234, 183, 37);
		txtAdd.setFont(new Font("Cambria Math", Font.PLAIN, 19));
		txtAdd.setColumns(10);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(23, 310, 112, 41);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// clears all the text field
				txtFName.setText(null);
				txtLName.setText(null);
				txtMob.setText(null);
				txtAdd.setText(null);

			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 17));
		panel.setLayout(null);
		panel.add(lblLastName);
		panel.add(txtLName);
		panel.add(btnClear);
		panel.add(lblAddress);
		panel.add(lblMobileNo);
		panel.add(lblNewLabel);
		panel.add(btnRegister);
		panel.add(txtFName);
		panel.add(txtMob);
		panel.add(txtAdd);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}// -------------------------------------------------------------------------------------

	public static Connection getConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/VoterDB", "root", "root");

		} catch (Exception e) {
			System.out.println(e);
		}

		return con;
	}// -------------------------------------------------------------------------------------------------

//	public void insertINTO(String frstName, String lstName, Long MobNo, String Addr) {

	/*
	 * try {
	 * 
	 * String sql =
	 * "insert into Voter(vFirstName, vLastName, vMobno, vAddress) values(?, ?, ?, ?)"
	 * ;
	 * 
	 * PreparedStatement ps = con.prepareStatement(sql);
	 * 
	 * ps.setString(1, frstName); ps.setString(2, lstName); ps.setLong(3, MobNo);
	 * ps.setString(4, Addr);
	 * 
	 * int i = ps.executeUpdate(); if (i > 0) {
	 * System.out.println("========  Successful  =====");
	 * 
	 * } else { System.out.println("--- Error ---"); }
	 * 
	 * } catch (Exception e) { System.out.println(e);
	 * 
	 * }
	 */
	// }//
	// ----------------------------------------------------------------------------------------
}

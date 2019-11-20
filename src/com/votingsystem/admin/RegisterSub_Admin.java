package com.votingsystem.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.votingsystem.voter.VoterRegister;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class RegisterSub_Admin extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtAdmin_FullName;
	private JTextField txtAdmin_Pwd;
	private JTextField txtAdmin_ConfirmPwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterSub_Admin frame = new RegisterSub_Admin();
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
	
	public RegisterSub_Admin() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JLabel lblIncludeNewAdmin = new JLabel("Include New Admin");
		lblIncludeNewAdmin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIncludeNewAdmin.setBounds(146, 32, 159, 39);
		contentPane.add(lblIncludeNewAdmin);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		lblNewLabel.setBounds(67, 102, 121, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblAdminFullName = new JLabel("Admin Full Name");
		lblAdminFullName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		lblAdminFullName.setBounds(67, 154, 121, 39);
		contentPane.add(lblAdminFullName);
		
		JLabel lblSetPasscode = new JLabel("Set Passcode");
		lblSetPasscode.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		lblSetPasscode.setBounds(67, 206, 121, 39);
		contentPane.add(lblSetPasscode);
		
		JLabel lblConfirmPasscode = new JLabel("Confirm Passcode");
		lblConfirmPasscode.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		lblConfirmPasscode.setBounds(67, 258, 121, 39);
		contentPane.add(lblConfirmPasscode);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(224, 102, 149, 39);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		txtAdmin_FullName = new JTextField();
		txtAdmin_FullName.setColumns(10);
		txtAdmin_FullName.setBounds(224, 154, 149, 39);
		contentPane.add(txtAdmin_FullName);
		
		txtAdmin_Pwd = new JTextField();
		txtAdmin_Pwd.setColumns(10);
		txtAdmin_Pwd.setBounds(224, 206, 149, 39);
		contentPane.add(txtAdmin_Pwd);
		
		txtAdmin_ConfirmPwd = new JTextField();
		txtAdmin_ConfirmPwd.setColumns(10);
		txtAdmin_ConfirmPwd.setBounds(224, 258, 149, 39);
		contentPane.add(txtAdmin_ConfirmPwd);
		
		
		
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String UserName =txtUserName.getText();
				String FullName= txtAdmin_FullName.getText();
				int Pwd= Integer.parseInt(txtAdmin_Pwd.getText());
				int ConfirmPwd= Integer.parseInt(txtAdmin_ConfirmPwd.getText());
				int FinalPwd = 0 ;
				
				if(Pwd == ConfirmPwd) {
					FinalPwd = ConfirmPwd;
				
					try {
						String sql= "insert into Admin (AdminFullName, AdminUserName, AdminPwd) values(?, ?, ?)";
						
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, FullName);
						ps.setString(2, UserName);
						ps.setInt(3, FinalPwd);
						
						int i = ps.executeUpdate();
						if (i>0) {
							JOptionPane.showMessageDialog(btnRegister, "New Admin Registered");
						}else {
							JOptionPane.showMessageDialog(btnRegister, "Contact Developer ==> ASon");
							
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e);
					}
				
				
				
				}else {
					JOptionPane.showMessageDialog(btnRegister, "Password did not Match");
				}
				
			}
		});
		btnRegister.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnRegister.setBounds(224, 310, 149, 34);
		contentPane.add(btnRegister);
		
		JButton btnClearFields = new JButton("Clear Fields");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtUserName.setText(null);
				txtAdmin_FullName.setText(null);
				txtAdmin_Pwd.setText(null);
				txtAdmin_ConfirmPwd.setText(null);
				
			}
		});
		btnClearFields.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnClearFields.setBounds(67, 310, 121, 34);
		contentPane.add(btnClearFields);
		
		JButton btnBackAdminPanel = new JButton("<< Back to Admin Panel");
		btnBackAdminPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Admin ad = new Admin();
				ad.setVisible(true);
				
				dispose();
				
			}
		});
		btnBackAdminPanel.setBounds(67, 389, 306, 25);
		contentPane.add(btnBackAdminPanel);
		setLocationRelativeTo(null);
	}
}

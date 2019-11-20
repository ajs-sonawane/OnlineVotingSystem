package com.votingsystem.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.votingsystem.index.Index;
import com.votingsystem.voter.Voting;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;
import java.awt.Component;
import javax.swing.SwingConstants;

public class TimePanel_AdminAccess extends JFrame {

	private JPanel contentPane;
	static boolean state = true;
	static int hours = 0;
	static int minutes = 0;
	static int seconds = 0;
	static int milliseconds = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimePanel_AdminAccess frame = new TimePanel_AdminAccess();
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
	public TimePanel_AdminAccess() {
		Preferences adminPref = Preferences.userNodeForPackage(Index.class);
		Admin_ReAccess ar= new Admin_ReAccess();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JLabel lblTime = new JLabel("00:00:00");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTime.setFont(new Font("Lucida Bright", Font.PLAIN, 50));
		lblTime.setBounds(55, 30, 480, 111);
		contentPane.add(lblTime);

		
		Timer t= new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
				lblTime.setText(sdf.format(new java.util.Date()));
				
				String currentTime= new SimpleDateFormat("H:m:s").format(new Date());
				String timeLimit= adminPref.get("timeLimit", null);
				
				boolean compareTime= currentTime.equals(timeLimit);{
					if(compareTime == true) {
						ar.setVisible(true);
						new Voting().dispose();
						new Index().dispose();
						dispose();
					}
				}
				//System.out.println(compareTime);
				
				
				
				
				
				
			}
		});
		t.start();
		
		
	}
}

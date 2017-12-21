
import javax.swing.*;

import common.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.text.NumberFormat;

import client.*;
import common.*;


public class ClientWindow extends JFrame implements ActionListener, KeyListener{
	
	ClientUI master;
	
	private JButton boutonValid = new JButton("Valider");
	private JButton boutonQuit = new JButton("Quit");
	private JPanel conteneur = new JPanel();
	private JPanel panelLogin = new JPanel();
	private JPanel panelHote = new JPanel();
	private JPanel panelPort = new JPanel();
	private JPanel panelBouton = new JPanel();
	private JTextField texteLogin = new JTextField("Anonymous");
	private JTextField texteHote = new JTextField("localhost");
	private JTextField textePort = new JTextField("5555");
	private JLabel labelLogin = new JLabel("Log In");
	private JLabel labelHote = new JLabel("Host Name");
	private JLabel labelPort = new JLabel("Port Number");
	private String login;
	private String hote;

	public ClientWindow(ClientUI f) {
		master=f;
		setTitle("Login");
		setSize(300,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		texteLogin.setPreferredSize(new Dimension(200, 20));
		texteHote.setPreferredSize(new Dimension(200, 20));
		texteLogin.addKeyListener(this);
		texteHote.addKeyListener(this);
		textePort.addKeyListener(this);

		
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.LINE_AXIS));
		panelHote.setLayout(new BoxLayout(panelHote, BoxLayout.LINE_AXIS));
		panelBouton.setLayout(new BoxLayout(panelBouton, BoxLayout.LINE_AXIS));
		panelPort.setLayout(new BoxLayout(panelPort, BoxLayout.LINE_AXIS));
		
		boutonValid.addActionListener(this);
		boutonValid.setBackground(Color.GREEN);
		boutonQuit.addActionListener(this);
		boutonQuit.setBackground(Color.RED);
		boutonQuit.addActionListener(new ActionListener() {
			
			
		@Override
		public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panelLogin.add(labelLogin);
		panelLogin.add(texteLogin);
		panelHote.add(labelHote);
		panelHote.add(texteHote);
		panelPort.add(labelPort);
		panelPort.add(textePort);
		
		panelBouton.add(boutonValid);
		panelBouton.add(boutonQuit);
		
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
		conteneur.add(panelLogin);
		conteneur.add(panelHote);
		conteneur.add(panelPort);
		conteneur.add(panelBouton);
		setContentPane(conteneur);
		pack();
		setVisible(true);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==boutonValid) {
			master.client.handleMessageFromClientUI("#setport "+textePort.getText());
			master.client.handleMessageFromClientUI("#sethost "+texteHote.getText());
			master.login=texteLogin.getText();
			master.usr.setText(master.login);
			master.setVisible(true);
			setVisible(false);
			master.client.handleMessageFromClientUI("#login "+texteLogin.getText());
			login = texteLogin.getText();
			hote = texteHote.getText();
		}
		this.setVisible(false);
	}
	
	public String getHote(){
		System.out.println(hote);
		return hote;
	}
	
	public String getLogin() {
		System.out.println(login);
		return login;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			master.client.handleMessageFromClientUI("#setport "+textePort.getText());
			master.client.handleMessageFromClientUI("#sethost "+texteHote.getText());
			master.login=texteLogin.getText();
			master.usr.setText(master.login);
			master.setVisible(true);
			setVisible(false);
			master.client.handleMessageFromClientUI("#login "+texteLogin.getText());
			login = texteLogin.getText();
			hote = texteHote.getText();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

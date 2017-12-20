import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import client.*;
import common.*;

public class ClientUI extends JFrame implements ChatIF, ActionListener{
	
	final public static int DEFAULT_PORT = 5555;
	
	ChatClient client;
	
	String login;
	
	private JPanel conteneur = new JPanel();
	private JPanel listeCoPanel = new JPanel();
	private JPanel chatPanel = new JPanel();
	private JPanel messagePanel = new JPanel();
	private JLabel titreListeCoLabel = new JLabel("Who's connected");
	private JLabel messageLabel = new JLabel("Chat");
	private JTextField champsTextChat = new JTextField();
	private JButton ValidMessageBouton = new JButton("Send");
	private JComboBox choixCommande = new JComboBox();
	
	
	public ClientUI(String login1, String host, String port) {
		try 
		{
			int port2=Integer.parseInt(port);
			System.out.println(port2);
			client= new ChatClient(host, port2, this);
			this.login=login1;
			client.handleMessageFromClientUI("#login "+login);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
		JFrame fenetre = new JFrame();
		setTitle("Chat");
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(conteneur);
		conteneur.setLayout(new BorderLayout());
		champsTextChat.setPreferredSize(new Dimension(400, 20));
		messagePanel.setPreferredSize(new Dimension(400,500));
		messagePanel.add(messageLabel);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		messagePanel.setBorder(blackline);
		listeCoPanel.setLayout(new BoxLayout(listeCoPanel, BoxLayout.PAGE_AXIS));
		chatPanel.add(choixCommande);
		chatPanel.add(champsTextChat);
		chatPanel.add(ValidMessageBouton);
		ValidMessageBouton.addActionListener(this);
		listeCoPanel.add(titreListeCoLabel);
		JLabel user = new JLabel("-"+login);
		listeCoPanel.add(user);
		choixCommande.addItem("sethost");
		choixCommande.addItem("quit");
		choixCommande.addItem("logoff");
		choixCommande.addItem("sethost");
		choixCommande.addItem("setport");
		choixCommande.addItem("login");
		choixCommande.addItem("setport");
		conteneur.add(listeCoPanel,BorderLayout.WEST);
		conteneur.add(messagePanel, BorderLayout.CENTER);
		conteneur.add(chatPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ValidMessageBouton) {
			client.handleMessageFromClientUI(champsTextChat.getText());
		}
	}

	@Override
	public void display(String message) {
		JLabel messageRecu = new JLabel(message);
		messagePanel.add(messageRecu);
	}
	
	public static void main(String[] args)
	{
		ClientUI clientWindow = new ClientUI("Anonymous","localhost", ""+DEFAULT_PORT);
		ClientWindow loginWindow = new ClientWindow();
	}
}

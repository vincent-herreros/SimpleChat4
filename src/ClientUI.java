import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JTextPane conversation = new JTextPane();
	private JScrollPane ensembleMessage ;
	private JTextField champsTextChat = new JTextField();
	private JButton ValidMessageBouton = new JButton("Send");
	private JComboBox<String> choixCommande = new JComboBox<String>();
	
	
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
		
		setTitle("Chat");
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		conteneur.setLayout(new BorderLayout());
		
		JLabel user = new JLabel("-"+login);
		
		ValidMessageBouton.addActionListener(this);
		
		champsTextChat.setPreferredSize(new Dimension(400, 20));
		
		ensembleMessage = new JScrollPane(conversation);
		
		/*messagePanel.setPreferredSize(new Dimension(400,500));
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.PAGE_AXIS));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		messagePanel.setBorder(blackline);
		messagePanel.add(ensembleMessage);*/
		
		listeCoPanel.setLayout(new BoxLayout(listeCoPanel, BoxLayout.PAGE_AXIS));
		listeCoPanel.add(titreListeCoLabel);
		listeCoPanel.add(user);

		chatPanel.add(choixCommande);
		chatPanel.add(champsTextChat);
		chatPanel.add(ValidMessageBouton);
		
		choixCommande.addItem("gethost");
		choixCommande.addItem("getport");
		choixCommande.addItem("quit");
		choixCommande.addItem("logoff");
		choixCommande.addItem("sethost");
		choixCommande.addItem("setport");
		choixCommande.addItem("login");
		choixCommande.addActionListener(this);
		
		conteneur.add(listeCoPanel,BorderLayout.WEST);
		conteneur.add(ensembleMessage, BorderLayout.CENTER);
		conteneur.add(chatPanel, BorderLayout.SOUTH);
		
		setContentPane(conteneur);

		setVisible(true);
		conversation.setText("hello");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ValidMessageBouton) {
			client.handleMessageFromClientUI(champsTextChat.getText());
		}
		if(e.getSource()==choixCommande) {
			if(choixCommande.getSelectedItem()=="getport") {
				client.handleMessageFromClientUI("#getport");
			}
		}
	}

	@Override
	public void display(String message) {
		String t=""+conversation.getText();
		System.out.println(t);
		conversation.setText("yeye");
	}
	
	public static void main(String[] args)
	{
		ClientUI clientWindow = new ClientUI("Anonymous","localhost", ""+DEFAULT_PORT);
	}
}

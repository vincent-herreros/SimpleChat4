import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.*;
import common.*;
import server.EchoServer;

public class ServerUI extends JFrame implements ChatIF, ActionListener, KeyListener{
	
	final public static int DEFAULT_PORT = 5555;
	
	EchoServer server;
	
	String login = "server";
	
	JLabel usr ;
	
	private JPanel conteneur = new JPanel();
	private JPanel listeCoPanel = new JPanel();
	private JPanel chatPanel = new JPanel();
	private JLabel titreListeCoLabel = new JLabel("Who's connected");
	private JTextPane conversation = new JTextPane();
	private JScrollPane ensembleMessage ;
	private JTextField champsTextChat = new JTextField();
	private JButton ValidMessageBouton = new JButton("Send");
	private JButton quitterBouton = new JButton("Quitter");
	private JComboBox<String> choixCommande = new JComboBox<String>();
	private JScrollBar scroll;
	
	
	public ServerUI(int port)
		{
			try 
			{
				server = new EchoServer(port, this);
			} 
			catch (Exception ex) 
			{
				System.out.println("ERROR - Could not listen for clients!");
			}	
		
		setTitle("Chat");
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		conteneur.setLayout(new BorderLayout());
		
		usr = new JLabel("-"+login);
		
		ValidMessageBouton.addActionListener(this);
		
		quitterBouton.addActionListener(this);
		
		champsTextChat.addKeyListener(this);
		
		champsTextChat.setPreferredSize(new Dimension(300, 20));
		
		ensembleMessage = new JScrollPane(conversation);
		scroll=ensembleMessage.getVerticalScrollBar();
		
		listeCoPanel.setLayout(new BoxLayout(listeCoPanel, BoxLayout.PAGE_AXIS));
		listeCoPanel.add(titreListeCoLabel);
		listeCoPanel.add(usr);

		chatPanel.add(choixCommande);
		chatPanel.add(champsTextChat);
		chatPanel.add(ValidMessageBouton);
		chatPanel.add(quitterBouton);
		
		choixCommande.addItem("gethost");
		choixCommande.addItem("getport");
		choixCommande.addItem("logoff");
		choixCommande.addItem("login");
		choixCommande.addActionListener(this);
		conteneur.add(listeCoPanel,BorderLayout.WEST);
		conteneur.add(ensembleMessage, BorderLayout.CENTER);
		conteneur.add(chatPanel, BorderLayout.SOUTH);
		
		setContentPane(conteneur);
		
		server.handleMessageFromServerUI("#login "+login);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ValidMessageBouton) {
			server.handleMessageFromServerUI(champsTextChat.getText());
			champsTextChat.setText("");
		}
		else if(e.getSource()==quitterBouton) {
			server.handleMessageFromServerUI("#quit");
		}
		else if(e.getSource()==choixCommande) {
			if(choixCommande.getSelectedItem()=="getport") {
				server.handleMessageFromServerUI("#getport");
			}
			else if(choixCommande.getSelectedItem()=="gethost") {
				server.handleMessageFromServerUI("#gethost");
			}
			else if(choixCommande.getSelectedItem()=="logoff") {
				server.handleMessageFromServerUI("#logoff");
			}
			else if(choixCommande.getSelectedItem()=="login") {
				//new ClientWindow(this);
				setVisible(false);
			}
		}
	}

	@Override
	public void display(String message) {
		String t;
		if(message.contains("["+login+"]")){
			t=""+conversation.getText();
			conversation.setText(t+"\n>"+message.substring(login.length()+2));
		}
		else if(message.contains("[console]") || message.contains("[server]")){
			t=""+conversation.getText();
			conversation.setText(t+"\n"+message);
		}
		else {
			t=""+conversation.getText();
			conversation.setText(t+"\n"+message.substring(1, message.indexOf("]"))+" >"+message.substring(message.indexOf("]")+1));
		}
		scroll.setValue(scroll.getMaximum());
	}
	
	public static void main(String[] args)
	{
		ServerUI serverWindow = new ServerUI(DEFAULT_PORT);
		//clientWindow.client.handleMessageFromClientUI("#login matthieu");
		//ClientWindow loginWindow = new ClientWindow();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			server.handleMessageFromServerUI(champsTextChat.getText());
			champsTextChat.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
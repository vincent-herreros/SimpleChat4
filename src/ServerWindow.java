
import javax.swing.*;

import common.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;

import client.*;
import common.*;


public class ServerWindow extends JFrame implements ActionListener{
	
	ServerUI master;
	
	private JButton boutonValid = new JButton("Valider");
	private JButton boutonQuit = new JButton("Quit");
	private JPanel conteneur = new JPanel();
	private JPanel panelPort = new JPanel();
	private JPanel panelBouton = new JPanel();
	private JTextField textePort = new JTextField("5555");
	private JLabel labelPort = new JLabel("Port Number");

	public ServerWindow(ServerUI f) {
		master=f;
		setTitle("Config");
		setSize(300,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
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
		panelPort.add(labelPort);
		panelPort.add(textePort);
		
		panelBouton.add(boutonValid);
		panelBouton.add(boutonQuit);
		
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
		conteneur.add(panelPort);
		conteneur.add(panelBouton);
		setContentPane(conteneur);
		pack();
		setVisible(true);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==boutonValid) {
			master.server.handleMessageFromServerUI("#setport "+textePort.getText());
			master.setVisible(true);
			setVisible(false);
		}
		this.setVisible(false);
	}

}

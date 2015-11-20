package skj.raf.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JLabel _ip;
	private JLabel _port;
	private JLabel _status;
	private JLabel _selected;
	private JLabel _current;
	private JLabel _speed;
	private JLabel _total;
	
	public MainFrame(){
		super("Client v.0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50,50);
		
		setLayout(new BorderLayout());
		
		// Creates menu
		
		JPanel menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(200, 300));
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton connect = new JButton("Connect");
		connect.setAlignmentX(CENTER_ALIGNMENT);
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: connection to server
			}
		});
		menuPanel.add(connect);
		
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton disconnect = new JButton("Disconnect");
		disconnect.setAlignmentX(CENTER_ALIGNMENT);
		disconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: disconnection
			}
		});
		menuPanel.add(disconnect);
		
		menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton ip = new JButton("Change IP");
		ip.setAlignmentX(CENTER_ALIGNMENT);
		ip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: ip change
			}
		});
		menuPanel.add(ip);
		
		menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton port = new JButton("Change PORT");
		port.setAlignmentX(CENTER_ALIGNMENT);
		port.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: port change
			}
		});
		menuPanel.add(port);
		
		menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JButton select = new JButton("Select file/folder");
		select.setAlignmentX(CENTER_ALIGNMENT);
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: selection
			}
		});
		menuPanel.add(select);
		
		menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JButton end = new JButton("End");
		end.setAlignmentX(CENTER_ALIGNMENT);
		end.setAlignmentY(BOTTOM_ALIGNMENT);
		end.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// To-Do: end
			}
		});
		menuPanel.add(end);
		
		add(menuPanel, BorderLayout.WEST);
		
		// Creates body
		
		JPanel bodyPanel = new JPanel();
		bodyPanel.setPreferredSize(new Dimension(300, 300));
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));

		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_ip = new JLabel("IP: ");
		_ip.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_ip);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_port = new JLabel("PORT: ");
		_port.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_port);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_status = new JLabel("Status: ");
		_status.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_status);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_selected = new JLabel("Selected: ");
		_selected.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_selected);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_current = new JLabel("Current: ");
		_current.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_current);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		_speed = new JLabel("Speed: ");
		_speed.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_speed);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		_total = new JLabel("Total: ");
		_total.setAlignmentX(LEFT_ALIGNMENT);
		bodyPanel.add(_total);
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		add(bodyPanel, BorderLayout.CENTER);
	}
	
	public void bootstrap() {
		pack();
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}
}
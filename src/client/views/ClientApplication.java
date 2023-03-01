package client.views;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;

public class ClientApplication extends JFrame {
	// (0) 소켓, gson 선언
	private Socket socket;
	private Gson gson;
	
	private JPanel mainPanel;
	
	 // (3-1)
	private CardLayout mainCard;
	
	private JTextField usernameField;
	private JTextField sendMessageField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApplication frame = new ClientApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientApplication() {
		
		// (8) init 소켓연결하고 접속오류 예외처리 접속실패하였을경우 강제종료
		gson = new Gson();
		try {
			socket = new Socket("127.0.0.1", 9090);
			
		}catch (UnknownHostException e1) {
			e1.printStackTrace();
			
		}catch (ConnectException e1) {
			JOptionPane.showMessageDialog(this, 
					"서버에 접속 실해"
					, "접속오류", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		// Frame set (1)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		
		// Panel (2)
		mainPanel = new JPanel();
		JPanel loginPanel = new JPanel();
		JPanel roomListPanel = new JPanel();
		JPanel roomPanel = new JPanel();
		
		// Layout (3)
		mainCard = new CardLayout();
		mainPanel.setLayout(mainCard); // Layout전역으로 빼고 내용 변경
		loginPanel.setLayout(null);
		roomListPanel.setLayout(null);
		roomPanel.setLayout(null);

		// Panel set (4)
		setContentPane(mainPanel);
		mainPanel.add(loginPanel, "loginPanel");
		mainPanel.add(roomListPanel, "roomListPanel");
		mainPanel.add(roomPanel, "roomPanel");
		
		//Login panel (5)
		JButton enterButton = new JButton("접속하기");

		usernameField = new JTextField();
		usernameField.setBounds(91, 432, 262, 60);
		loginPanel.add(usernameField);
		usernameField.setColumns(10);
		enterButton.setBounds(91, 502, 262, 37);
		loginPanel.add(enterButton);
		
		// RoomListPanel (6)
		JScrollPane roomListScroll = new JScrollPane();
		roomListScroll.setBounds(106, 0, 350, 753);
		roomListPanel.add(roomListScroll);
		JList roomList = new JList();
		roomListScroll.setViewportView(roomList);
		JButton createButton = new JButton("방 생성");
		createButton.setBounds(0, 90, 105, 92);
		roomListPanel.add(createButton);
		
		// RoomPanel (7)
		JScrollPane joinUserListScroll = new JScrollPane();
		joinUserListScroll.setBounds(0, 0, 325, 109);
		roomPanel.add(joinUserListScroll);
		
		JList joinUserList = new JList();
		joinUserListScroll.setViewportView(joinUserList);
		
		JButton roomExitButton = new JButton("나가기");
		roomExitButton.setBounds(337, 0, 119, 109);
		roomPanel.add(roomExitButton);
		
		JScrollPane chattingContensScroll = new JScrollPane();
		chattingContensScroll.setBounds(0, 115, 456, 551);
		roomPanel.add(chattingContensScroll);
		
		JTextArea chatContens = new JTextArea();
		chattingContensScroll.setViewportView(chatContens);
		
		sendMessageField = new JTextField();
		sendMessageField.setBounds(0, 676, 350, 77);
		roomPanel.add(sendMessageField);
		sendMessageField.setColumns(10);
		
		JButton sendButton = new JButton("전송");
		sendButton.setBounds(362, 676, 95, 77);
		roomPanel.add(sendButton);
	}
}

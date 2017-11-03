package testApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

public class Main extends JFrame {

	private JPanel contentPane;
	private JList<String> list;
	private JTextArea textArea;
	private JTextField teamTextField;
	private ArrayList<String> Teams;
	private ArrayList<String> Matches, AwayMatches;
	private int noOfTeams;
	private int rounds;
	private int matches;
	private int matchno;
	private DefaultListModel<String> dm;
	private JButton btnShuffle;
	private JTextField leaguetitleTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setTitle("YCFA League Fixtures Generator");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 16));
		textArea.setEditable(false);
		textArea.setBounds(344, 128, 364, 302);
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setBounds(229, 132, 479, 302);
		contentPane.add(jsp);
		
		list = new JList<String>();
		list.setToolTipText("Right click to delete selected team");
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setFont(new Font("Tahoma", Font.BOLD, 12));
		list.setBounds(31, 132, 159, 249);
		dm = new DefaultListModel<>();
		list.setModel(dm);
		list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(arg0)) {
					list.setSelectedIndex(list.locationToIndex(arg0.getPoint()));
					JPopupMenu menu = new JPopupMenu();
	                JMenuItem item = new JMenuItem("Delete");
	                item.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	Teams.remove(list.getSelectedIndex());
	                        dm.removeElementAt(list.getSelectedIndex());
	                    }
	                });
	                menu.add(item);
	                menu.show(list, arg0.getPoint().x, arg0.getPoint().y);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		contentPane.add(list);
		
		JButton btnGenerateFixtures = new JButton("Generate Fixtures");
		btnGenerateFixtures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Teams.size() > 2) {
					createAlgorithm();
					createAwayAlgorithm();
				} else {
					JOptionPane.showMessageDialog(Main.this, "You need at least 3 teams to generate fixtures");
				}
			}
		});
		btnGenerateFixtures.setBounds(31, 404, 159, 30);
		contentPane.add(btnGenerateFixtures);
		
		JButton btnAdd = new JButton("Add Team");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String team = teamTextField.getText().toString();
				if (team.length()>0) {
					dm.addElement(team);
					Teams.add(team);
					teamTextField.setText("");
					teamTextField.requestFocus();
				}
			}
		});
		btnAdd.setBounds(133, 100, 57, 23);
		contentPane.add(btnAdd);
		
		teamTextField = new JTextField();
		teamTextField.setBounds(31, 99, 92, 22);
		contentPane.add(teamTextField);
		
		btnShuffle = new JButton("Shuffle");
		btnShuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shuffle();
			}
		});
		btnShuffle.setBounds(619, 453, 89, 23);
		contentPane.add(btnShuffle);
		
		leaguetitleTextField = new JTextField();
		leaguetitleTextField.setBounds(133, 35, 445, 30);
		contentPane.add(leaguetitleTextField);
		leaguetitleTextField.setColumns(10);
		
		JLabel lblLeagueTitle = new JLabel("League Title", SwingConstants.RIGHT);
		lblLeagueTitle.setToolTipText("");
		lblLeagueTitle.setBounds(31, 35, 92, 30);
		contentPane.add(lblLeagueTitle);
		
		Teams = new ArrayList<String>();
	}
	
	private void createAlgorithm() {
		textArea.setText("");
		textArea.append("\t" + leaguetitleTextField.getText().toString() + "\n");
		Matches = new ArrayList<String>();
		textArea.append("\t Round 1\n");
		noOfTeams = Teams.size();
		matchno = 0;
		if (noOfTeams%2 == 1) {
			noOfTeams++;
			Teams.add("Ghost");
		}
		int[] numbers = new int[noOfTeams];
		rounds = noOfTeams - 1;
		matches = noOfTeams / 2;
		System.out.println("Array of " + numbers.length + " created.");
		int j = 0;
		for (int i=0; i<(rounds); i++) {
			textArea.append("\tMatch Day " + (i+1) + "\n");
			if (i==0) {
				for (j=0; j<(noOfTeams); j++) {
					numbers[j] = j + 1;
				}
			} else {
				for (j=0; j<(noOfTeams-1); j++) {
					numbers[j] = numbers[j] + (matches);
					if (numbers[j] > rounds)
						numbers[j] = numbers[j] - rounds;
				}
			}
			for (int n=0; n<(matches); n++) {
				String h = Teams.get(numbers[n]-1);
				String a = Teams.get(numbers[noOfTeams-n-1]-1);
				
				if (h.equals("Ghost") || a.equals("Ghost"))
				{
					
				} else {
					matchno++;
					System.out.println(h + " vs " + a);
					textArea.append("\t" + h + " vs " + a + "\n");
					Matches.add("\t" + h + " vs " + a + "\n");
				}
			}
			
		}
	}
	
	private void Shuffle() {
		Collections.shuffle(Teams);
		createAlgorithm();
		createAwayAlgorithm();
	}
	
	private void createAwayAlgorithm() {
		AwayMatches = new ArrayList<String>();
		textArea.append("\n\t Round 2\n");
		noOfTeams = Teams.size();
		matchno = 0;
		if (noOfTeams%2 == 1) {
			noOfTeams++;
			Teams.add("Ghost");
		}
		int[] numbers = new int[noOfTeams];
		rounds = noOfTeams - 1;
		matches = noOfTeams / 2;
		System.out.println("Array of " + numbers.length + " created.");
		int j = 0;
		for (int i=0; i<(rounds); i++) {
			String MatchDay = "";
			if (i==0) {
				for (j=0; j<(noOfTeams); j++) {
					numbers[j] = j + 1;
				}
			} else {
				for (j=0; j<(noOfTeams-1); j++) {
					numbers[j] = numbers[j] + (matches);
					if (numbers[j] > rounds)
						numbers[j] = numbers[j] - rounds;
				}
			}
			for (int n=0; n<(matches); n++) {
				String a = Teams.get(numbers[n]-1);
				String h = Teams.get(numbers[noOfTeams-n-1]-1);
				
				if (h.equals("Ghost") || a.equals("Ghost"))
				{
					
				} else {
					System.out.println(h + " vs " + a);
					MatchDay += ("\t" + h + " vs " + a + "\n");
				}
			}
			AwayMatches.add(MatchDay);
		}
		
		Collections.shuffle(AwayMatches);
		int matchday = 0;
		for (int i=0; i<AwayMatches.size(); i++) {
			//matchno++;
			matchday++;
			textArea.append("\tMatch Day " + matchday + "\n");
			textArea.append(AwayMatches.get(i));
		}
	}
}

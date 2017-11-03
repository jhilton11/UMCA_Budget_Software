package testApps;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class TeamsPanel extends JDialog {
	ArrayList<JTextField> textField = new ArrayList<JTextField>();
	ArrayList<String> TeamsArray, HomeTeams, AwayTeams;
	int teams;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public TeamsPanel(int noOfTeams) {
		teams = noOfTeams;
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						createTeams();
						createAlgorithm();
						for (int i=0; i<teams; i++) 
							textField.get(i).setText("");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		for (int i=0; i<teams; i++) {
			JTextField tf = new JTextField();
			contentPanel.add(new JLabel("Team " + (i+1)));
			contentPanel.add(tf);
			textField.add(tf);
		}
	}
	
	private void createTeams() {
		TeamsArray = new ArrayList<String>();
		HomeTeams = new ArrayList<String>();
		AwayTeams = new ArrayList<String>();
		for (int i=0; i<teams; i++) {
			String t = textField.get(i).getText().toString();
			TeamsArray.add(t);
		}
		if (teams%2 == 1){
			TeamsArray.add("Ghost");
			teams++;
			}
		System.out.println(TeamsArray.size() + " teams added.");
	}
	
	private void createAlgorithm() {
		int[] numbers = new int[teams];
		int rounds = teams - 1;
		int matches = teams / 2;
		System.out.println("Array of " + numbers.length + " created.");
		int j = 0;
		for (int i=0; i<(rounds); i++) {
			if (i==0) {
				for (j=0; j<(teams); j++) {
					numbers[j] = j + 1;
				}
			} else {
				for (j=0; j<(teams-1); j++) {
					numbers[j] = numbers[j] + (matches);
					if (numbers[j] > rounds)
						numbers[j] = numbers[j] - rounds;
				}
			}
			for (int n=0; n<(matches); n++) {
				String h = TeamsArray.get(numbers[n]-1);
				String a = TeamsArray.get(numbers[teams-n-1]-1);
				
				if (h.equals("Ghost") || a.equals("Ghost"))
				{
					
				} else {
				System.out.println(h + " vs " + a);
				HomeTeams.add(h);
				AwayTeams.add(a);
				}
			}
		}
	}

}

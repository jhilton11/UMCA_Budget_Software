package com.iconnect;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	double[] actual_2016, app_2017, actual_2017, percent, prop_2018;
	double grandTotal;
	private JButton btnCalculate;
	private static DecimalFormat df2 = new DecimalFormat(".##");
	private String[] particulars = new String[] {
			"Salaries", "Allowances", "Leave Bonus", "Rent and Accommodation", "Office Running",
			"Tithes to HQ", "Mission Sunday", "UMCA Day", "UMCATC Day", "Traveling",
			"Meeting", "Bank Charges", "Levies/Dues", "Annual Retreat", "Missions",
			"Donations", "Loans", "Honourarium/Benevolence", "Refund", "Interest on Savings",
			"Dist Sunday", "Special Receipts", "Income Tithe", "Income Assessment", "Total"
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		createGUI();
		getValues();
	}

	private void createGUI() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 10));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{new Integer(1), "Salaries", null, null, null, null, null},
				{new Integer(2), "Allowances", null, null, null, null, null},
				{new Integer(3), "Leave Bonuses", null, null, null, null, null},
				{new Integer(4), "Rent and Accommodation", null, null, null, null, null},
				{new Integer(5), "Office Running", null, null, null, null, null},
				{new Integer(6), "Tithes to HQ", null, null, null, null, null},
				{new Integer(7), "Missions Sunday", null, null, null, null, null},
				{new Integer(8), "UMCA Day", null, null, null, null, null},
				{new Integer(9), "UMCATC Day", null, null, null, null, null},
				{new Integer(10), "Traveling", null, null, null, null, null},
				{new Integer(11), "Meetings", null, null, null, null, null},
				{new Integer(12), "Bank Charges", null, null, null, null, null},
				{new Integer(13), "Levies / Dues", null, null, null, null, null},
				{new Integer(14), "Annual Retreat", null, null, null, null, null},
				{new Integer(15), "Missions", null, null, null, null, null},
				{new Integer(16), "Donations", null, null, null, null, null},
				{new Integer(17), "Loans", null, null, null, null, null},
				{new Integer(18), "Honorarium / Benevolence", null, null, null, null, null},
				{new Integer(19), "Refund", null, null, null, null, null},
				{new Integer(20), "Interest on Savings", null, null, null, null, null},
				{new Integer(21), "Dist Sunday", null, null, null, null, null},
				{new Integer(22), "Special Receipts", null, null, null, null, null},
				{new Integer(23), "Income Tithe", null, null, null, null, null},
				{new Integer(24), "Income Assessment", null, null, null, null, null},
				{null, "Grand Total", null, null, null, null, null},
			},
			new String[] {
				"S/N", "Particulars", "Actual 2016", "Approved 2017", "Actual 2017", "%", "Proposed 2018"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Double.class, Double.class, Double.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(41);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(2).setPreferredWidth(132);
		table.getColumnModel().getColumn(3).setPreferredWidth(137);
		table.getColumnModel().getColumn(4).setPreferredWidth(117);
		table.getColumnModel().getColumn(5).setPreferredWidth(65);
		table.getColumnModel().getColumn(6).setPreferredWidth(111);
		
		table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				getValues();
				calculateValues();
			}
		});;
		
		for (int i=0; i<table.getModel().getRowCount(); i++) {
			table.setRowHeight(i, 25);
			table.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		}

		String Title = " UMCA Lokoja District\n"
				+ " Financial Statement 2017/ Budget 2018";
		JLabel lblNewLabel = new JLabel(Title);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		btnCalculate = new JButton("Export");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getValues();
				calculateValues();
				exportToWord();
			}
		});
		panel.add(btnCalculate, BorderLayout.SOUTH);
	}
	
	private void getValues() {
		System.out.println(particulars.length + " and " + table.getRowCount());
		int rowCount = table.getRowCount();
		actual_2016 = new double[rowCount];
		app_2017 = new double[rowCount];
		actual_2017 = new double[rowCount];
		percent = new double[rowCount];
		prop_2018 = new double[rowCount];
		
		for (int i=0; i<rowCount; i++) {
			if (table.getModel().getValueAt(i, 2) != null)
				actual_2016[i] = (double) table.getModel().getValueAt(i, 2);
			else
				actual_2016[i] = 0;
			
			if (table.getModel().getValueAt(i, 3) != null)
				app_2017[i] = (double) table.getModel().getValueAt(i, 3);
			else
				app_2017[i] = 0;
			
			if (table.getModel().getValueAt(i, 4) != null)
				actual_2017[i] = (double) table.getModel().getValueAt(i, 4);
			else
				actual_2017[i] = 0;
		}
	}
	
	private void calculateValues() {
		for (int i=0; i<table.getRowCount(); i++) {
			percent[i] = (actual_2017[i]/app_2017[i]) * 100;
			
			
			if (actual_2017[i] >= app_2017[i]) {
				prop_2018[i] = actual_2017[i] * 1.1;
			} else {
				prop_2018[i] = app_2017[i];
			}
		}
		
		grandTotal = 0;
		for (int i=0; i<table.getRowCount(); i++) {
			grandTotal = grandTotal + prop_2018[i];
		}
		
		System.out.println("Approved 2017 \t Actual 2017");
		for (int i=0; i<table.getRowCount(); i++) {
			System.out.println(app_2017[i] + "\t " + actual_2017[i]);
		}
		
		for (int i=0; i<table.getRowCount(); i++) {
			table.setValueAt(new String(df2.format(percent[i])), i, 5);
			table.setValueAt(new Double(prop_2018[i]), i, 6);
		}
		table.setValueAt(grandTotal, table.getRowCount()-1, 6);
	}
	
	private void exportToWord() {
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun header = paragraph.createRun();
		header.setText("UMCA Lokoja District");
		header.setBold(true);
		header.setFontSize(16);
		
		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun header1 = paragraph1.createRun();
		header1.setText("\nFinancial Statement 2017 / Budget 2018");
		header1.setBold(true);
		header1.setFontSize(16);
		File file = null;
		String fileName;
			
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("DOC FILES", "doc", "docx");
		chooser.setFileFilter(filter);
		
		int selectedDirectory = chooser.showSaveDialog(this);
		
		if (selectedDirectory == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			fileName = file.toString();
			if (!fileName.endsWith(".docx")){
				fileName += ".docx";
				//file = new File(fileName);
				System.out.println(fileName);
			}
			
			try {
				if (file != null) {
					FileOutputStream out = new FileOutputStream(file);
					
					XWPFTable wordTable = document.createTable();
					
					//Write to the first row
					XWPFTableRow headerRow = wordTable.getRow(0);
					headerRow.getCell(0).setText("S/N");
					headerRow.addNewTableCell().setText("Particulars");
					headerRow.addNewTableCell().setText("Actual 2016");
					headerRow.addNewTableCell().setText("Approved 2017");
					headerRow.addNewTableCell().setText("Actual 2018");
					headerRow.addNewTableCell().setText("%");
					headerRow.addNewTableCell().setText("Proposed 2018");
					
					//write to other rows
					for (int i=1; i<table.getRowCount(); i++) {
						XWPFTableRow otherRows = wordTable.createRow();
						otherRows.getCell(0).setText(i + "");
						otherRows.getCell(1).setText(particulars[i-1]);
						otherRows.getCell(2).setText(actual_2016[i-1] + "");
						otherRows.getCell(3).setText(app_2017[i-1] + "");
						otherRows.getCell(4).setText(actual_2017[i-1] + "");
						otherRows.getCell(5).setText(df2.format(percent[i-1]));
						otherRows.getCell(6).setText(prop_2018[i-1] + "");
					}
					
					document.write(out);
					out.close();
					
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}

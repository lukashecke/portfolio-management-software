package Views;

import Business.DBConnection;
import Models.Asset;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class InvestmentsWindow extends BaseWindow {

	private Models.Asset asset;

	private Container container;

	private JPanel assetPanel;

	private JLabel message;
	private JLabel messageName;
	private JLabel messageShortName;
	private JLabel assetShortNameLabel;
	private JLabel placeHolder;
	private JLabel placeHolder1;
	private JLabel placeHolder2;

	private JTextField assetNameField;
	private JTextField assetShortNameField;

	private JButton submitButton;

	public InvestmentsWindow(Asset selectedAsset) {
		super();

		this.asset = selectedAsset;

		setComponents();
	}

	private void setComponents() {

		container = getContentPane();
		container.setLayout(new GridLayout(1,2));

		assetPanel = new JPanel();
		assetPanel.setLayout(new GridBagLayout());
		//assetNameField = new JTextField();

		message = new JLabel("Erzeugung einer neue Anlage");
		message.setFont(new Font("Courier", Font.BOLD, 28));

		placeHolder = new JLabel(" ");
		placeHolder.setFont(new Font("Courier", Font.BOLD, 120));

		placeHolder1 = new JLabel(" ");
		placeHolder1.setFont(new Font("Courier", Font.BOLD, 30));

		placeHolder2 = new JLabel(" ");
		placeHolder2.setFont(new Font("Courier", Font.BOLD, 30));

		messageName = new JLabel("Name");
		messageName.setFont(new Font("Courier", Font.PLAIN, 12));

		messageShortName = new JLabel("Abkürzung");
		messageShortName.setFont(new Font("Courier", Font.PLAIN, 12));

		assetNameField = new JTextField(asset.getName());
		assetShortNameField = new JTextField(asset.getShortName());
		submitButton = new JButton("SPEICHERN");

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;

		c.gridx = 0;
		c.gridy = 1;
		assetPanel.add(message, c);

		c.gridx = 0;
		c.gridy = 2;
		assetPanel.add(placeHolder, c);

		c.gridx = 0;
		c.gridy = 3;
		assetPanel.add(messageName, c);

		c.gridx = 0;
		c.gridy = 4;
		assetPanel.add(assetNameField, c);

		c.gridx = 0;
		c.gridy = 5;
		assetPanel.add(placeHolder1, c);

		c.gridx = 0;
		c.gridy = 6;
		assetPanel.add(messageShortName, c);

		c.gridx = 0;
		c.gridy = 7;
		assetPanel.add(assetShortNameField, c);

		c.gridx = 0;
		c.gridy = 8;
		assetPanel.add(placeHolder2, c);

		c.gridx = 0;
		c.gridy = 9;
		assetPanel.add(submitButton, c);

		{
			Object[][] data;
			data = DBConnection.getInstance().getAssetInvestments(asset.getId());
			DefaultTableModel tableModel = new DefaultTableModel(data, data[0]);

			JTable assetTable = new JTable(tableModel);

			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();

			tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			assetTable.setDefaultRenderer(Object.class, tableCellRenderer);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(assetTable, BorderLayout.CENTER);

			JScrollPane scrollPane = new JScrollPane(panel);

			// Zusammenbauen der Seite
			container.add(assetPanel, 0);
			container.add(scrollPane, 1);

		}
	}
}
package Views;

import Business.DBConnection;
import Models.Asset;
import Utils.Constants;
import Models.History;
import Models.Investment;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class AssetWindow extends BaseWindow {

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
	private JButton deleteButton;

	private JTable assetTable;

	/**
	 * Erzeugt die Komponenten für das InvestmentWindow.
	 * @author  Lukas Hecke
	 * @author  Namandeep Singh
	 */

	public AssetWindow(Asset selectedAsset) {
		super();

		this.asset = selectedAsset;

		setComponents();

		userValidationForComponents();
	}

	/**
	 * Setzt alle anzuzeigenden Komponenten im Fenster.
	 * @author  Namandeep Singh
	 */

	private void setComponents() {
		container = getContentPane();
		container.setLayout(new GridLayout(1,2));

		assetPanel = new JPanel();
		assetPanel.setLayout(new GridBagLayout());
		//assetNameField = new JTextField();

		message = new JLabel("Editieren einer Anlage ");
		message.setFont(Constants.MIDDLEFONT);

		placeHolder = new JLabel(" ");
		placeHolder.setFont(Constants.MIDDLELARGEFONT);

		placeHolder1 = new JLabel(" ");
		placeHolder1.setFont(Constants.MIDDLEFONT);

		placeHolder2 = new JLabel(" ");
		placeHolder2.setFont(Constants.MIDDLEFONT);

		messageName = new JLabel("Name");
		messageName.setFont(Constants.EXTRASMALLFONT);

		messageShortName = new JLabel("Abkürzung");
		messageShortName.setFont(Constants.EXTRASMALLFONT);

		assetNameField = new JTextField(asset.getName());
		assetShortNameField = new JTextField(asset.getShortName());
		submitButton = new JButton("SPEICHERN");

		// Anpassung aller Komponente im Grid
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
			// Es wird eine Tabelle erzeugt für die ansicht der Historie
			Object[][] data;
			data = Investment.getAssetInvestmentsPresentation(asset.getId());
			DefaultTableModel tableModel = new DefaultTableModel(data, data[0]);

			assetTable = new JTable(tableModel);

			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();

			tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			assetTable.setDefaultRenderer(Object.class, tableCellRenderer);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(assetTable, BorderLayout.CENTER);

			JPanel rightSide = new JPanel();
			rightSide.setLayout(new BorderLayout());
			JScrollPane scrollPane = new JScrollPane(panel);
			deleteButton = new JButton("Löschen");

			rightSide.add(scrollPane, BorderLayout.CENTER);
			rightSide.add(deleteButton, BorderLayout.SOUTH);
			// Zusammenbauen der Seite
			container.add(assetPanel, 0);
			container.add(rightSide, 1);

		}
	}

	private void userValidationForComponents() {
		deleteButton.setVisible(false);
		if (DBConnection.getInstance().isAdmin()) {
			assetNameField.setEnabled(true);
			assetShortNameField.setEnabled(true);
			assetTable.setEnabled(true);

			assetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ListSelectionModel selectionModel = assetTable.getSelectionModel();
			selectionModel.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					deleteButton.setVisible(true);
				}
			});

			submitButton.addActionListener((e) -> {
				asset.setName(assetNameField.getText());
				asset.setShortName(assetShortNameField.getText());
				Asset.updateAsset(asset.getId(), asset.getName(), asset.getShortName());
				refreshTitle();
				revalidate();
				repaint();
			});
			deleteButton.addActionListener((e) -> {
				int[] selectedRows;
				int investmentId = -1;

				selectedRows = assetTable.getSelectedRows();
				if (selectedRows.length > 0)
				{
					for (int i=0; i < 1; i++) {
						// get data from JTable
						TableModel tm = assetTable.getModel();
						investmentId = Integer.parseInt((String)tm.getValueAt(selectedRows[0],i));
					}
				}
				int historyId = History.getHistoryIdForInvestment(investmentId);

				// Reihenfolge wichtig, weil history nicht gelöscht werden kann, wenn investment noch darauf verweist
				DBConnection.getInstance().deleteInvestment(investmentId);
				History.deleteHistory(historyId);

				refreshAssetTable();
				assetTable.revalidate();
				assetTable.repaint();
				revalidate();
				repaint();
			});
		} else {
			assetNameField.setEnabled(false);
			assetShortNameField.setEnabled(false);
			assetTable.setEnabled(false);

			submitButton.setVisible(false);
		}
	}

	private void refreshAssetTable() {
		Object[][] data;
		data = Investment.getAssetInvestmentsPresentation(asset.getId());
		DefaultTableModel tableModel = new DefaultTableModel(data, data[0]);

		assetTable.setModel(tableModel);
	}

	private void refreshTitle() {
		setTitle(asset.getName() + " (" + asset.getShortName()+ ")");
	}
}
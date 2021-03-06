package Views;

import Formatter.DateLabelFormatter;
import Models.Asset;
import Models.Investment;
import Models.Platform;
import Utils.Constants;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

/**
 * Erzeugung der New Investments Fenster.
 * @author  Namandeep Singh
 */

public class NewInvestmentWindow extends BaseWindow {

	private JLabel message;
	private JLabel assetLabel;
	private JComboBox<Asset> assetComboBox;
	private JLabel platformLabel;
	private JComboBox<Platform> platformComboBox;
	private JLabel transactionFeeLabel;
	private JLabel purchasePriceLabel;
	private JLabel pricePerUnitLabel;
	private JLabel dateOfPurchaseLabel;
	private JTextField transactionFeeField;
	private JTextField purchasePriceField;
	private JTextField pricePerUnitField;

	private JButton submitButton;
	Container container;

	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;

	public NewInvestmentWindow() {
		super();
		setComponents();
	}

	private void setComponents() {
		message = new JLabel("Wählen Sie Ihre neue Investition");
		message.setFont(Constants.LARGEFONT);

		assetLabel = new JLabel("Asset");

		var assets = Models.Asset.getAssets();

		assetComboBox = new JComboBox<Asset>();
		for (Asset asset:assets) {
			assetComboBox.addItem(asset);
		}

		FocusListener focusListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!e.isTemporary()) {
					String content = purchasePriceField.getText();
					try {
						double parsedContent = Double.parseDouble(content);
						// wird nur bei erfolgreichem Parsen erreicht (z.B. nach korrektur)
						((JTextField)e.getComponent()).setBorder(BorderFactory.createLineBorder(Color.lightGray));
					} catch (NumberFormatException ex) {
						((JTextField)e.getComponent()).setBorder(BorderFactory.createLineBorder(Color.RED));
					}
				}
			}
		};

		platformLabel = new JLabel("Platform");
		var platforms = Platform.GetPlatforms();

		platformComboBox = new JComboBox<Platform>();
		for (Platform platform:platforms) {
			platformComboBox.addItem(platform);
		}

		purchasePriceLabel = new JLabel("Kaufpreis");
		purchasePriceField = new JTextField();
		purchasePriceField.addFocusListener(focusListener);

		//Combo box für Plattform

		pricePerUnitLabel = new JLabel("Preis pro Einheit");
		pricePerUnitField = new JTextField();
		pricePerUnitField.addFocusListener(focusListener);

		transactionFeeLabel = new JLabel("Transaktionsgebühr");
		transactionFeeField = new JTextField();
		transactionFeeField.addFocusListener(focusListener);

		dateOfPurchaseLabel = new JLabel("Kaufdatum");

		UtilDateModel model = new UtilDateModel();
		var now = LocalDateTime.now();
		model.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");

		datePanel = new JDatePanelImpl(model, p);
		datePanel.getModel().setDate(now.getYear(),now.getMonthValue(),now.getDayOfMonth());
		datePanel.getModel().setSelected(true);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


		submitButton = new JButton("SPEICHERN");
		submitButton.setFont(Constants.SMALLFONT);
		submitButton.addActionListener((e) -> {
			try {
				double purchasePrice = Double.parseDouble(purchasePriceField.getText());
				double pricePerUnit = Double.parseDouble(pricePerUnitField.getText());
				double transactionFee = Double.parseDouble(transactionFeeField.getText());

				Asset selectedAsset = (Asset)assetComboBox.getSelectedItem();
				Platform selectedPlatform = (Platform)platformComboBox.getSelectedItem();
				Date selectedDate = (Date)datePicker.getModel().getValue();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String formattedDateText = formatter.format(selectedDate);

				Investment.createNewInvestment(selectedAsset, selectedPlatform, "\""+formattedDateText+"\"", pricePerUnit, purchasePrice, transactionFee);

				JOptionPane.showMessageDialog(container, "Neues Investment erfolgreich gespeichert", "Anlegen erfolgreich", JOptionPane.INFORMATION_MESSAGE);
				// this.exit();
			} catch (NumberFormatException ex) {
				transactionFeeField.setBorder(BorderFactory.createLineBorder(Color.RED));
				JOptionPane.showMessageDialog(container, "Sie haben einen ungültigen Wert in eines der Zahlenfelder eingetragen", "Falsche Eingaben", JOptionPane.WARNING_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
				// Genauere Unterscheidung notwendig
				var exceptionHash = ex.getSQLState().hashCode();
				switch (exceptionHash) {
					// execute command denied to user...
					case 49560306 -> JOptionPane.showMessageDialog(container, "Sie haben keine Berechtigung, um diese Aktion durchzuführen. Bitte wenden Sie sich an Ihren Administrator", "Fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
					// Sonstige Fehler
					default -> JOptionPane.showMessageDialog(container, ex.getMessage(), "Schwerliegender Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		container = getContentPane();
		container.setLayout(null);

		setBounds();
		addComponents();


	}
	public void setBounds() {
		message.setBounds(50, 10, 800, 50);             // (vonLinksNachRechts , höhe , länge , breite)

		assetLabel.setBounds(50, 100, 100, 30);
		assetComboBox.setBounds(170, 100, 200, 30);

		platformLabel.setBounds(50, 150, 100, 30);
		platformComboBox.setBounds(170, 150, 200, 30);

		purchasePriceLabel.setBounds(50, 200, 100, 30);
		purchasePriceField.setBounds(170, 200, 200, 30);

		pricePerUnitLabel.setBounds(50, 250, 200, 30);
		pricePerUnitField.setBounds(170, 250, 200, 30);

		transactionFeeLabel.setBounds(50, 300, 200, 30);
		transactionFeeField.setBounds(170, 300, 200, 30);

		dateOfPurchaseLabel.setBounds(50, 350, 200, 30);
		datePicker.setBounds(170, 350, 200, 30);

		submitButton.setBounds(250, 415, 300, 50);


	}
	public void addComponents() {
		container.add(message);
		container.add(assetLabel);
		container.add(assetComboBox);
		container.add(platformLabel);
		container.add(platformComboBox);
		container.add(purchasePriceLabel);
		container.add(purchasePriceField);
		container.add(pricePerUnitLabel);
		container.add(pricePerUnitField);
		container.add(transactionFeeLabel);
		container.add(transactionFeeField);
		container.add(dateOfPurchaseLabel);
		//JDatePicker
		container.add(datePicker);
		container.add(submitButton);
	}

}

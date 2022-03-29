package Views;

import java.awt.Container;
import javax.swing.*;
import java.awt.Font;
import java.util.Properties;

import Formatter.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AddInvestmentWindow extends BaseWindow {

	private JLabel message;
	private JLabel assetLabel;
	private JComboBox<String> assetList;
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

	public AddInvestmentWindow() {
		super();
		setComponents();
		investmentFrame();
	}

	private void investmentFrame() {
		showWindow("Neue Investition", 900, 700);
	}


	private void setComponents() {
		message = new JLabel("Wählen Sie Ihre neue Investition");
		message.setFont(new Font("Courier", Font.BOLD, 40));

		assetLabel = new JLabel("Asset");
		assetList = new JComboBox<String>();
		assetList.addItem("Stocks");
		assetList.addItem("ETFs");
		assetList.addItem("Metals");

		purchasePriceLabel = new JLabel("Kaufpreis");
		purchasePriceField = new JTextField();

		pricePerUnitLabel = new JLabel("Preis pro Einheit");
		pricePerUnitField = new JTextField();

		transactionFeeLabel = new JLabel("Transaktionsgebühr");
		transactionFeeField = new JTextField();

		dateOfPurchaseLabel = new JLabel("Kaufdatum");

		UtilDateModel model = new UtilDateModel();
		model.setDate(2022, 01, 31);
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


		submitButton = new JButton("SPEICHERN");
		container = getContentPane();
		container.setLayout(null);

		setBounds();
		addComponents();


	}
	public void setBounds() {
		message.setBounds(50, 10, 800, 50);             //links , höhe , länge , breite

		assetLabel.setBounds(50, 100, 100, 30);
		assetList.setBounds(170, 100, 200, 30);

		purchasePriceLabel.setBounds(50, 160, 100, 30);
		purchasePriceField.setBounds(170, 160, 200, 30);

		pricePerUnitLabel.setBounds(50, 220, 200, 30);
		pricePerUnitField.setBounds(170, 220, 200, 30);

		transactionFeeLabel.setBounds(50, 280, 200, 30);
		transactionFeeField.setBounds(170, 280, 200, 30);

		dateOfPurchaseLabel.setBounds(50, 350, 200, 30);
		datePicker.setBounds(170, 350, 200, 30);

		submitButton.setBounds(250, 450, 350, 30);


	}
	public void addComponents() {
		container.add(message);
		container.add(assetLabel);
		container.add(assetList);
		container.add(purchasePriceLabel);
		container.add(purchasePriceField);
		container.add(pricePerUnitLabel);
		container.add(pricePerUnitField);
		container.add(transactionFeeLabel);
		container.add(transactionFeeField);
		container.add(dateOfPurchaseLabel);
		/*JDatePicker*/
		container.add(datePicker);
		container.add(submitButton);
	}

}

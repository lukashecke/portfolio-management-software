package Views;

import java.awt.Container;
import javax.swing.*;
import java.awt.Font;

public class CreateNewAssetWindow extends BaseWindow {

	private JLabel message;
	private JLabel assetLabel;
	private JComboBox<String> assetList;
	private JLabel assetNameLabel;
	private JLabel assetShortNameLabel;
	private JTextField assetNameField;
	private JTextField assetShortNameField;

	private JButton submitButton;
	Container container;

	public CreateNewAssetWindow() {
		super();
		setComponents();
		assetFrame();
	}

	private void assetFrame() {
	//	showWindow("Neue Anlage", 900, 700);
	}


	private void setComponents() {
		message = new JLabel("Erzeugung einer neue Anlage");
		message.setFont(new Font("Courier", Font.BOLD, 40));

		assetLabel = new JLabel("Anlageklasse");
		assetList = new JComboBox<String>();
		assetList.addItem("Aktien");
		assetList.addItem("ETFs");
		assetList.addItem("Krypto");
		assetList.addItem("Edelmetal");

		assetNameLabel = new JLabel("Name");
		assetNameField = new JTextField();

		assetShortNameLabel = new JLabel("Abkürzung");
		assetShortNameField = new JTextField();


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

		assetNameLabel.setBounds(50, 160, 100, 30);
		assetNameField.setBounds(170, 160, 200, 30);

		assetShortNameLabel.setBounds(50, 220, 200, 30);
		assetShortNameField.setBounds(170, 220, 200, 30);

		submitButton.setBounds(250, 450, 350, 30);


	}
	public void addComponents() {
		container.add(message);
		container.add(assetLabel);
		container.add(assetList);
		container.add(assetNameLabel);
		container.add(assetNameField);
		container.add(assetShortNameLabel);
		container.add(assetShortNameField);
		container.add(submitButton);
	}

}
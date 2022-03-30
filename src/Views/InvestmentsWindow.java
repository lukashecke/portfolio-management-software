package Views;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class InvestmentsWindow extends BaseWindow {

	public void investmentPane() {
		{
			String[] index = new String[]{"Investitionssumme", "Investitionsdatum"};
			JFrame frame = new JFrame("Investments");
			JLabel label = new JLabel("Bitcoin", JLabel.CENTER);
			Object[][] data = new Object[][]{
					{"Investitionssumme", "Investitionsdatum"},
					{"20 €", "2022.03.28"},
					{"60 €", "2022.03.09"},
					{"30 €", "2022.02.16"},
			};

			DefaultTableModel tableModel = new DefaultTableModel(data, index);

			JTable assetTable = new JTable(tableModel);

			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();

			tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			assetTable.setDefaultRenderer(Object.class, tableCellRenderer);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(assetTable, BorderLayout.CENTER);

			JScrollPane scrollPane = new JScrollPane(panel);

		//	showWindow("Neue Investition", 320, 200);
		}
	}

	public InvestmentsWindow() {
		super();
		investmentPane();
	}
}
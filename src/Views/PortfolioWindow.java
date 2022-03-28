package Views;

import javax.swing.*;
import java.awt.*;

public class PortfolioWindow extends BaseWindow {
    private Container container;
    private JTable assets = new JTable();

    public PortfolioWindow() {
        super();

        container = getContentPane();
        container.setLayout(new GridLayout(1,1));
        container.add(assets);
    }

    public JTable getAssets() {
        return assets;
    }
}

package de.lsn.example.fscrud.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.commons.io.FileUtils;

public class FsCrudMainView extends MvcCrudView<JFrame> {

	private static final String TITLE = "FS CRUD";
	private JTabbedPane mainTabbedPane;
	private JPanel customerPanel;
	private JPanel addressPanel;
	private JToolBar customerToolBar;

	public FsCrudMainView() {
		this(null);
	}
	
	public FsCrudMainView(MvcCrudView<?> parent) {
		super(new JFrame(), parent);
		setup();
		getComponent().setTitle(TITLE);
		getComponent().setSize(600, 800);
		getComponent().setVisible(true);
	}
	
	private void setup() {
		
		try {
			getComponent().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getComponent().setLayout(new BorderLayout(10, 10));
			getComponent().getContentPane().add(getMainPanel());
	//		getComponent().setLayout(new GridLayout(5, 3, 5, 5));
	//		for (int i = 0; i < 5; i++) {
	//			for (int j = 0; j < 3; j++) {
	//				getComponent().getContentPane().add(new JButton("Button "+i+"x"+j));
	//			}
	//		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JTabbedPane getMainPanel() throws IOException {
		if (null == mainTabbedPane) {
			this.mainTabbedPane = new JTabbedPane();
			mainTabbedPane.addTab("Kunden", new ImageIcon(FileUtils.readFileToByteArray(new File("src/main/resources/gfx/icon_customer.png")), "Adressen"), getCustomerPanel(), "Adressdaten");
			mainTabbedPane.addTab("Adressen", new ImageIcon(FileUtils.readFileToByteArray(new File("src/main/resources/gfx/icon_contact.png")), "Kunden"), getAddressPanel(), "Kundendaten");
		}
		return mainTabbedPane;
	}
	
	private JPanel getCustomerPanel() {
		if (null == customerPanel) {
			this.customerPanel = new JPanel();
			this.customerPanel.setLayout(new BorderLayout(5, 5));
			this.customerPanel.add(getCustomerToolBar(), BorderLayout.NORTH);
		}
		return customerPanel;
	}
	
	private JToolBar getCustomerToolBar() {
		if (null == customerToolBar) {
			this.customerToolBar = new JToolBar();
			this.customerToolBar.add(new JButton("Moo"));
			this.customerToolBar.add(new JButton("Foo"));
			this.customerToolBar.add(new JButton("Koo"));
		}
		return customerToolBar;
	}
	
	private JPanel getAddressPanel() {
		if (null == this.addressPanel) {
			this.addressPanel = new JPanel();
			this.addressPanel.setLayout(new GridLayout(3, 3, 5, 5));
		}
		return addressPanel;
	}
	
	@Override
	public JFrame getComponent() {
		return super.getComponent();
	}
	
}

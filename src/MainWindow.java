import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentListener;
import static javax.swing.GroupLayout.Alignment.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.lang.*;
import java.util.*;

class MainWindow extends JFrame implements ActionListener, DocumentListener
{



	JCheckBox 			parseAllLinks, parseImages, parseEmails, parsePersonalInfo, parseSubDomains, scanPorts, scanVersionInfo;

	JButton				executeButton, quitButton;

	JTextField			websiteField;

	JTextArea			headerArea, websiteHTMLArea;

	JLabel				checkboxLabel, websiteFieldLabel;

	JPanel				headerPanel, checkboxPanel, buttonPanel, infoPanel, mainPanel ,websitePanel;

	JScrollPane			scrollList, scrollTextArea;

	ArrayList<String> 	arrayList;
	DefaultListModel	dlm;
	JList				websiteList;
	String 				website, header;
	//GroupLayout		gLayout;
	GridBagLayout		bagLayout;
	GridBagConstraints	bagConstraints;
	Container			cp;


	public MainWindow()
	{
		// initializing dependent variables
		arrayList = new ArrayList<>();
		dlm = new DefaultListModel();
		websiteList	= new JList(dlm);
		header = "Filler text --------------------------------------------------------------------\n" +
							"---------------------------------------------------------------------------------";

		// initializing GridBagLayout components
		bagConstraints = new GridBagConstraints();
		bagConstraints.fill = GridBagConstraints.BOTH;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		// creating JLabels
		checkboxLabel = new JLabel("Select parse options:");
		websiteFieldLabel = new JLabel("Website to parse:");
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 0;
		mainPanel.add(websiteFieldLabel, bagConstraints);

		// creating JCheckBoxes
		parseAllLinks = new JCheckBox("Parse all website links", true);
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 1;
		parseAllLinks.setOpaque(false);
		mainPanel.add(parseAllLinks, bagConstraints);

		parseImages = new JCheckBox("Parse all website images");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 2;
		parseImages.setOpaque(false);
		mainPanel.add(parseImages, bagConstraints);

		parseEmails = new JCheckBox("Parse all Email addresses");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 3;
		parseEmails.setOpaque(false);
		mainPanel.add(parseEmails, bagConstraints);

		parsePersonalInfo = new JCheckBox("Parse all contact information");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 4;
		parsePersonalInfo.setOpaque(false);
		mainPanel.add(parsePersonalInfo, bagConstraints);

		parseSubDomains = new JCheckBox("Find all subdomains");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 5;
		parseSubDomains.setOpaque(false);
		mainPanel.add(parseSubDomains, bagConstraints);

		scanPorts = new JCheckBox("Scan ports");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 6;
		scanPorts.setOpaque(false);
		mainPanel.add(scanPorts, bagConstraints);

		scanVersionInfo = new JCheckBox("Scan for version information");
		bagConstraints.weighty = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 7;
		scanVersionInfo.setOpaque(false);
		mainPanel.add(scanVersionInfo, bagConstraints);

		// creating Buttons
		executeButton = new JButton("Execute");
		executeButton.addActionListener(this);
		executeButton.setActionCommand("EXECUTE");

		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		quitButton.setActionCommand("QUIT");

		// creating JTextFields
		websiteField = new JTextField(30);
		bagConstraints.gridx = 1;
		bagConstraints.gridy = 0;
		websiteField.getDocument().addDocumentListener(this);
		mainPanel.add(websiteField, bagConstraints);

		// creating JTextAreas
		headerArea = new JTextArea(header);
		headerArea.setEditable(false);

		websiteHTMLArea = new JTextArea("TEST TEST TEST TEST");
		bagConstraints.gridx = 1;
		bagConstraints.gridy = 1;
		bagConstraints.gridheight = 7;
		websiteHTMLArea.setEditable(true);
		scrollTextArea = new JScrollPane(websiteHTMLArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollTextArea, bagConstraints);

		// creating general JPanels
		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		infoPanel.add(headerArea);

		buttonPanel = new JPanel();
		buttonPanel.add(executeButton);
		buttonPanel.add(quitButton);

		// adding panels to ContentPane
		//getContentPane().add(headerPanel, BorderLayout.NORTH);
		getContentPane().add(infoPanel, BorderLayout.NORTH);
		//getContentPane().add(websitePanel, BorderLayout.CENTER);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
       	//getContentPane().add(checkboxPanel, BorderLayout.WEST);
       	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

       	JRootPane rootPane = SwingUtilities.getRootPane(executeButton);
       	rootPane.setDefaultButton(executeButton);
        setupMainFrame();
	}// end MainWindow constructor

	void setupMainFrame()
    {
        Toolkit    tk;
        Dimension   d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();

        setTitle("WebCrawler");
        //setSize(d.width/2, d.height/3);
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //setBackground(Color.YELLOW);
        setVisible(true);
        pack();
    }// end setupMainFrame function

    public void actionPerformed(ActionEvent ae)
    {
    	if(ae.getActionCommand().equals("EXECUTE"))
    	{
    		//selectedCheckBoxes();
			startScraper();
    		System.out.println("The options selected are: -> ");
    	}
    	else if(ae.getActionCommand().equals("QUIT"))
    	{
    		System.exit(1);
    	}
    	else
    	{ /*do nothing for now*/}

    }// end actionPerformed function

    public void insertUpdate(DocumentEvent de)
    {
        website = websiteField.getText().trim();
        if(!website.equals(""))
        {
            executeButton.setEnabled(true);
        }
    }

    public void removeUpdate(DocumentEvent de)
    {
        website = websiteField.getText().trim();
        if(website.equals(""))
        {
            executeButton.setEnabled(false);
        }
    }

    public void changedUpdate(DocumentEvent de)
    {}


    public void startScraper()
	{
		website = websiteField.getText().trim();
		System.out.println("Scraping the website: -> " + website);
		website = "https://" + website;
		try {
			new WebScraper(website);
			websiteHTMLArea.setText("Currently scraping: -> " + website);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void updateUI()
	{
		cp = getContentPane();
		cp.invalidate();
		cp.validate();
	}

	public void updateComponent(String text)
	{
		System.out.println("text variable contains: " + text);
		websiteHTMLArea.append('\n' + text);
		System.out.println("Inside updateComponent()");
	}



}// end MainWindow class
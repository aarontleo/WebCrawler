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

	JPanel				buttonPanel, infoPanel, mainPanel;

	JScrollPane			scrollTextArea;

	ArrayList<String> 	websiteList;

	String 				website, header;
	GridBagConstraints	bagConstraints;
	Container			cp;
	WebScraper          webScraper;


	public MainWindow()
	{
		// initializing dependent variables
		websiteList = new ArrayList<>();
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
		bagConstraints.ipadx = 0;
		bagConstraints.ipady = 0;
		websiteHTMLArea.setEditable(false);
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
		getContentPane().add(infoPanel, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
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

        setTitle("Scraper v1.0");
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        pack();
    }// end setupMainFrame function

    public void actionPerformed(ActionEvent ae)
    {
    	if(ae.getActionCommand().equals("EXECUTE"))
    	{
    		//selectedCheckBoxes();
            System.out.println("Execute button pressed");
			startScraper();
    		//System.out.println("The options selected are: -> ");
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
    } // remove insertUpdate() function

    public void removeUpdate(DocumentEvent de)
    {
        website = websiteField.getText().trim();
        if(website.equals(""))
        {
            executeButton.setEnabled(false);
        }
    } // end removeUpdate() function

    public void changedUpdate(DocumentEvent de)
    {} // end changedUpdate() function


    public void startScraper()
	{
		website = websiteField.getText().trim();
		System.out.println("Scraping the website: -> " + website);
		website = "https://" + website;
		try {
			webScraper = new WebScraper(website);
			websiteHTMLArea.setText("Currently scraping: -> " + website + '\n');
			websiteList = webScraper.getLinks();

			for(int index = 0; index < websiteList.size(); index++)
            {
                updateTextArea(websiteList.get(index));
                index++;
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}// end startScraper() function

	public void updateUI()
	{
	    // Currently unused

		cp = getContentPane();
		cp.invalidate();
		cp.validate();
	}// end updateUI() function

	public void updateTextArea(String s)
	{
		System.out.println("String variable passed to updateComponent() is: " + s);
		websiteHTMLArea.append('\n' + s);
	}// end updateTextArea() function



}// end MainWindow class
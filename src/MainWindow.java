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
	String 		website, header;
	GroupLayout			gLayout;
	Container			cp;


	public MainWindow()
	{
		// initializing dependent variables
		arrayList = new ArrayList<>();
		dlm = new DefaultListModel();
		websiteList	= new JList(dlm);
		header = "Filler text --------------------------------------------------------------------\n" +
							"---------------------------------------------------------------------------------";
		// creating JCheckBoxes
		parseAllLinks = new JCheckBox("Parse all website links", true);
		parseAllLinks.setOpaque(false);
		parseImages = new JCheckBox("Parse all website images");
		parseImages.setOpaque(false);
		parseEmails = new JCheckBox("Parse all Email addresses");
		parseEmails.setOpaque(false);
		parsePersonalInfo = new JCheckBox("Parse all contact information");
		parsePersonalInfo.setOpaque(false);
		parseSubDomains = new JCheckBox("Find all subdomains");
		parseSubDomains.setOpaque(false);
		scanPorts = new JCheckBox("Scan ports");
		scanPorts.setOpaque(false);
		scanVersionInfo = new JCheckBox("Scan for version information");
		scanVersionInfo.setOpaque(false);

		// creating Buttons
		executeButton = new JButton("Execute");
		executeButton.addActionListener(this);
		executeButton.setActionCommand("EXECUTE");

		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		quitButton.setActionCommand("QUIT");

		// creating JTextFields
		websiteField = new JTextField(30);
		websiteField.getDocument().addDocumentListener(this);

		// creating JTextAreas
		headerArea = new JTextArea(header);
		headerArea.setEditable(false);

		websiteHTMLArea = new JTextArea("TEST TEST TEST TEST");
		websiteHTMLArea.setEditable(true);
		scrollTextArea = new JScrollPane(websiteHTMLArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollTextArea.setBounds(0, 0, 200, 200);

		// creating JLabels
		checkboxLabel = new JLabel("Select parse options:");
		websiteFieldLabel = new JLabel("Website to parse:");

		// creating JPanels
		mainPanel = new JPanel();
		gLayout = new GroupLayout(mainPanel);
		gLayout.setAutoCreateGaps(true);
		gLayout.setAutoCreateContainerGaps(true);
		mainPanel.setLayout(gLayout);
		GroupLayout.SequentialGroup hGroup = gLayout.createSequentialGroup();
			hGroup.addGroup(gLayout.createParallelGroup(LEADING).addComponent(websiteFieldLabel).addComponent(parseAllLinks).addComponent(parseImages).addComponent(parseEmails).addComponent(parsePersonalInfo).addComponent(parseSubDomains).addComponent(scanPorts).addComponent(scanVersionInfo));
			hGroup.addGroup(gLayout.createParallelGroup(TRAILING).addComponent(websiteField).addComponent(scrollTextArea));
		gLayout.setHorizontalGroup(hGroup);
		GroupLayout.SequentialGroup vGroup = gLayout.createSequentialGroup();
			vGroup.addGroup(gLayout.createParallelGroup(BASELINE).addComponent(websiteFieldLabel).addComponent(websiteField));
			vGroup.addGroup(gLayout.createParallelGroup(BASELINE).addComponent(parseAllLinks).addComponent(scrollTextArea));
			//vGroup.addGroup(gLayout.createParallelGroup(BASELINE).addComponent(parseAllLinks).addComponent(parseImages).addComponent(scrollList));
			vGroup.addGroup(gLayout.createSequentialGroup().addComponent(parseAllLinks).addComponent(parseImages).addComponent(parseEmails).addComponent(parsePersonalInfo).addComponent(parseSubDomains).addComponent(scanPorts).addComponent(scanVersionInfo));
			vGroup.addComponent(scrollTextArea);
			//vGroup.addComponent(parseAllLinks);
			//vGroup.addComponent(parseImages);
			//vGroup.addComponent(parseEmails);
			//vGroup.addComponent(parsePersonalInfo);
			//vGroup.addComponent(parseSubDomains);
			//vGroup.addComponent(scanPorts);
			//vGroup.addComponent(scanVersionInfo);
		gLayout.setVerticalGroup(vGroup);

		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		infoPanel.add(headerArea);

		buttonPanel = new JPanel();
		//buttonPanel.setBackground(Color.RED);
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
		//repaint();
		System.out.println("Inside updateComponent()");
	}



}// end MainWindow class
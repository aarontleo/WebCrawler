import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultsWindow extends JDialog
{
    JTextArea           ta1, ta2, ta3;
    JPanel              jp1, jp2, jp3;
    JScrollPane         sp1, sp2, sp3;
    JTabbedPane         resultsPane;
    ArrayList<String>   results;
    Container           cp;

    public ResultsWindow()
    {
        cp = getContentPane();
        ta1 = new JTextArea("");
        ta2 = new JTextArea("");
        ta3 = new JTextArea("");
        resultsPane = new JTabbedPane();
        createTabs();
        setTitle("Results");
        setupDialog();
    }// end ResultsTab() class constructor

    void setupDialog()
    {
        Toolkit     tk;
        Dimension   d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        setSize(d.width/4, d.height/4);
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void createTabs()
    {
        if(resultsPane != null){ cp.remove(resultsPane); }

        sp1 = new JScrollPane(ta1);
        sp2 = new JScrollPane(ta2);
        sp3 = new JScrollPane(ta3);

        jp1 = new JPanel(new BorderLayout());
        jp1.add(sp1, BorderLayout.CENTER);
        jp2 = new JPanel(new BorderLayout());
        jp2.add(sp2, BorderLayout.CENTER);
        jp3 = new JPanel(new BorderLayout());
        jp3.add(sp3, BorderLayout.CENTER);

        resultsPane.addTab("Link Results", sp1);
        resultsPane.addTab("Image Results", sp2);
        resultsPane.addTab("Email Results", sp3);

        cp.add(resultsPane, BorderLayout.CENTER);
    }


    public void setResults(ArrayList<String> results)
    {
        if(ta1.getText().equals(""))
        {
            this.results = results;
            for(int index = 0; index < results.size(); index++)
            {
                ta1.append('\n' + results.get(index));
            }
        }
        else if(ta2.getText().equals(""))
        {
            this.results = results;
            for(int index = 0; index < results.size(); index++)
            {
                ta2.append('\n' + results.get(index));
            }
        }
        else if(ta3.getText().equals(""))
        {
            this.results = results;
            for(int index = 0; index < results.size(); index++)
            {
                ta3.append('\n' + results.get(index));
            }
        }
        else
        {
            System.out.println("Something went wrong when trying to display results!");
            JOptionPane.showMessageDialog(null, "Error displaying results!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }









}// end ResultsTab() class

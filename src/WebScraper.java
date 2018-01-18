import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.*;


public class WebScraper {

    String          site;
    MainWindow      window;

    public WebScraper(String site)
    {
        window = new MainWindow();
        this.site = site;
        checkBoxes();

    }

    public void checkBoxes()
    {
        if(window.parseAllLinks.isSelected()){
                scrapeLinks();
        }
    }


    public void scrapeLinks()
    {
        try {
            System.out.println("Scraping Links!");
            Document websitePage = Jsoup.connect(site).get();
            Elements links = websitePage.select("a[href]");

            for(Element link : links)
            {
                System.out.println("Link found on " + site + " , Link: -> " + link.attr("href"));
                //window.websiteHTMLArea.append(link.attr("href"));
                window.updateComponent((link.attr("href")));
                //window.updateUI();
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}

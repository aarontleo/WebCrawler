import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.*;
import java.util.ArrayList;


public class WebScraper {

    String              site, websiteLink;
    ArrayList<String>   websiteList;

    public WebScraper(String site)
    {
        websiteList = new ArrayList<>();
        this.site = site;
        scrapeLinks();
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
                websiteLink = link.attr(("href"));
                websiteList.add(websiteLink);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLinks(){
        return websiteList;
    }

}

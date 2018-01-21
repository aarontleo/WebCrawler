import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebScraper {

    String              site, websiteLink, websiteImage, websiteEmail;
    ArrayList<String>   websiteList, imageList, emailList;
    ResultsWindow       resultsWindow;

    public WebScraper(String site)
    {
        websiteList = new ArrayList<>();
        imageList = new ArrayList<>();
        emailList = new ArrayList<>();
        this.site = site;
        resultsWindow = new ResultsWindow();
    }// end WebScraper class constructor


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
    }// end scrapeLinks() function

    public void scrapeImages()
    {
        try {
            System.out.println("Scraping Images!");
            Document websitePage = Jsoup.connect(site).get();
            Elements images = websitePage.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

            for(Element image: images)
            {
                System.out.println("Image found on " + site + " , Image: ->" + image.attr("src"));
                websiteImage = image.attr("src");
                imageList.add(websiteImage);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }// end scraperImages() function

    public void scrapeEmails()
    {
        try {
            System.out.println("Scraping Emails!");
            Document websitePage = Jsoup.connect(site).get();
            Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
            Matcher m = p.matcher(websitePage.text());

            while(m.find())
            {
                System.out.println("Email found on " + site + " , Email address: -> " + m.group());
                emailList.add(m.group());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }// end scrapeEmails() function

    public ArrayList<String> getLinks(){ return websiteList; }// end getLinks() function
    public ArrayList<String> getImages() { return imageList; }// end getImages() function
    public ArrayList<String> getEmails() { return emailList; }// end getEmails() function

}// end WebScraper class

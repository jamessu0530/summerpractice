import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlKeelungFirstPage {
    private static final String BASE_URL = "https://www.travelking.com.tw";
    public static void main(String[] args) throws IOException {
        CrawlKeelungFirstPage crawler = new CrawlKeelungFirstPage();
        Sight[] sights = crawler.getItems("七堵");
        for (Sight s : sights) System.out.println(s);
    }
    public Sight[] getItems(String zone) throws IOException {// 抓七堵
        String startUrl = BASE_URL + "/tourguide/taiwan/keelungcity/";
        Document doc = Jsoup.connect(startUrl).get();
        Elements links = doc.select("a[href]");
        String qiduUrl = null;
        for (Element link : links) {
            String text = link.text().trim();
            String href = link.attr("href");
            if (text.equals("七堵區")) {
                if (href.startsWith("http")) 
                    qiduUrl = href;
                 else 
                    qiduUrl = BASE_URL + href;
                break;
            }
        }
        Document qiduDoc = Jsoup.connect(qiduUrl).get();//進入七堵區
        Elements sightLinks = qiduDoc.getElementById("guide-point").selectFirst(".box").select("a[href]");
        Sight[] sights = new Sight[4];
        for (int i = 0; i < sightLinks.size(); i++) {
            Element link = sightLinks.get(i);
            String href = link.attr("href");
            String fullUrl = href.startsWith("http") ? href : BASE_URL + href;
            sights[i] = getItem(fullUrl, zone);
        }
        return sights;
    }
    public Sight getItem(String url, String zone) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Sight sight = new Sight();
        sight.setZone(zone);
        // 抓景點名稱
        sight.setSightName(doc.selectFirst("meta[itemprop=name]").attr("content"));
        // 抓分類
        sight.setCategory(doc.selectFirst("span[property='rdfs:label'] strong").text());
        // 抓圖片網址
        sight.setPhotoURL(doc.selectFirst("meta[itemprop=image]").attr("content"));
        // 抓描述
        sight.setDescription(doc.selectFirst("meta[itemprop=description]").attr("content"));
        // 抓地址
        sight.setAddress(doc.selectFirst("meta[itemprop=address]").attr("content"));
        return sight;
    }
}
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.119 YaBrowser/22.3.0.2430 Yowser/2.5 Safari/537.36";
    private static final String REFERRER = "https://www.google.com";
    private static final String urlPattern = "(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
    private static final List<String> banned = Arrays.asList("wp-login", "download");

    public ArrayList<Link> linkList = new ArrayList<>();
    public ArrayList<Pair<Integer, Integer>> paths = new ArrayList<>();

    private String host;

    public LinkService(String host) {
        this.host = host;
    }

    public void crawl() {
        Link link = new Link(host);
        linkList.add(link);
        getLinks(host, 0);
    }

    public void getLinks(String url, int level) {
        if (level != 3) {
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .referrer(REFERRER)
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .timeout(10000)
                        .get();
                Elements links = doc.select("a");
                Link linkSite = new Link(url);
                for (Element link : links) {
                    String href = link.attr("href");
                    if (href.matches(urlPattern)) {
                        Link subLinkSite = new Link(href);
                        if (!linkList.contains(subLinkSite)) {
                            linkList.add(subLinkSite);
                            getLinks(href, level+1);
                            int index = linkList.indexOf(subLinkSite);
                            if (index != -1) {
                                paths.add(new Pair<>(linkList.indexOf(linkSite), index));
                            }
                        } else if (linkList.contains(subLinkSite)) {
                            paths.add(new Pair<>(linkList.indexOf(linkSite), linkList.indexOf(subLinkSite)));
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}

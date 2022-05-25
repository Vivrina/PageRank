import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LinkService linkService = new LinkService("http://gramota.ru/");
        linkService.crawl();
        for (Link link : linkService.linkList) {
            System.out.println(link.toString());
        }
        System.out.println(linkService.linkList.size());

        PageRank pageRank = new PageRank(linkService.paths, linkService.linkList.size());
        pageRank.doAlgorithm(3);

        BigDecimal[] authority = pageRank.getaMatrix();
        BigDecimal[] hubbiness = pageRank.gethMatrix();

        for (int j = 0; j < linkService.linkList.size(); j++) {
            linkService.linkList.get(j).setAuthority(authority[j]);
            linkService.linkList.get(j).setHubbiness(hubbiness[j]);
        }
        List<Link> result1 = linkService.linkList.stream().sorted((o1, o2)->o2.getAuthority().
                        compareTo(o1.getAuthority())).
                collect(Collectors.toList());



        List<Link> result2 = linkService.linkList.stream().sorted((o1, o2)->o2.getHubbiness().
                        compareTo(o1.getHubbiness())).
                collect(Collectors.toList());


        System.out.println("Authorities");
        for (Link link : result1) {
            System.out.println("Authority: " + link.getAuthority() + " for " + link.getUrl());
        }

        System.out.println("Hubs");
        for (Link link : result2) {
            System.out.println("Hubbiness: "+link.getHubbiness() + " for " + link.getUrl());
        }
    }

}

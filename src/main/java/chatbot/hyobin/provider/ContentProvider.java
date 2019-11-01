package chatbot.hyobin.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContentProvider {
    @Value("${swexpert.session}")
    private String session;
    @Value("${swexpert.language}")
    private String language;
    @Value("${swexpert.url}")
    private String url;

    private static final String SEARCH_STRING = "신청중";
    public boolean checkLeast() throws IOException {
        Connection conn = Jsoup.connect(url)
                .header("Content-Type", "application/json;charset=UTF-8")
                .cookie("SESSION",session)
                .cookie("LANGUAGE",language);
        Document doc = conn.get();
        String a[] = doc.toString().split("B형");
        //System.out.println(a[0]);
        return a[0].contains(SEARCH_STRING);
    }

}

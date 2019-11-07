package chatbot.hyobin.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SwExpertProvider extends ContentProvider{
    @Value("${swexpert.session}")
    private String session;
    @Value("${swexpert.language}")
    private String language;
    @Value("${swexpert.url}")
    private String url;

    String desc = "이 기능은 Sw Expert Academy에 자리가 났는지를 확인하는 기능입니다. \n/신청 [세션값] 하시면 신청할 수 있는 정보를 확인 할 수 있습니다.";

    private static final String SEARCH_STRING = "신청중";

    public String getDescrib(){
        return desc;
    }

    @Override
    public String chooseFunction(String[] str) {
        if(str[1].equals("설명")){
            return getDescrib();
        }
        else{
            return startFunction(str[1]);
        }
    }
    private String startFunction(String s){
        Connection conn = Jsoup.connect(url)
                .header("Content-Type", "application/json;charset=UTF-8")
                .cookie("SESSION",s)
                .cookie("LANGUAGE",language);
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String a[] = doc.toString().split("B형");

        if(!a[0].contains("인재개발원")){
            return "연결 종료";
        }
        if(a[0].contains(SEARCH_STRING)){
            return "쌉가능";
        }
        else{
            return "쌉불가능";
        }
    }

}

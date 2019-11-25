package chatbot.hyobin.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class WeatherProvider extends ContentProvider{

    String desc = "이 기능은 날씨는 확인할 수 있는 기능입니다.\n/날씨 [지역] 입력하시면 확인할 수 있습니다.";
    static final String url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=날씨+";

    public String getDescrib(){
        return desc;
    }
    @Override
    public String chooseFunction(String[] str) throws IOException {

        if(str[1].equals("설명")){
            return getDescrib();
        }
        else{
            return startFunction(str[1]);
        }
    }

    private String startFunction(String str) throws IOException {

        Connection conn = Jsoup.connect(url+str);
        Document doc = conn.get();

        String result="";
        Elements elements=doc.select(".cast_txt");
        result+=elements.first().text();
        result+=" \n";
        elements=doc.select(".todaytemp");
        result+="현재 기온은 "+elements.first().text()+"도";

        return result;
    }
}

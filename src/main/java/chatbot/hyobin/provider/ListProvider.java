package chatbot.hyobin.provider;

import chatbot.hyobin.enumClass.ProviderEnumClass;
import org.springframework.stereotype.Component;

@Component
public class ListProvider extends ContentProvider{
    @Override
    public String chooseFunction(String[] str) {
        String result="";
        for(ProviderEnumClass providerEnumClass : ProviderEnumClass.values()){
            result+="/"+providerEnumClass.name()+"\n";
        }
        return result;
    }
}

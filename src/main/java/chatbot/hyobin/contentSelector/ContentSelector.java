package chatbot.hyobin.contentSelector;

import chatbot.hyobin.enumClass.ProviderEnumClass;
import chatbot.hyobin.provider.ContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContentSelector {

    @Autowired
    private ApplicationContext context;

    public String selectContent(String arguments) throws IOException {
        String args[] = arguments.split(" ");
        ContentProvider contentProvider;
        try {
            contentProvider = ProviderEnumClass.valueOf(args[0]).getObject(context);
        }
        catch(RuntimeException runtimeException){
            return "없는 기능입니다.";
        }
        return contentProvider.chooseFunction(args);
    }
}

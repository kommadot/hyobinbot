package chatbot.hyobin.enumClass;

import chatbot.hyobin.provider.ContentProvider;
import chatbot.hyobin.provider.ListProvider;
import chatbot.hyobin.provider.SwExpertProvider;
import chatbot.hyobin.provider.WeatherProvider;
import org.springframework.beans.factory.BeanFactory;

public enum ProviderEnumClass {
    신청(SwExpertProvider.class),
    날씨(WeatherProvider.class),
    기능목록(ListProvider.class);

    Class<? extends ContentProvider> type;

    ProviderEnumClass(Class<? extends ContentProvider> type){
        this.type = type;
    }
    public <T extends ContentProvider> T getObject(BeanFactory beanFactory){
        return (T) beanFactory.getBean(type);
    }
}

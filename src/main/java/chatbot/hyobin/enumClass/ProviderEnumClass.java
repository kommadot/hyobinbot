package chatbot.hyobin.enumClass;

import chatbot.hyobin.provider.ContentProvider;
import chatbot.hyobin.provider.ListProvider;
import chatbot.hyobin.provider.SwExpertProvider;
import org.springframework.beans.factory.BeanFactory;

public enum ProviderEnumClass {
    신청(SwExpertProvider.class),
    기능목록(ListProvider.class);

    Class<? extends ContentProvider> type;

    ProviderEnumClass(Class<? extends ContentProvider> type){
        this.type = type;
    }
    public <T extends ContentProvider> T getObeject(BeanFactory beanFactory){
        return (T) beanFactory.getBean(type);
    }
}

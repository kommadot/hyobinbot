package chatbot.hyobin.provider;

import java.io.IOException;

abstract public class ContentProvider {

    abstract public String chooseFunction(String[] str) throws IOException;


}

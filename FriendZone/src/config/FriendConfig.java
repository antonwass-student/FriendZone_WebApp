package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Anton on 2016-11-24.
 */
@ManagedBean(eager=true)
@ApplicationScoped
public class FriendConfig {

    private static String friendApiUrl;

    private static String chatApiUrl;

    @PostConstruct
    public void init(){
        loadConfig();
    }


    private void loadConfig(){
        try{
            ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = context.getRealPath("/WEB-INF/config.xml");
            File xmlFile = new File(path);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            Node node = doc.getElementsByTagName("webservices").item(0);

            Element element = (Element) node;

            friendApiUrl = ((Element)element.getElementsByTagName("friend-api").item(0)).getAttribute("value");
            chatApiUrl = ((Element)element.getElementsByTagName("chat-api").item(0)).getAttribute("value");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getFriendApiUrl(){
        return friendApiUrl;
    }

    public static String getChatApiUrl(){
        return chatApiUrl;
    }

}

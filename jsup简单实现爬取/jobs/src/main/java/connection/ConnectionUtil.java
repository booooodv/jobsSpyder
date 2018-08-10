package connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ConnectionUtil {
    /**
     * 通过网址获得页面信息
     * @param address   传入地址
     * @return document 返回页面
     */
    public static Document Connect(String address) {
        while (true){
            try {
                System.out.println(address);
                Document document = Jsoup.connect(address).get();
                return document;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}

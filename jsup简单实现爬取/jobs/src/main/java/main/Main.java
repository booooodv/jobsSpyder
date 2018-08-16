package main;

import analysis.Spyder;
import connection.ConnectionUtil;
import org.jsoup.nodes.Document;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Spyder spyder = new Spyder();
        Document document = ConnectionUtil.Connect("https://search.51job.com/list/020000,000000,0000,32,9,99,%2B,2,2.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");
        List<String> urlList = spyder.jobUrlSpyder(document);
        for (String url:urlList) {
            Document urlDoc = ConnectionUtil.Connect(url);
            if(spyder.jobSpyder(urlDoc)){
                System.out.println("开心");
            }else {
                System.out.println("难过");
            }
        }
    }
}

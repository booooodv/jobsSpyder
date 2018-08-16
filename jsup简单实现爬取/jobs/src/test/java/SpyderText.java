import analysis.Spyder;
import connection.ConnectionUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class SpyderText {
    @Test
    public void jobUrlSpyderText(){
        Spyder spyder = new Spyder();
        Document document = ConnectionUtil.Connect("https://search.51job.com/list/020000,000000,0000,32,9,99,%2B,2,2.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");
        spyder.jobUrlSpyder(document);
    }

    @Test
    public void realSalaryText(){
        Spyder spyder = new Spyder();
        spyder.getRealSalary("1-3万/月");
    }

    @Test
    public void jobSpyderText(){
        Spyder spyder = new Spyder();
        Document document = ConnectionUtil.Connect("https://jobs.51job.com/shanghai-pdxq/105661634.html?s=01&t=0");
        spyder.jobSpyder(document);
    }
}

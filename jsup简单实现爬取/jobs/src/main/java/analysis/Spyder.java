package analysis;

import io.Output;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Spyder {

    /**
     *  用来得到URL列表，后续用来爬取招聘信息
     * @param document  传入页面信息
     * @return  链接列表
     */
    public List<String> jobUrlSpyder(Document document){
        List<String> urlList = new ArrayList<String>();
        Elements elements = document.select(".t1 span a");  //每个class为ti的子标签span下的a标签
        for (Element e:elements) {  //遍历每个a标签
            String url = e.attr("href");    //取出href属性，即是链接
            urlList.add(url);   //每个链接加入List用来返回
        }
//        System.out.println(urlList);
        return  urlList;
    }

    /**
     *  传入链接，爬取需要的信息并写入数据库
     * @param document  传入页面信息
     * @return  返回true表示成功，返回false表示失败
     */
    public boolean jobSpyder(Document document){
        try {
            Output output = new Output();
            //标题title，公司company，地区area，工作地点workPlace，工资salary
            //招聘要求jobInformation,工作经验experience,创建时间creatTime
            String title,company ,area,workPlace,jobInformation,experience;
            int salary = 0;
            Date creatTime= new java.sql.Date(new java.util.Date().getTime());
            title = document.select(".cn h1").text();   //cn类下的h1标签里的文字
            company = document.select(".com_name p").text();    //com_name类下的p标签里的文字
            String areaAndExperience = document.getElementsByAttributeValue("class", "msg ltype").text();    //获得一串字符串，用来拆分出地区和经验(因为类名有空格不能用select来得到）
            String a[] = areaAndExperience.split("\\|");
            area = a[0]; //地点
            experience = a[1];   //经验
            workPlace = document.select(".fp").text();
            String salaryString = document.select(".cn strong").text(); //因为得到的是1-3万/月这样子，所以需要转化成int型
            float salarys[] = getRealSalary(salaryString);
            salary = (int)(salarys[0] + salarys[1]) / 2;    //工资去范围的平均值
            jobInformation = document.getElementsByAttributeValue("class", "bmsg job_msg inbox").text();

//            System.out.println("title：" + title);
//            System.out.println("company：" + company);
//            System.out.println("area：" + area);
//            System.out.println("workPlace：" + workPlace);
//            System.out.println("salary：" + salary);
//            System.out.println("jobInformation：" + jobInformation);
//            System.out.println("experience：" + experience);
//            System.out.println("creatTime：" + creatTime);
            //写入mysql
            output.toMysql(title,company,area,workPlace,salary,jobInformation,experience,creatTime);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    public  void zhaopinSpyder(Document document){

    }

    public float[] getRealSalary(String salaryString){
        int subNum = salaryString.length()-3;   //重复利用提取变量
        String t = salaryString.substring(subNum); //会得到万/月或者千/月
        float salarys[] = {0,0};
        if (t.equals("万/月")){
            t = salaryString.substring(0,subNum);
            String[] salaryStrings = t.split("-");
            salarys[0] = Float.parseFloat(salaryStrings[0]) * 10000;    //得到第一个数字乘以一万
            salarys[1] = Float.parseFloat(salaryStrings[1]) * 10000;    //得到第二个数字乘以一万
            return salarys;
        }else if(t.equals("千/月")){
            t = salaryString.substring(0,subNum);
            String[] salaryStrings = t.split("-");
            salarys[0] = Float.parseFloat(salaryStrings[0]) * 1000;    //得到第一个数字乘以一千
            salarys[1] = Float.parseFloat(salaryStrings[1]) * 1000;    //得到第二个数字乘以一千
            return salarys;
        }else if(t.equals("元/天")){
            t = salaryString.substring(0,subNum);
            String[] salaryStrings = t.split("-");
            salarys[0] = Float.parseFloat(salaryStrings[0]) * 20;    //得到第一个数字 每个月工作4周大概20天
            salarys[1] = Float.parseFloat(salaryStrings[1]) * 20;
            return salarys;
        }else if(t.equals("万/年")){
            t = salaryString.substring(0,subNum);
            String[] salaryStrings = t.split("-");
            if(salaryStrings.length == 1){
                salarys[0] = Float.parseFloat(salaryStrings[0]) / 12;
                salarys[1] = salarys[0];
            }else {
                salarys[0] = Float.parseFloat(salaryStrings[0]) / 12;    //得到第一个数字 除以12得到每个月
                salarys[1] = Float.parseFloat(salaryStrings[1]) / 12;
            }
            return salarys;
        } else{
            //如果是正常数字的话，就直接变为数字返回（用于互联）
            String[] salaryStrings = t.split("-");
            salarys[0] = Float.parseFloat(salaryStrings[0]);
            salarys[1] = Float.parseFloat(salaryStrings[1]);
            return salarys;
        }
    }
}

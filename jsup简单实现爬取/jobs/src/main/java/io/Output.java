package io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

public class Output {
    public void toMysql(String title,String company,String area,String workPlace,int salary,String jobInformation,String experience,Date creatTime){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/work", "root","mysql");
            PreparedStatement pre = conn.prepareStatement("insert into jobs_text(title,company,area,work_place,salary,job_information,experience,creat_time) values(?,?,?,?,?,?,?,?)");
            pre.setObject(1, title);
            pre.setObject(2, company);
            //也可以使用setObject方法，避免判断参数的麻烦
            pre.setObject(3, area);
            pre.setObject(4, workPlace);
            pre.setObject(5,salary);
            pre.setObject(6,jobInformation);
            pre.setObject(7,experience);
            pre.setObject(8,creatTime);
            pre.execute();
            conn.close();
            pre.close();
            System.out.println("<-- 写入成功 -->");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

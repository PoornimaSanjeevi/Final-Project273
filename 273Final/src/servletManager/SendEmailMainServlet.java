package servletManager;

import servletManager.functions.CsvFileWriter;
import servletManager.functions.SearchTweets;
import servletManager.functions.SendFilesToS3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Spurthy on 5/13/2015.
 */
@WebServlet(name = "SendEmailMainServlet")
public class SendEmailMainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ht1 = request.getParameter("ht1");
        String ht2 = request.getParameter("ht2");
//        OperateMongoDBCollections.addSearchStringsToDB(ht1, ht2);
//        OperateMongoDBCollections.addTagCountByHourFromDB();
//        OperateMongoDBCollections.addAssociatedHashTagsFromDB();

        String fromDateInput = request.getParameter("fromDate");
        String[] splits = fromDateInput.split("-");
        String year = splits[0];
        String month = splits[1];
        String dayCrude = splits[2];
        String day = dayCrude.substring(0, 2);
        System.out.println(day);

        String hour = dayCrude.substring(3, 5);
        System.out.println(hour);
        String fromDate = year + "-" + month + "-" + day;
        System.out.println(fromDate);
        String fromTime = hour;
        String toSendDate = fromDate + "-" + fromTime;

        String toDate = request.getParameter("toDate");
        String toTime = request.getParameter("toTime");
        String email = request.getParameter("email");
        System.out.println(email);
        CsvFileWriter.writeCsvFile(ht1, ht2, toSendDate, toDate, email);
        SearchTweets.sendTweets(ht1, ht2, fromDate, toDate);
        String tweetsFilePath = "C:\\SJSU\\SecondSem\\283\\Project2\\273Final\\" + "2015-05-13" + ".txt";
        SendFilesToS3.putToS3(fromDate, tweetsFilePath, "dataToS3.csv", "C:\\SJSU\\SecondSem\\283\\Project2\\273Final\\dataToS3.csv");
        response.setContentType("text/html");
        response.sendRedirect("showStatistics.jsp");





    }
}

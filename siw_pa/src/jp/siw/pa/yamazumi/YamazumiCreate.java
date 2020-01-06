package jp.siw.pa.yamazumi;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pa.yamazumi.util.PropertyLoader;

/**
 * Servlet implementation class YamazumiCreate
 */
public class YamazumiCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public YamazumiCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //当日の日付取得
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

		String resultPage = PropertyLoader.getProperty("url.yamazumi_jsp.selectCalendarYM");

	    RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    dispatcher.forward(request, response);

	}
}

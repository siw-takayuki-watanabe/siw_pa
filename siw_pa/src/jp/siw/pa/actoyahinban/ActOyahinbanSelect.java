package jp.siw.pa.actoyahinban;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pa.actoyahinban.bean.ActoyahinbanBean;
import jp.siw.pa.actoyahinban.dao.ActoyahinbanDao;
import jp.siw.pa.yamazumi.util.PropertyLoader;

/**
 * Servlet implementation class ActoyahinbanSelect
 */
public class ActOyahinbanSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActOyahinbanSelect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.actoyahinban_jsp.error_actoyahinban");

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);
        //日付表示ここまで
        String tkcd = "1101";//KHI
        //String tkcd = "1001";//SMC
        //String tkcd = "1511";//KBT
        //String tkcd = "1102";//日立建機T


		try{

            ActoyahinbanDao selectHinbanDao = new ActoyahinbanDao();
            System.out.println("ActOyahinban 1====");
            List<ActoyahinbanBean> selectHinban = selectHinbanDao.getActoyahinban(tkcd);
    		//*******************************************************/


    		//*********t_fuka csv出力*****************
    		ActoyahinbanDao actoyahinban0100csvDao = new ActoyahinbanDao();
    		List<ActoyahinbanBean> yamazumi0100csv = actoyahinban0100csvDao.getActoyahinbancsv();
    		//********************************/



            //フォルダ内データ検索
    		String dir = "C:/pleiades/workspace/siw_pm/WebContent/FUKA/FUKA_FILES";
            // Fileクラスをインスタンス化
            File file = new File(dir);

            // listメソッドでファイルの一覧を配列で取得
            String temp_files[] = file.list();
            request.setAttribute("temp_files", temp_files);

            int fileSu = temp_files.length;
            String temp_files_URL[] = new String [fileSu];

            for (int i=0 ; i<fileSu ; i++){

            	temp_files_URL[i] = "../siw_pm/FUKA/FUKA_FILES/" + temp_files[i];
            	System.out.println(temp_files_URL[i]);
            }

            request.setAttribute("temp_files_URL", temp_files_URL);

        } catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		resultPage = PropertyLoader.getProperty("url.yamazumi_jsp.insertHinban");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}
}

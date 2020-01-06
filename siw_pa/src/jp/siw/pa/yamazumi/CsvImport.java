package jp.siw.pa.yamazumi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

import jp.siw.pa.yamazumi.bean.YamazumiBean;
import jp.siw.pa.yamazumi.dao.YamazumiDao;
import jp.siw.pa.yamazumi.util.PropertyLoader;

/**
 * Servlet implementation class CsvImport
 */
public class CsvImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CsvImport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.yamazumi_jsp.error_yamazumi");
		String hhgyc = "0100";
		//String hhgyc = "%";

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String half = request.getParameter("half");
        String del_year = request.getParameter("del_year");
        if(del_year == null){
        	del_year="";
        }
        String del_month = request.getParameter("del_month");
        if(del_month == null){
        	del_month="";
        }
        String del_half = request.getParameter("del_half");
        if(del_half == null){
        	del_half="";
        }

        String insertDay = year + month + half;
        String deleteDay = del_year + del_month + del_half;
        System.out.println("CsvImport  1== " + deleteDay);
        //日付表示ここまで

		//****************読込回避時にブランク*******************
		//ファイルを読み込む
		FileReader fr = new FileReader("\\\\192.168.101.236\\riseプロジェクト\\15.生産負荷計算\\01XXHACHU.csv");
		BufferedReader br = new BufferedReader(fr);
		//********************************************************/

		try{

			//****************読込回避時にブランク*******************
            YamazumiDao yamazumiDao = new YamazumiDao();
    		yamazumiDao.insertCsv(fr, br, insertDay );
    		//********************************************************/

            YamazumiDao yamazumiAll100Dao = new YamazumiDao();
            /***********riseプレス工順使用時*************************
    		List<YamazumiBean> yamazumi100All = yamazumiAll100Dao.getYamazumi100All(hhgyc);
    		//*******************************************************/

            //***********ラデッシュ構成使用時*************************
    		List<YamazumiBean> yamazumi100All = yamazumiAll100Dao.getYamazumi100All(hhgyc, insertDay, deleteDay);
    		//*******************************************************/

    		//*********t_fuka csv出力*****************
    		YamazumiDao yamazumi0100csvDao = new YamazumiDao();
    		List<YamazumiBean> yamazumi0100csv = yamazumi0100csvDao.getYamazumi0100csv();
    		//********************************/

    		//*********t_mitouroku 未登録リストcsv出力*****************
    		YamazumiDao mitouroku0100csvDao = new YamazumiDao();
    		List<YamazumiBean> mitouroku0100csv = mitouroku0100csvDao.getMitouroku0100csv();
    		//********************************/


            //フォルダ内データ検索
    		String dir = "C:/pleiades_1/workspace/siw_pa/WebContent/FUKA/FUKA_FILES";
            // Fileクラスをインスタンス化
            File file = new File(dir);

            // listメソッドでファイルの一覧を配列で取得
            String temp_files[] = file.list();
            request.setAttribute("temp_files", temp_files);

            int fileSu = temp_files.length;
            String temp_files_URL[] = new String [fileSu];

            for (int i=0 ; i<fileSu ; i++){

            	temp_files_URL[i] = "../siw_pa/FUKA/FUKA_FILES/" + temp_files[i];
            	System.out.println(temp_files_URL[i]);
            }

            request.setAttribute("temp_files_URL", temp_files_URL);
            br.close();

        } catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			br.close();
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			br.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
			br.close();
		}
		resultPage = PropertyLoader.getProperty("url.yamazumi_jsp.importCsv");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}
}

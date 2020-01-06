package jp.siw.pa.yamazumi.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.siw.pa.yamazumi.bean.YamazumiBean;

public class YamazumiDao {
	private static final String DELETE_0010HACHU = "DELETE FROM 0100hachu ";
	private static final String DELETE_T_FUKA = "DELETE FROM t_fuka ";
	private static final String DELETENAIJI_T_FUKA = "DELETE FROM t_fuka where ordb=0 and insertDay=? ";
	private static final String DELETE_T_MITOUROKU = "DELETE FROM t_mitouroku ";
	private static final String INSERT  = "insert into 0100hachu "
			+ "values ("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ? )";
	//private static final String SELECT_0100All = "select * from 0100hachu where  hhgyc=? and ordb!=0 group by hinb";
	private static final String SELECT_0100All = "select * from 0100hachu where  hhgyc=? group by hinb";
	/*************RISE工順使用************************
	private static final String CREATE_LIST = "select "
			+ "nhiz, ordb, A.hinban, A.koujun, A.koujun_dd_lt, A.koujun_sz_lt, hsry, (A.koujun_sz_lt * hsry) as 'kousuu',(A.koujun_dd_lt+ (A.koujun_sz_lt * hsry)) as 'soukousuu' "
			+ "FROM `m_koujun` A "
			+ "JOIN 0100hachu "
			+ "on A.hinban = hinb "
			+ "WHERE hinban like ? ";
	//*********************************************/
	//*************ラデッシュ構成使用*******************
	private static final String CREATE_LIST = "select nhiz, ordb, setubiCd, kata, oya_hinmoku, ko_hinmoku, setTime, cycleTime, hsry, TRUNCATE(cycleTime*hsry + 0.05 ,1) as kousuu from (with recursive temp ( Lv, oya_hinmoku, ko_hinmoku, su) as ( select 1 as Lv, PP.oya_hinmoku, PP.ko_hinmoku, PP.su from m_accroad_kousei PP where PP.oya_hinmoku = '18006-1631' UNION ALL select Lv+1, pp.oya_hinmoku, pp.ko_hinmoku, pp.su from temp t, m_accroad_kousei pp where pp.oya_hinmoku = t.ko_hinmoku ) select Lv, '18006-1631' as oya_hinmoku, ko_hinmoku, jikouteimei, S.setubiCd, S.cycleTime, S.dandoriLt*60 as setTime, su from temp left outer join m_accroad_hinmoku H on ko_hinmoku = H.hinmoku left outer join m_accroad_seisan S on ko_hinmoku = S.hinmoku UNION select 0 as Lv, '18006-1631' as oya_hinmoku,  oya_hinmoku as ko_hinmoku, '' as jikouteimei, C.setubiCd, C.cycleTime, C.dandoriLt*60 as setTime, A.su from m_accroad_kousei A left outer join m_accroad_hinmoku B on A.ko_hinmoku = B.hinmoku left outer join m_accroad_seisan C on A.ko_hinmoku = C.hinmoku where A.oya_hinmoku = '18006-1631' ORDER by Lv asc ) A JOIN 0100hachu  on A.oya_hinmoku = hinb WHERE A.oya_hinmoku ='18006-1631'";
	//*********************************************/
	/*************RISE工順使用************************
	private static final String INSERT_KOUSUU = "insert into t_fuka values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	//************************************************/
	//*************ラデッシュ構成使用*******************
	private static final String INSERT_KOUSUU = "insert into t_fuka values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	//*********************************************/
	private static final String INSERT_MITOUROKU = "insert into t_mitouroku values (?, ?, ?, ?, ? ) ";

	private DataSource source;
	public YamazumiDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
	}

	public void insertCsv(FileReader fr, BufferedReader br, String insertDay)	throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{
            PreparedStatement statementDelete = connection.prepareStatement(DELETE_0010HACHU);
            statementDelete.executeUpdate();
            System.out.println("Yamazumidao insertCsv 1====  削除しました");

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	            	System.out.println("Yamazumidao insertCsv 1====  while文の中");
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT);
						String tokk = token.nextToken().replace("\"", "").trim();
						statement.setString(1, tokk);
						//System.out.println("Yamazumidao insertCsv 2==== " + tokk);

						int ordb = Integer.parseInt(token.nextToken());
						statement.setInt(2, ordb);
						//System.out.println("Yamazumidao insertCsv 2==== " + ordb);

						String hgyc = token.nextToken().replace("\"", "").trim();
						statement.setString(3, hgyc);
						//System.out.println("Yamazumidao insertCsv 2==== " + hgyc);

						String hhgyc =token.nextToken().replace("\"", "").trim();
						statement.setString(4, hhgyc);
						//System.out.println("Yamazumidao insertCsv 2==== " + hhgyc);

						String hinb =token.nextToken().replace("\"", "").trim();
						statement.setString(5, hinb);
						//System.out.println("Yamazumidao insertCsv 2==== " + hinb);

						String nony =token.nextToken().replace("\"", "").trim();
						statement.setString(6, nony);
						//System.out.println("Yamazumidao insertCsv 2==== " + nony);

						String nhiz = token.nextToken().replace("\"", "").trim();
						statement.setString(7, nhiz);
						//System.out.println("Yamazumidao insertCsv 2==== " + nhiz);

						int hsry =Integer.parseInt(token.nextToken().replace("+", "").replace(".00", ""));
						statement.setInt(8, hsry);
						//System.out.println("Yamazumidao insertCsv 2==== " + hsry);

						String hhiz =token.nextToken().replace("\"", "").trim();
						statement.setString(9, hhiz);
						//System.out.println("Yamazumidao insertCsv 2==== " + hhiz);

						String kata = token.nextToken().replace("\"", "").trim();
						statement.setString(10,kata);
						//System.out.println("Yamazumidao insertCsv 2==== " + kata);

						String hinm = token.nextToken().replace("\"", "").trim();
						statement.setString(11,hinm);
						//System.out.println("Yamazumidao insertCsv 2==== " + hinm);

						statement.setString(12,insertDay);

						statement.executeUpdate();
						statement.close();
	                }
	                i++;
					System.out.println(i);
	            }

            //終了処理
            br.close();
            System.out.println("Yamazumidao insertCsv 2====  登録しました");

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        } catch(Exception e){

        }
	}
	/*****************RISE工順使用時プレス職場用負荷計算　データベース登録**************************
	public List<YamazumiBean>getYamazumi100All(String  hhgyc) throws SQLException {
		System.out.println("YamazumiDao getYamazumi100All 1=====");

		List<YamazumiBean> yamazumi100All = new ArrayList<YamazumiBean>();
		Connection connection = source.getConnection();

		try{
            PreparedStatement statementDeleteFuka = connection.prepareStatement(DELETE_T_FUKA);
            statementDeleteFuka.executeUpdate();

			PreparedStatement statement = connection.prepareStatement(SELECT_0100All);
            statement.setString(1,hhgyc);
            ResultSet result = statement.executeQuery();


            while(result.next()){

            	YamazumiBean fuka100 = new YamazumiBean();
            	fuka100.setHinb(result.getString("hinb"));
            	String hinb = fuka100.getHinb();

            	PreparedStatement statement3 = connection.prepareStatement("select count(hinban)as hinbanSu from m_koujun where hinban='" + hinb + "'");
            	ResultSet result3 = statement3.executeQuery();
            	while(result3.next()){


            		int su = result3.getInt("hinbanSu");

            		if(su == 0){

                		System.out.println(result3.getInt("hinbanSu") + "  未登録品番  :" + hinb);


            		}else {

                    	//System.out.println("YamazumiDao getYamazumi100All 2=====  " + hinb);

		            	PreparedStatement statement2 = connection.prepareStatement(CREATE_LIST);
			            statement2.setString(1,hinb);
			            ResultSet result2 = statement2.executeQuery();

			            while(result2.next()){
			            	PreparedStatement kousuu = connection.prepareStatement(INSERT_KOUSUU);
			            	kousuu.setDate  (1, result2.getDate("nhiz"));
			            	kousuu.setInt   (2, result2.getInt("ordb"));
			            	kousuu.setString(3, result2.getString("hinban"));
			            	kousuu.setString(4, result2.getString("koujun"));
			            	kousuu.setInt   (5, result2.getInt("koujun_dd_lt"));
			            	kousuu.setInt   (6, result2.getInt("koujun_sz_lt"));
			            	kousuu.setInt   (7, result2.getInt("hsry"));
			            	kousuu.setInt   (8, result2.getInt("kousuu"));
			            	kousuu.setInt   (9, result2.getInt("soukousuu"));
							kousuu.executeUpdate();
			            }
		            }
            	}
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
		System.out.println("YamazumiDao getYamazumi100All 3=====");
		return yamazumi100All;
	}
	//*******************************************************************************/

	//*****************ラディシュ構成使用時　プレス職場用負荷計算　データベース登録*******************
	public List<YamazumiBean>getYamazumi100All(String  hhgyc, String insertDay, String deleteDay) throws SQLException {
		System.out.println("YamazumiDao getYamazumi100All 1=====");

		List<YamazumiBean> yamazumi100All = new ArrayList<YamazumiBean>();
		Connection connection = source.getConnection();

		try{
			//t_fukaテーブル削除初回起動時だけオン、以後使わない
            //PreparedStatement statementDeleteFuka = connection.prepareStatement(DELETE_T_FUKA);
            //statementDeleteFuka.executeUpdate();

			//t_fukaテーブル　内示削除　2回目以降の処理
            PreparedStatement statementDeleteNaiji = connection.prepareStatement(DELETENAIJI_T_FUKA);
            statementDeleteNaiji.setString(1,  deleteDay);
            statementDeleteNaiji.executeUpdate();

            //t_mitourokuテーブル削除
            PreparedStatement statementDeletetMitouroku = connection.prepareStatement(DELETE_T_MITOUROKU);
            statementDeletetMitouroku.executeUpdate();

			PreparedStatement statement = connection.prepareStatement(SELECT_0100All);
            statement.setString(1,hhgyc);
            ResultSet result = statement.executeQuery();

            int i =1;
            while(result.next()){

            	YamazumiBean fuka100 = new YamazumiBean();
            	fuka100.setHinb(result.getString("hinb"));
            	String hinb = fuka100.getHinb();

            	PreparedStatement statement3 = connection.prepareStatement("select count(oya_hinmoku)as hinbanSu from m_accroad_kousei where oya_hinmoku='" + hinb + "'");
            	ResultSet result3 = statement3.executeQuery();

            	while(result3.next()){

            		int su = result3.getInt("hinbanSu");

            		if(su == 0){

                		System.out.println(result3.getInt("hinbanSu") + "  未登録品番  :" + hinb);

		            	PreparedStatement mitouroku = connection.prepareStatement(INSERT_MITOUROKU);
		            	mitouroku.setDate  (1, result.getDate("nhiz"));
		            	mitouroku.setInt   (2, result.getInt("ordb"));
		            	mitouroku.setString(3, result.getString("kata"));
		            	mitouroku.setString(4, result.getString("hinb"));
		            	mitouroku.setInt   (5, result.getInt("hsry"));
						mitouroku.executeUpdate();

            		}else {

                    	//System.out.println("YamazumiDao getYamazumi100All 2=====  " + hinb);
            			/*********with句*********
		            	PreparedStatement statement2 = connection.prepareStatement("select "
		            			+ "nhiz, ordb, setubiCd, kata, oya_hinmoku, ko_hinmoku, setTime, cycleTime, hsry, TRUNCATE(cycleTime*hsry + 0.05 ,1) as kousuu "
		            			+ "from (with recursive temp ( Lv, oya_hinmoku, ko_hinmoku, su) as ( select 1 as Lv, PP.oya_hinmoku, PP.ko_hinmoku, PP.su from m_accroad_kousei PP "
		            			+ "where PP.oya_hinmoku ='" + hinb + "' "
		            					+ "UNION ALL "
		            					+ "select Lv+1, pp.oya_hinmoku, pp.ko_hinmoku, pp.su "
		            					+ "from temp t, m_accroad_kousei pp "
		            					+ "where pp.oya_hinmoku = t.ko_hinmoku ) "
		            					+ "select Lv, '" + hinb + "' as oya_hinmoku, ko_hinmoku, jikouteimei, S.setubiCd, S.cycleTime, S.dandoriLt*60 as setTime, su "
		            							+ "from temp "
		            							+ "left outer join m_accroad_hinmoku H on ko_hinmoku = H.hinmoku "
		            							+ "left outer join m_accroad_seisan S on ko_hinmoku = S.hinmoku "
		            							+ "UNION "
		            							+ "select 0 as Lv, '" + hinb + "' as oya_hinmoku,  oya_hinmoku as ko_hinmoku, '' as jikouteimei, C.setubiCd, C.cycleTime, C.dandoriLt*60 as setTime, A.su "
		            									+ "from m_accroad_kousei A "
		            									+ "left outer join m_accroad_hinmoku B on A.ko_hinmoku = B.hinmoku "
		            									+ "left outer join m_accroad_seisan C on A.ko_hinmoku = C.hinmoku where A.oya_hinmoku = '" + hinb + "'  "
		            											+ "ORDER by Lv asc ) A "
		            											+ "JOIN 0100hachu  on A.oya_hinmoku = hinb"
		            											+ " WHERE A.oya_hinmoku = '" + "' and ordb!=0" );
		            	//*********************/

            			//*********union*********
		            	PreparedStatement statement2 = connection.prepareStatement("select "
		            			+ "nhiz, ordb, setubiCd, kata, oya_hinmoku, ko_hinmoku, setTime, cycleTime, hsry, TRUNCATE(cycleTime*hsry + 0.05 ,1) as kousuu "
		            			+ "from ( "
		            			+ "SELECT '" + hinb + "' as oya_hinmoku, ko_hinmoku, jikouteimei, S.setubiCd, S.cycleTime, S.dandoriLt*60 as setTime "
		            					+ "FROM m_accroad_kousei "
		            					+ "left outer join m_accroad_hinmoku H on ko_hinmoku = H.hinmoku "
		            					+ "left outer join m_accroad_seisan S on ko_hinmoku = S.hinmoku "
		            					+ "WHERE ko_hinmoku like '" + hinb + '%' + "' UNION "
		            							+ "SELECT '" + hinb + "' as oya_hinmoku,  oya_hinmoku as ko_hinmoku, '' as jikouteimei, C.setubiCd, C.cycleTime, C.dandoriLt*60 as setTime "
		            									+ "FROM m_accroad_kousei A "
		            									+ "left outer join m_accroad_hinmoku B on A.ko_hinmoku = B.hinmoku "
		            									+ "left outer join m_accroad_seisan C on A.ko_hinmoku = C.hinmoku "
		            									+ "WHERE oya_hinmoku = '" + hinb + "' ) D "
		            											+ "JOIN 0100hachu  on D.oya_hinmoku = hinb"
		            											+ " WHERE D.oya_hinmoku = '" + hinb + "'  " );
		            	//*********************/

			            //statement2.setString(1,hinb);
			            ResultSet result2 = statement2.executeQuery();

			            while(result2.next()){
			            	PreparedStatement kousuu = connection.prepareStatement(INSERT_KOUSUU);
			            	kousuu.setDate  (1, result2.getDate("nhiz"));
			            	kousuu.setInt   (2, result2.getInt("ordb"));
			            	kousuu.setString(3, result2.getString("setubiCd"));
			            	kousuu.setString(4, result2.getString("kata"));
			            	kousuu.setString(5, result2.getString("oya_hinmoku"));
			            	kousuu.setString(6, result2.getString("ko_hinmoku"));
			            	kousuu.setInt   (7, result2.getInt("setTime"));
			            	kousuu.setInt   (8, result2.getInt("cycleTime"));
			            	kousuu.setInt   (9, result2.getInt("hsry"));
			            	kousuu.setInt   (10, result2.getInt("kousuu"));
			            	kousuu.setString(11, insertDay);
							kousuu.executeUpdate();

		            		System.out.println(i);
		                	i++;
			            }
		            }
            	}
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
		System.out.println("YamazumiDao getYamazumi100All 3=====");
		return yamazumi100All;
	}
	//************************************************************/

	//*****************プレス　山積　csv出力*******************
	public List<YamazumiBean>getYamazumi0100csv() throws SQLException {

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyyMMddHHmm");
        String today  = timeStampNowDay.format(nowTime);
        //日付表示ここまで

		List<YamazumiBean> yamazumi0100csv = new ArrayList<YamazumiBean>();
		Connection connection = source.getConnection();

		//t_fukaテーブル削除
		PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'C:/pleiades_1/workspace/siw_pa/WebContent/FUKA/FUKA_FILES/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'C:/users/see04nave/desktop/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'C:/temp/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'//192.168.101.236/riseプロジェクト/15.生産負荷計算/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		statementFukaCsv.executeQuery();

		//File srcFile = new File("C:/fuka/" + today + "_t_fuka.csv");
		//File desFile = new File("//192.168.101.236/riseプロジェクト/15.生産負荷計算/" + today + "_t_fuka.csv");
		//srcFile.renameTo(desFile);
		System.out.println("Yamazumi0100csv 登録終了3==  " + statementFukaCsv );
		return yamazumi0100csv;
	}

	//*****************プレス　未登録リスト　csv出力*******************
	public List<YamazumiBean>getMitouroku0100csv() throws SQLException {

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyyMMddHHmm");
        String today  = timeStampNowDay.format(nowTime);
        //日付表示ここまで

		List<YamazumiBean> mitouroku0100csv = new ArrayList<YamazumiBean>();
		Connection connection = source.getConnection();

		//t_fukaテーブル削除
		PreparedStatement statementMitourokuCsv = connection.prepareStatement("select *  from t_mitouroku into outfile \'C:/pleiades_1/workspace/siw_pa/WebContent/FUKA/FUKA_FILES/" + today + "_t_mitouroku.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		statementMitourokuCsv.executeQuery();
		System.out.println("Mitouroku0100csv 登録終了3==  " + statementMitourokuCsv );
		return mitouroku0100csv;
	}
}

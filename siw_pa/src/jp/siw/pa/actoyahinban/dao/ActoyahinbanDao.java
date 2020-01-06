package jp.siw.pa.actoyahinban.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.siw.pa.actoyahinban.bean.ActoyahinbanBean;

public class ActoyahinbanDao {
	private static final String DELETE_T_ACTOYAHINMOKU = "DELETE FROM t_act_oyahinmoku";
	private static final String DELETE_T_ACTOYAHINMOKU2 = "DELETE FROM t_act_oyahinmoku2";
	private static final String SELECT_HINBAN ="select * from t_act_hinmoku where tkcd= ?";
	private static final String INSERT ="insert into t_act_oyahinmoku values (?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ? )";
	private static final String INSERT2 ="insert into t_act_oyahinmoku2 values (?, ?, ?, ? ) "
			+ "on duplicate key update "
			+ " path=? ";
	private DataSource source;
	public ActoyahinbanDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
	}

	public List<ActoyahinbanBean>getActoyahinban(String tkcd) throws SQLException{

		List<ActoyahinbanBean> selectHinban = new ArrayList<ActoyahinbanBean>();
		Connection connection = source.getConnection();

		try{
			System.out.println("ActOyahinban 2====");
            PreparedStatement actOyahinmokuDelete = connection.prepareStatement(DELETE_T_ACTOYAHINMOKU);
            actOyahinmokuDelete.executeUpdate();
            PreparedStatement actOyahinmokuDelete2 = connection.prepareStatement(DELETE_T_ACTOYAHINMOKU2);
            actOyahinmokuDelete2.executeUpdate();

            System.out.println("ActOyahinban 3====");
            PreparedStatement statement = connection.prepareStatement(SELECT_HINBAN);
            statement.setString(1,tkcd);
            ResultSet result = statement.executeQuery();

            int i=1;
            while (result.next()) {

            	System.out.println("ActOyahinban 4====");
            	ActoyahinbanBean item = new ActoyahinbanBean();
            	item.setHinb(result.getString("hinb"));
            	String hinban = result.getString("hinb");

            	System.out.println("ActOyahinban 5====");
            	PreparedStatement oyaStatement = connection.prepareStatement("select * from (with recursive temp ( lv, hinb2, hinb1, seil, addseil, genti, gentz, su, path) "
            			+ "as (select 1 as Lv, PP.hinb2, PP.hinb1, PP.seil, PP.seil as addseil, PP.genti, PP.gentz, (PP.genti/PP.gentz) ,concat('0,', cast( hinb1 as char(4000))) as path "
            			+ "from m_kousei PP "
            			+ "where PP.hinb2 = ? UNION ALL select Lv+1, pp.hinb2, pp.hinb1, pp.seil, (t.addseil + pp.seil) as addseil, pp.genti, pp.gentz, (pp.genti/pp.gentz) , concat(t.path, ',', PP.hinb1) as path "
            			+ "from temp t, m_kousei pp "
            			+ "where pp.hinb2 = t.hinb1 ) select lv, hinb2, hinb1, h.thin, B.hinb, B.basy, h.hinm, h.syor, hgyc, kata, seil, addseil, h.tkcd, su, path "
            			+ "from temp "
            			+ "LEFT OUTER join t_hinmoku_all H on hinb1 = h.rcno "
            			+ "join m_hinbanhenkan B on hinb1= B.dhin "
            			+ "UNION "
            			+ "select 0 as lv, '' as hinb2, A.hinb as hinb1, A.hinb as thin, thin as hinb, B.basy, A.hinm, '' as syor, hgyc, kata, 0 as seil, 0 as addseil, A.tkcd, 0 as su, 0 as path "
            			+ "from t_hinmoku_all A "
            			+ "LEFT outer  join m_hinbanhenkan B on A.hinb= B.dhin "
            			+ "where A.hinb = ? ORDER by path asc) as AAA group by hinb order by path asc ");
                oyaStatement.setString(1,hinban);
            	oyaStatement.setString(2,hinban);
                ResultSet oyaResult = oyaStatement.executeQuery();
                System.out.println("ActOyahinban 6====");

                String A="";
                while (oyaResult.next()) {
                	PreparedStatement statement2 = connection.prepareStatement(INSERT);


            		if(oyaResult.getString("lv").equals("0")){
            			System.out.println(oyaResult.getInt("lv"));
            			A=oyaResult.getString("hinb");
            			System.out.println(oyaResult.getString("hinb"));
            			System.out.println("0の時" + A);
            		}else {
            			A=A + "," + oyaResult.getString("hinb");
            			System.out.println("0以外の時" + A);
            		}

                	statement2.setString(1, oyaResult.getString("lv"));
                	statement2.setString(2, oyaResult.getString("hinb2"));
                	statement2.setString(3, oyaResult.getString("hinb1"));
                	statement2.setString(4, oyaResult.getString("thin"));
                	statement2.setString(5, oyaResult.getString("hinb"));
                	statement2.setString(6, oyaResult.getString("basy"));
                	statement2.setString(7, oyaResult.getString("hinm"));
                	statement2.setString(8, oyaResult.getString("syor"));
                	statement2.setString(9, oyaResult.getString("hgyc"));
                	statement2.setString(10, oyaResult.getString("kata"));
                	statement2.setInt(11, oyaResult.getInt("seil"));
                	statement2.setInt(12, oyaResult.getInt("addseil"));
                	statement2.setString(13, oyaResult.getString("tkcd"));
                	statement2.setInt(14, oyaResult.getInt("su"));
                	statement2.setString(15, oyaResult.getString("path"));
                	statement2.executeUpdate();
					System.out.println("Yamazumidao insertCsv 2=satement2=== " + i + "個登録しました");

	                PreparedStatement statement3 = connection.prepareStatement(INSERT2);
	                statement3.setInt(1, i);
                	statement3.setString(2, oyaResult.getString("hinb"));
                	statement3.setString(3, oyaResult.getString("kata"));
	                statement3.setString(4, A);

	                statement3.setString(5, A);
                	statement3.executeUpdate();
                	System.out.println("Yamazumidao insertCsv 2=satement3=== " + A + "個登録しました");

                	statement2.close();
					statement3.close();
                }
                System.out.println(A);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
		return selectHinban;
	}

	//*****************csv出力*******************
	public List<ActoyahinbanBean>getActoyahinbancsv() throws SQLException {

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyyMMddHHmm");
        String today  = timeStampNowDay.format(nowTime);
        //日付表示ここまで

		List<ActoyahinbanBean> actoyahinbancsv = new ArrayList<ActoyahinbanBean>();
		Connection connection = source.getConnection();

		//t_fukaテーブル削除
		PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_act_oyahinmoku2 into outfile \'C:/pleiades/workspace/siw_pm/WebContent/FUKA/FUKA_FILES/" + today + "_t_act_oya.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'C:/users/see04nave/desktop/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'C:/temp/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		//PreparedStatement statementFukaCsv = connection.prepareStatement("select *  from t_fuka into outfile \'//192.168.101.236/riseプロジェクト/15.生産負荷計算/" + today + "_t_fuka.csv\' fields terminated by \',\' enclosed by \'\"\' ");
		statementFukaCsv.executeQuery();

		//File srcFile = new File("C:/fuka/" + today + "_t_fuka.csv");
		//File desFile = new File("//192.168.101.236/riseプロジェクト/15.生産負荷計算/" + today + "_t_fuka.csv");
		//srcFile.renameTo(desFile);
		System.out.println("Actoyahinban0100csv 登録終了3==  " + statementFukaCsv );
		return actoyahinbancsv;
	}


}


package jp.siw.pa.actoyahinban.util;

import java.util.List;

import jp.siw.pa.actoyahinban.bean.ActoyahinbanBean;

public class ActoyahinbanCast {

    @SuppressWarnings("unchecked")
    public static List <ActoyahinbanBean>castList(Object object) {
        return (List<ActoyahinbanBean>)object;
    }
}
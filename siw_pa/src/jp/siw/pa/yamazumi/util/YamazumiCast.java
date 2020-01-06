package jp.siw.pa.yamazumi.util;

import java.util.List;

import jp.siw.pa.yamazumi.bean.YamazumiBean;

public class YamazumiCast {

    @SuppressWarnings("unchecked")
    public static List <YamazumiBean>castList(Object object) {
        return (List<YamazumiBean>)object;
    }
}

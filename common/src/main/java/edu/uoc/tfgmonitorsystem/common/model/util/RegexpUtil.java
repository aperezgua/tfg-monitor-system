package edu.uoc.tfgmonitorsystem.common.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtil {

    private static Pattern DOUBLE_PATTERN = Pattern.compile("C=(\\d+[\\.\\d]*)");

    private RegexpUtil() {
        super();
    }

    /**
     * Obtiene el 1er nº decimalde un valor
     *
     * @param strValue
     * @return
     */
    public static final Double getDoubleFromString(String strValue) {
        Matcher matcher = DOUBLE_PATTERN.matcher(strValue);

        if (matcher.find()) {
            return Double.valueOf(matcher.group(0));
        }

        return null;
    }
}

package edu.uoc.tfgmonitorsystem.common.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class RegexpUtil {

    private static final Logger LOGGER = Logger.getLogger(RegexpUtil.class);

    private static Pattern DOUBLE_PATTERN = Pattern.compile("(\\d+[\\.\\d]*)");

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

        try {
            Matcher matcher = DOUBLE_PATTERN.matcher(strValue);

            if (matcher.find()) {
                return Double.valueOf(matcher.group(0));
            }
        } catch (Exception e) {
            LOGGER.error("Cannot parse double value", e);
        }

        return null;
    }
}

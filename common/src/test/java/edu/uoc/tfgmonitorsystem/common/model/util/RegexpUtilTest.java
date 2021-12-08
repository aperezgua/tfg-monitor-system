package edu.uoc.tfgmonitorsystem.common.model.util;

import org.junit.Assert;
import org.junit.Test;

public class RegexpUtilTest {

    @Test
    public void checkPatternDecimal() {
        Double doubleFromString = RegexpUtil.getDoubleFromString("fadsflja adfslkfjasd 123.43 fad sfdas fa 10");

        Assert.assertEquals(Double.valueOf(123.43D), doubleFromString);
    }

    @Test
    public void checkPatternNoDecimal() {
        Double doubleFromString = RegexpUtil.getDoubleFromString("fadsflja adfslkfjasd 123 fad fadsf 42");

        Assert.assertEquals(Double.valueOf(123D), doubleFromString);
    }
}

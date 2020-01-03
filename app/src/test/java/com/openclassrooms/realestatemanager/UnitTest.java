package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {


    @Test
    public void currencyConverterDollarToEuro_isCorrect() {

        int priceTest = 500000;
        int result = Utils.convertDollarToEuro(priceTest);

        assertEquals(452284, result);
    }

    @Test
    public void currencyConverterEuroToDollar_isCorrect() {

        int priceTest = 500000;
        int result = Utils.convertEuroToDollar(priceTest);

        assertEquals(552750, result);
    }

    @Test
    public void dateConverter_isCorrect() {

        String dateToConvert = "2018/01/15";
        String convertedDate = Utils.convertDate(dateToConvert);

        assertEquals("15/01/2018", convertedDate);
    }

    @Test
    public void formatDateForQuery_isCorrect() {

        String dateToConvert = "15/01/18";
        String convertedDate = Utils.formatDateForQuery(dateToConvert);

        assertEquals("20180115", convertedDate);
    }

}
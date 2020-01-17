package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars / 1.1055);
    }

    public static int convertEuroToDollar(int euros) {
        return (int) Math.round(euros * 1.1055);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    public static String convertDate(String dateToConvert) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = inputFormat.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputFormat.format(Objects.requireNonNull(date));
    }

    public static String formatDateForQuery(String dateToConvert) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;

        try {
            date = inputFormat.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputFormat.format(Objects.requireNonNull(date));
    }


    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isWifiAvailable(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return Objects.requireNonNull(wifi).isWifiEnabled();
    }

    public static Boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}

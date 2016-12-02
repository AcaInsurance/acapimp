package com.aca.dal;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;

import com.aca.database.DBA_MASTER_PRODUCT_SETTING;
import com.aca.database.DBA_PRODUCT_MAIN;
import com.aca.database.DBA_PRODUCT_OTOMATE;
import com.aca.dbflow.VersionAndroid;
import com.aca.util.Var;
import com.raizlabs.android.dbflow.sql.language.Select;

import static android.text.TextUtils.isEmpty;

/**
 * Created by marsel on 30/9/2016.
 */
public class Scalar {

    public static String getProdukName(Activity activity, String kodeProduk) {
        try {
            Cursor c;
            DBA_MASTER_PRODUCT_SETTING dba = new DBA_MASTER_PRODUCT_SETTING(activity);
            dba.open();
            c = dba.getRow(kodeProduk);
            if (!c.moveToFirst()) return "";

            String produkName = c.getString(c.getColumnIndex(DBA_MASTER_PRODUCT_SETTING.PRODUCT_NAME));
            return produkName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getProdukName(Activity activity, String productName, long sppaId) {
        try {
            if (productName.equalsIgnoreCase("OTOMATE")) {
                DBA_PRODUCT_OTOMATE dbOtomate = new DBA_PRODUCT_OTOMATE(activity);
                dbOtomate.open();
                Cursor cOtomate = dbOtomate.getRow(sppaId);
                if (!cOtomate.moveToFirst()) {
                    return productName;
                }
                String kodeOtomate = cOtomate.getString(cOtomate.getColumnIndex(DBA_PRODUCT_OTOMATE.KODE_PRODUK));
                String tempProductName = getProdukName(activity, kodeOtomate);
                productName = tempProductName.equals("") ? productName : tempProductName;

                dbOtomate.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return productName;
        }
    }

    public static String getProdukKode(Activity activity, long sppaId) {
        String kodeOtomate = "03";
        try {
            DBA_PRODUCT_OTOMATE dbOtomate = new DBA_PRODUCT_OTOMATE(activity);
            dbOtomate.open();
            Cursor cOtomate = dbOtomate.getRow(sppaId);
            if (!cOtomate.moveToFirst()) {
                return kodeOtomate;
            }
            kodeOtomate = cOtomate.getString(cOtomate.getColumnIndex(DBA_PRODUCT_OTOMATE.KODE_PRODUK));
            kodeOtomate = isEmpty(kodeOtomate) ? Var.OTOMATE : kodeOtomate;


            dbOtomate.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return kodeOtomate;
        }
    }

    public static boolean isOtomateSppa(Activity activity, long sppaId) {
        DBA_PRODUCT_MAIN dbMain = null;
        try {
            dbMain = new DBA_PRODUCT_MAIN(activity);

            dbMain.open();

            Cursor cMain = dbMain.getRow(sppaId);

            if (!cMain.moveToFirst()) return false;

            String productName = cMain.getString(cMain.getColumnIndex(DBA_PRODUCT_MAIN.PRODUCT_NAME));
            if (productName.equalsIgnoreCase("OTOMATE") || productName.equalsIgnoreCase("OTOMATESYARIAH"))
                return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbMain.close();
        }

        return false;
    }
    public static boolean isNewSppa(Activity activity, long sppaId) {
        DBA_PRODUCT_OTOMATE dbOtomate = null;

        try {
            dbOtomate = new DBA_PRODUCT_OTOMATE(activity);
            dbOtomate.open();
            Cursor cMain = dbOtomate.getRow(sppaId);
            if (!cMain.moveToFirst()) return false;


//            String entry = cMain.getString(cMain.getColumnIndex(DBA_PRODUCT_MAIN.ENTRY_DATE));
//            LocalDate entryDate = UtilDate.toDate(entry, UtilDate.STANDARD_DATE);

            VersionAndroid versionAndroid = new Select().from(VersionAndroid.class).querySingle();
            if (versionAndroid.Version >= 39) {
                String isNewSppa = cMain.getString(cMain.getColumnIndex(DBA_PRODUCT_OTOMATE.IS_NEW_SPPA));
                boolean isNew = TextUtils.isEmpty(isNewSppa) ? false : true;
                return isNew;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbOtomate.close();
        }
        return false;
    }


}

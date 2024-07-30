/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.util;

import bf.mfptps.detachementservice.exception.CustomException;
import com.google.zxing.WriterException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
public class AppUtil {

    public static String TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final String FR_SHORT_DATE_FORMAT = "dd/MM/yyyy";

    private static final DateFormat appFullMonthDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

    private static final DateFormat appShortDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

    public static final String DEFAULT_PASSWORD = "Def@ult%P@ssw0rd!2SpmaBg.2@23";

    public static final String appStoreRootPath = System.getProperty("user.home") + File.separatorChar + "sigepa-docs" + File.separatorChar;

    public static final String APPLICATION_NAME = "SIGEPA";

    private static final String APPLICATION_COPYRIGHT = "SIGEPA 2023";

    // EXTENSIONS AUTORISEES
    public static final String EXTENSION_PDF = ".pdf";

    public static final String EXTENSION_PNG = ".png";

    public static final String EXTENSION_JPG = ".jpg";

    public static final String EXTENSION_JPEG = ".jpeg";

    /**
     * CONVERTIR DATE EN FORMAT : 18 mai 2023
     *
     * @param date : date fournie
     * @return
     */
    public static String convertDatWithFullMonthToString(Date date) {
        if (null == date) {
            throw new CustomException("Veuillez une date pour la conversion SVP.");
        }
        return appFullMonthDateFormat.format(date);
    }

    /**
     * CONVERTIR DATE EN FORMAT : 18-05-2023
     *
     * @param date
     * @return
     */
    public static String convertDateToShort(Date date) {
        if (null == date) {
            throw new CustomException("Veuillez une date pour la conversion SVP.");
        }
        return appShortDateFormat.format(date);
    }

    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static int getCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    public static Object[] convertToDateFromData(int total, int index, int year) throws ParseException {
        Object[] result = new Object[3];
        DateFormat dateFormat = new SimpleDateFormat(FR_SHORT_DATE_FORMAT);
        int valeur = year - (total - index);

        String debutString = "01/01/" + valeur;
        String finString = "31/12/" + valeur;

        Date debut = dateFormat.parse(debutString);
        result[0] = debut;
        Date fin = dateFormat.parse(finString);
        result[1] = fin;
        result[2] = String.valueOf(valeur);
        return result;
    }

    public static String convertDateFormat(Date date) {
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDateStr = dmyFormat.format(date);
        return formattedDateStr;

    }

    /**
     * FONCTION DE CONSTRUCTION DU CODE QR
     *
     * @param mention
     * @return
     */
    public static Image constructQRCode(String mention) {
        try {
            String qrData = mention + " -- Généré(e) par " + APPLICATION_NAME + " © " + APPLICATION_COPYRIGHT + ".";
            return QRCodeGenerator.createQRAsImage(qrData, new HashMap<Object, Object>(), 130, 130);
        } catch (WriterException | IOException e) {
            log.debug("Impossible de générer le QR Code, WriterException :: {}", e.getMessage());
            return null;
        }
    }
}

package bf.mfptps.detachementservice.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppTools {

	private static final DateFormat appDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private static final DateFormat appShortDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat appFullMontDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

	private static final int demandeNumberFormatMaxLength = 8;

	private static InputStream logoStream;
	private static InputStream backStream;
	

	public static InputStream getAppDefaultLogo() {
		//File logo; 
		try {
			//logo = ResourceUtils.getFile("classpath:logo.png");
			//logo = new File(AppTools.class.getResource("/logo.png").toURI());
			logoStream = AppTools.class.getResourceAsStream("/logo.png"); ///BOOT-INF/classes
			//return new FileInputStream(logo);
			return logoStream;
		} catch (Exception e) {
			log.error("Error when loading default app logo", e);
			return null;
		}
	}
	
	public static InputStream getAppDefaultBackImage() {
		//File logo;
		try {
			//logo = ResourceUtils.getFile("classpath:backg.jpg");
			backStream = AppTools.class.getResourceAsStream("/asmap-bg.jpg");
			return backStream; //new FileInputStream(logo);
		} catch (Exception e) {
			log.error("Error when loading default app backbround image", e);
			return null;
		}
	} 

	public static String convertDateToString(Date date) {
		return appDateFormat.format(date);
	}

	public static String convertDateToShortString(Date date) {
		return appShortDateFormat.format(date);
	}
	
	public static String convertDatWithFullMonthToString(Date date) {
		return appFullMontDateFormat.format(date);
	}

	public static String generateNumero(int numero, long id, int year) {
		String value = "";

		String part = "";

		int firstPart = String.valueOf(numero).length();

		int lack = demandeNumberFormatMaxLength - firstPart;

		for (int k = 0; k < lack; k++) {
			part = part + "0";
		}

		value = id + "-" + year + "-" + part + numero;

		return value;
	}

	public static String correctTitre(String libelle, boolean directrice) {
		String value = "";
		int index = libelle.indexOf("Regionale");

		if (index == -1) {
			index = libelle.indexOf("Régionale");
		}

		if (index != -1) { 
			value = directrice ? "La Directrice Régionale " + libelle.substring(index + 9, libelle.length())
					: "Le Directeur Régional " + libelle.substring(index + 9, libelle.length());
		}

		return value;
	}

	public static Date correctToUpDate(Date date) {
		try {
			Date value = date == null ? new Date() : date;
			String str = appDateFormat.format(value);
			str = str.substring(0, 10);
			str = str + " 23:59:59";
			value = appDateFormat.parse(str);
			return value;	
		} catch (ParseException e) {
			log.error("BAD DATE FORMAT", e);
			return null;
		}
	}

	public static Date correctToLowDate(Date date) {
		try {
			Instant now = Instant.now();
			Instant defaut = now.minus(4, ChronoUnit.DAYS);

			Date value = date == null ?  Date.from(defaut) : date;
			String str = appDateFormat.format(value);
			str = str.substring(0, 10);
			str = str + " 00:00:00"; 
			value = appDateFormat.parse(str);
			return value;	
		} catch (ParseException e) {
			log.error("BAD DATE FORMAT", e);
			return null;
		}
	} 
	
	public static Date correctRealToLowDate(Date date) {
		try {
			Instant now = Instant.now();
			Instant defaut = now.minus(0, ChronoUnit.DAYS);

			Date value = date == null ?  Date.from(defaut) : date;
			String str = appDateFormat.format(value);
			str = str.substring(0, 10);
			str = str + " 00:00:00"; 
			value = appDateFormat.parse(str);
			return value;	
		} catch (ParseException e) {
			log.error("BAD DATE FORMAT", e);
			return null;
		}
	} 
}

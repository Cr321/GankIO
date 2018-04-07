package com.cr.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author RUI CAI
 * @date 2018/4/7
 */
public class TimeTransferUtil {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public static String unixToIso(long unix){
		Date time = new Date(unix);
		return format.format(time);
	}

	public static long unixAdd8(long unix){
		unix = unix +1000*60*60*8L;
		return unix;
	}

	public static Date isoToUtc(String iso) throws ParseException {
		return format.parse(iso);
	}


	public static  long isoToUnix(String iso) throws ParseException{
		Date time = format.parse(iso);
		long uinx = time.getTime();
		return uinx;
	}
}

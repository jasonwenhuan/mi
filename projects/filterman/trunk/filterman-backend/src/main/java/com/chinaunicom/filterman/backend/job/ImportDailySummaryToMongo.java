package com.chinaunicom.filterman.backend.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.chinaunicom.filterman.backend.ftp.FTPService;
import com.chinaunicom.filterman.core.bl.IZonemapphoneBL;
import com.chinaunicom.filterman.core.bl.impl.ZonemapphoneBL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.db.dao.ZonePayDailySumDao;
import com.chinaunicom.filterman.core.db.entity.ZonePayDailySumEntity;
import com.chinaunicom.filterman.utilities.Logging;
import com.csvreader.CsvReader;

/**
 * @author wenhuan
 * @date Dec 26, 2013
 * @time 5:15:51 PM
 */
public class ImportDailySummaryToMongo {
	protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext-backend.xml");

	@Autowired
	private IZonemapphoneBL zonemapphoneBL = (ZonemapphoneBL) ctx
			.getBean("zoneMapPhoneBL");

	@Autowired
	private ZonePayDailySumDao zonePayDailySumDao = (ZonePayDailySumDao) ctx
			.getBean("zonePayDailySumDao");

	@Autowired
	public FTPService ftp = (FTPService) ctx.getBean("ftpService");

	private Map<String, Integer> zoneSummaryCache = new HashMap<String, Integer>();

	private int dailySum = 0;

	public int currentRecord = 1;

	public static void main(String[] args) {
		ImportDailySummaryToMongo importer = new ImportDailySummaryToMongo();
		if (importer.downloadCsvFile()) {
			importer.importData();
		} else {
			Logging.logError("download csv file error !");
		}
		importer.removeOneWeekBeforeFiles();
	}

	public boolean downloadCsvFile() {
		return ftp.download();
	}

	public String formatCSVFilePath() {
		String downloadFilePath = ftp.getDownloadFilePath();
		Date now = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String datePrefix = dateformat.format(now);
		return downloadFilePath + datePrefix + "-dailysum.csv";
	}

	public Date getDateWithoutTimePart(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public String getZoneCodeByPhone(String phone) {
		String prefix = phone.substring(0, 7);
		return zonemapphoneBL.getZoneCodeWithPhone(prefix);
	}

	public float keep2Decimal(float source) {
		BigDecimal bg = new BigDecimal(source);
		Float f = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}

	public void removeOneWeekBeforeFiles() {
		String path = ftp.getDownloadFilePath();
		File directory = new File(path);
		String[] files = directory.list();

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");

		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);

		Date waitToRemove = getDateWithoutTimePart(calendar.getTime());
		try {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];
				String fileDate = fileName.substring(0, 8);

				Date realFileDate = dateformat.parse(fileDate);

				if (realFileDate.compareTo(waitToRemove) <= 0) {
					File file = new File(path + fileName);
					file.delete();
					System.out.println("delete " + fileName + " success!");
				}
			}
		} catch (ParseException e) {
			Logging.logError(
					"Doing ImportDailySummaryToMongo.removeOneWeekBeforeFiles erroe occured",
					e);
		}
	}

	public void importData() {
		CsvReader reader = null;
		String[] str;
		String zoneCode;
		Integer zoneSum;
		try {
			reader = new CsvReader(formatCSVFilePath());
			reader.readHeaders();
			while (reader.readRecord()) {
				str = reader.getValues();
				if (str != null && str.length > 0) {
					zoneCode = getZoneCodeByPhone(str[0]);
					if (zoneSummaryCache.get(zoneCode) != null) {
						zoneSum = zoneSummaryCache.get(zoneCode);
						zoneSum += Integer.parseInt(str[2]);
						zoneSummaryCache.put(zoneCode, zoneSum);
					} else {
						zoneSummaryCache
								.put(zoneCode, Integer.parseInt(str[2]));
					}
				}
				dailySum += Integer.parseInt(str[2]);
				currentRecord++;
			}
		} catch (FileNotFoundException e) {
			Logging.logError(
					"Doing ImportDailySummaryToMongo.importData error occured",
					e);
		} catch (Exception e) {
			Logging.logError(
					"Doing ImportDailySummaryToMongo.importData error occured",
					e);
		}

		System.out.println("have import " + currentRecord
				+ " to zoneSummaryCache");

		ZonePayDailySumEntity entity = new ZonePayDailySumEntity();
		// if csv file is confirmed of one day,we can use below
		Date today = getDateWithoutTimePart(new Date());
		float zoneRate;
		if (dailySum == 0) {
			Logging.logError("Date: " + getDateWithoutTimePart(new Date())
					+ " dailysum.csv dailysum value is 0 error,please check");
			return;
		}
		for (Entry<String, Integer> entry : zoneSummaryCache.entrySet()) {
			entity.setCreateDate(today);
			entity.setDailySum(dailySum);
			entity.setZoneCode(entry.getKey());
			entity.setZoneSum(entry.getValue());
			zoneRate = entry.getValue().floatValue() / dailySum * 100;
			entity.setZoneRate(keep2Decimal(zoneRate));
			zonePayDailySumDao.createZonePayDailySum(entity);
		}
	}
}

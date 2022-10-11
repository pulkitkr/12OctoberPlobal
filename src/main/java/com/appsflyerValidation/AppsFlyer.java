package com.appsflyerValidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.extent.ExtentReporter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metadata.ResponseInstance;
import com.opencsv.CSVReader;
import com.sun.mail.iap.ResponseInputStream;

public class AppsFlyer extends ExtentReporter {

	static String sheet = "KeyValue";
	static String xlpath = null;
	static int rownumber;
	private static String value;
	private static String key;

	static String showIDUpdate = null;
	static String packIDUpdate = null;
	static String musicIDUpdate = null;
	
	public static Properties expectedData = new Properties();

	static ExtentReporter extent = new ExtentReporter();

	public void deleteOldAppsFlyerFiles() {
		File directory = new File(System.getProperty("user.dir") + File.separator + "AppsflyerReport");
		directory.mkdir();
		int fileCount = directory.list().length;
		System.out.println("File Count:" + fileCount);

		for (int i = 1; i <= fileCount; i++) {
			Path parentFolder = Paths.get(System.getProperty("user.dir") + File.separator + "AppsflyerReport");

			Optional<File> mostRecentFile = Arrays.stream(parentFolder.toFile().listFiles())
					.max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
			String fileName = null;
			if (mostRecentFile.isPresent()) {
				File mostRecent = mostRecentFile.get();
				fileName = mostRecent.getPath();
				System.out.println("most recent is " + mostRecent.getPath());
				// Delete file
				File myObj = new File(fileName);
				if (myObj.delete()) {
					System.out.println("Deleted the file: " + myObj.getName());
				} else {
					System.out.println("Failed to delete the file.");
				}
			} else {
				System.out.println("folder is empty!");
			}
		}
	}

	
	
	public void deleteOldAppsFlyerEventExcelFiles() {
		File directory = new File(System.getProperty("user.dir") + "//eventsReport");
		directory.mkdir();
		int fileCount = directory.list().length;
		System.out.println("File Count:" + fileCount);

		for (int i = 1; i <= fileCount; i++) {
			Path parentFolder = Paths.get(System.getProperty("user.dir") + "//eventsReport");

			Optional<File> mostRecentFile = Arrays.stream(parentFolder.toFile().listFiles())
					.max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
			String fileName = null;
			if (mostRecentFile.isPresent()) {
				File mostRecent = mostRecentFile.get();
				fileName = mostRecent.getPath();
				System.out.println("most recent is " + mostRecent.getPath());
				// Delete file
				File myObj = new File(fileName);
				if (myObj.delete()) {
					System.out.println("Deleted the file: " + myObj.getName());
				} else {
					System.out.println("Failed to delete the file.");
				}
			} else {
				System.out.println("folder is empty!");
			}
		}
	}

	
	
	public String fetchTheDownloadedAppsFlyerReportName() {

		Path parentFolder = Paths.get(System.getProperty("user.dir") + "//AppsflyerReport");

		Optional<File> mostRecentFile = Arrays.stream(parentFolder.toFile().listFiles())
				.max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
		String filePath = null;
		String fileName = null;
		if (mostRecentFile.isPresent()) {
			File mostRecent = mostRecentFile.get();
			filePath = mostRecent.getPath();
			fileName = mostRecent.getName();
			System.out.println("most file recent is " + mostRecent.getName());

		} else {
			System.out.println("folder is empty!");
		}

		return fileName;
	}

	public static final char FILE_DELIMITER = ',';
	public static final String FILE_EXTN = ".xlsx";
	public static final String FILE_NAME = "EXCEL_DATA";

	public String convertCsvToXlsxReport(String downloadedFileName) {
		SXSSFSheet sheet = null;
		CSVReader reader = null;
		Workbook workBook = null;
		String generatedXlsxReportFilePath = "";
		FileOutputStream fileOutputStream = null;

		try {

			/****
			 * Get the CSVReader Instance & Specify The Delimiter To Be Used
			 ****/
			String[] nextLine;
			reader = new CSVReader(new FileReader(
					System.getProperty("user.dir") + File.separator + "AppsflyerReport" + "\\" + downloadedFileName),
					FILE_DELIMITER);

			workBook = new SXSSFWorkbook();
			sheet = (SXSSFSheet) workBook.createSheet("Sheet");

			int rowNum = 0;
			// logger.info("Creating New .Xls File From The Already Generated
			// .Csv File");
			System.out.println("Creating New .Xls File From The Already Generated .Csv File");
			while ((nextLine = reader.readNext()) != null) {
				Row currentRow = sheet.createRow(rowNum++);
				for (int i = 0; i < nextLine.length; i++) {
					if (NumberUtils.isDigits(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
					} else if (NumberUtils.isNumber(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
					} else {
						currentRow.createCell(i).setCellValue(nextLine[i]);
					}
				}
			}

			generatedXlsxReportFilePath = System.getProperty("user.dir") + "//AppsflyerConvertedReport"
					+ "\\" + FILE_NAME + FILE_EXTN;
			// logger.info("The File Is Generated At The Following Location?= "
			// + generatedXlsFilePath);
			System.out.println("The File Is Generated At The Following Location?= " + generatedXlsxReportFilePath);
			fileOutputStream = new FileOutputStream(generatedXlsxReportFilePath.trim());
			workBook.write(fileOutputStream);
		} catch (Exception exObj) {
			// logger.error("Exception In convertCsvToXls() Method?= " + exObj);
			System.out.println("Exception In convertCsvToXls() Method?=  " + exObj);
		} finally {
			try {

				/**** Closing The Excel Workbook Object ****/
				workBook.close();

				/**** Closing The File-Writer Object ****/
				fileOutputStream.close();

				/**** Closing The CSV File-ReaderObject ****/
				reader.close();
			} catch (IOException ioExObj) {
				System.out.println("Exception While Closing I/O Objects In convertCsvToXls() Method?=  " + ioExObj);
				// logger.error("Exception While Closing I/O Objects In
				// convertCsvToXls() Method?= " + ioExObj);
			}
		}

		return generatedXlsxReportFilePath;
	}

	public static void ExtractEventSpecificData(String userType, String idNumber,String Event) throws IOException, ParseException {

		
		System.out.println("=======================================================");
		System.out.println("SEARCH EVENT : " +Event);
		System.out.println("=======================================================");
		
		fetchEventValueFromAppsFlyerReport(Event, "");
		validate(userType,idNumber,Event, "");


	}

	public static void fetchEventValueFromAppsFlyerReport(String Event, String tab) throws IOException {
		String EventValueString = null;
		ArrayList<String> mpparameters = new ArrayList<String>();
		InputStream ExcelFileToRead = new FileInputStream(System.getProperty("user.dir") + "//AppsflyerConvertedReport" + "\\" + "EXCEL_DATA" + ".xlsx");

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		int starRow = sheet.getFirstRowNum();
		System.out.println("Row start : " + starRow);
		int endRow = sheet.getLastRowNum();
		System.out.println("Row end : " + endRow);

		int i = 1;
		int j = 1;
		String tabValue = null;
		//String screenValue = null;
		int RowOfTheEvent = 0;
		xlpath = null;



		try {
			flow: while (rows.hasNext()) {
				XSSFCell EventName = sheet.getRow(i).getCell(4);
				System.out.println("Event Name : " + EventName);

				if (EventName.getStringCellValue().contains(Event)) {

					
						System.out.println("EVENT PRESENT..");
						RowOfTheEvent = i;
						XSSFCell EventValue = sheet.getRow(i).getCell(5);
						System.out.println(EventValue);
						EventValueString = EventValue.toString();
						break;

				}else{
					System.out.println("Event not present");
				}
				i++;
			}
//			if(!EventValueString.equals("{}")){
			// CODE TO CONVERT THE JSON VALUE TO KEY VALUE
			JsonObject obj = new JsonParser().parse(EventValueString).getAsJsonObject();

			obj.keySet().forEach(keyStr -> {
				Object keyvalue = obj.get(keyStr);
				System.out.println("key: " + keyStr + " value: " + keyvalue);
				mpparameters.add(keyStr.replace("\"", "").replace("$", "") + "keyvalue"
						+ keyvalue.toString().replace("\"", "").replace("$", "").replace(",", "-").trim());
			});

			System.out.println(mpparameters);
			System.out.println(" - - - - - -");
			// GET COLUMN VALUE
			String[] eventsColumnValue = { "Country Code", "OS Version", "State", "City", "IP", "Language", "Device Type", "App Version", "App ID"};

			for (int h = 0; h < eventsColumnValue.length; h++) {

				String columnVaue = eventsColumnValue[h];
				row = sheet.getRow(0);
				String cellValue;
				int EventPresentAtColumn = 0;
				for (int jk = 0; jk < 200; jk++) {
					cellValue = row.getCell(jk).getStringCellValue();

					if (cellValue.contains(columnVaue)) {
						// System.out.println("Column Present");
						// System.out.println("Getting Column value");
						EventPresentAtColumn = jk;
						// System.out.println("EventValue is present at Column
						// number : "+EventPresentAtColumn);
						break;
					}
				}
				// System.out.println("RowOfTheEvent : "+RowOfTheEvent);
				// System.out.println("EventPresentAtColumn :
				// "+EventPresentAtColumn);
				System.out.println(columnVaue + " : " + sheet.getRow(RowOfTheEvent).getCell(EventPresentAtColumn));

				mpparameters.add(columnVaue + "keyvalue" + sheet.getRow(RowOfTheEvent).getCell(EventPresentAtColumn));
			}

			System.out.println(mpparameters);

			parseResponse(mpparameters, Event, tabValue);
//			}
		} catch (Exception e) {
		
		}
		
	}

	public static void parseResponse(ArrayList<String> response, String fileName, String tab) {
		String file = fileName + tab;
		if (fileName.contains("Tab_View")) {
			creatExcel(file); // Create an excel file
			for (int i = 0; i < response.size(); i++) {
				write(i, response.get(i).split("keyvalue")[0], response.get(i).split("keyvalue")[1], file);
			}
		} else if (fileName.contains("screen_view")) {
			creatExcel(file); // Create an excel file
			for (int i = 0; i < response.size(); i++) {
				write(i, response.get(i).split("keyvalue")[0], response.get(i).split("keyvalue")[1], file);
			}
		} else {
			creatExcel(fileName); // Create an excel file
			for (int i = 0; i < response.size(); i++) {
				write(i, response.get(i).split("keyvalue")[0], response.get(i).split("keyvalue")[1], fileName);
			}
		}
	}

	/**
	 * Function to create excel file of format .xlsx Function to create sheet
	 */
	public static void creatExcel(String fileName) {
		try {
			xlpath = System.getProperty("user.dir") + "//eventsReport" + "\\" + fileName + ".xlsx";
			File file = new File(xlpath);
			if (!file.exists()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				workbook.createSheet(sheet); // Create sheet
				FileOutputStream fos = new FileOutputStream(new File(xlpath));
				workbook.write(fos);
				workbook.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void write(int i, String key, String value, String fileName) {
		String xlpath = System.getProperty("user.dir") + "//eventsReport" + "\\" + fileName + ".xlsx";
		try {
			XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(xlpath));
			FileOutputStream output = new FileOutputStream(xlpath);
			XSSFSheet myExcelSheet = myExcelBook.getSheet(sheet);
			XSSFRow row = myExcelSheet.createRow(i);
			if (row == null) {
				row = myExcelSheet.createRow(i); // create row if not created
			}
			row.createCell(0).setCellValue(key); // write parameter key into
													// excel into first column
			row.createCell(1).setCellValue(value); // write parameter value into
													// excel second column
			myExcelBook.write(output);
			myExcelBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillCellColor() {
		try {
			XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(xlpath));
			XSSFSheet myExcelSheet = myExcelBook.getSheet(sheet);
			Row Cellrow = myExcelSheet.getRow(rownumber);
			XSSFCellStyle cellStyle = myExcelBook.createCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			cellStyle.setFillPattern(FillPatternType.FINE_DOTS);
			if (Cellrow.getCell(0) == null) {
				Cellrow.createCell(0);
			}
			Cell cell1 = Cellrow.getCell(0);
			cell1.setCellStyle(cellStyle);
			FileOutputStream fos = new FileOutputStream(new File(xlpath));
			myExcelBook.write(fos);
			fos.close();
		} catch (Exception e) {
		}
	}
	
	
	public static void fillCellColorGreen() {
		try {
			XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(xlpath));
			XSSFSheet myExcelSheet = myExcelBook.getSheet(sheet);
			Row Cellrow = myExcelSheet.getRow(rownumber);
			XSSFCellStyle cellStyle = myExcelBook.createCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			cellStyle.setFillPattern(FillPatternType.FINE_DOTS);
			if (Cellrow.getCell(0) == null) {
				Cellrow.createCell(0);
			}
			Cell cell1 = Cellrow.getCell(0);
			cell1.setCellStyle(cellStyle);
			FileOutputStream fos = new FileOutputStream(new File(xlpath));
			myExcelBook.write(fos);
			fos.close();
		} catch (Exception e) {
		}
	}
	
	

	/**
	 * Get Row count
	 */
	// Generic method to return the number of rows in the sheet.
	public static int getRowCount() {
		int rc = 0;
		try {
			System.out.println(xlpath);
			FileInputStream fis = new FileInputStream(xlpath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheet);
			rc = s.getLastRowNum();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("no file generated");
		}
		return rc;
	}

	public static void validation(String Event,String tab,String userType) {
		int NumberOfRows = getRowCount();
		System.out.println(NumberOfRows);
		
		if(NumberOfRows!=0){
			
		System.out.println("xlPath : "+xlpath);

		String file = Event + tab;
		if(Event.contains("Tab_View")){
			extent.HeaderChildNode(file +" Parameter Validation");
		}else if(Event.contains("screen_view")){
			extent.HeaderChildNode(file +" Parameter Validation");
		}else {
			extent.HeaderChildNode(Event +" Parameter Validation");
		}
		
		
		
		
		if (NumberOfRows != 0) {
			for (rownumber = 0; rownumber <= NumberOfRows; rownumber++) {
				try {
					XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(xlpath));
					XSSFSheet myExcelSheet = myExcelBook.getSheet(sheet);

					key = myExcelSheet.getRow(rownumber).getCell(0).toString();
					value = myExcelSheet.getRow(rownumber).getCell(1).toString();

					if (value.trim().isEmpty()) {
						System.out.println("Paramter is empty :- Key:" + key + " - value" + value);
						extent.extentLoggerFail("Empty parameter",
								"Paramter is empty :- <b>Key : " + key + " \n value : " + value + "</b>");
						fillCellColor();
					} else if(key.contains("User Type")){
						
						String expectedValue = expectedData.getProperty(key);

						System.out.println("Key: " + key + " - value: " + value+" - Expected Data: "+expectedValue);
						if (expectedValue != null) {
								
							
							if(userType.contains("Guest")){
								if (expectedValue.toString().contains("Guest") || expectedValue.toString().contains("Free")) {
									fillCellColorGreen();
									extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
											+ value + "<br/>Expected Value : " + "Guest / Free" + "</b>");
							}else{
								fillCellColor();
								extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
								}
							}else if(userType.contains("NonSubscribedUser")){
								if (expectedValue.toString().contains("Registered")) {
									fillCellColorGreen();
									extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
											+ value + "<br/>Expected Value : " + "Registered" + "</b>");
									
								}else{
									fillCellColor();
									extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
											+ value + "<br/>Expected Value : " + expectedValue + "</b>");
								}
							}else if(userType.contains("SubscribedUser")){
								if (expectedValue.toString().contains("Premium") || expectedValue.toString().contains("Expired")) {
									fillCellColorGreen();
									extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
											+ value + "<br/>Expected Value : " + "Premium / Expired" + "</b>");
									
								}else{
									fillCellColor();
									extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
											+ value + "<br/>Expected Value : " + expectedValue + "</b>");
								}
							}
							
							
							if (!expectedValue.toString().equalsIgnoreCase(value.toString())) {
								fillCellColor();
								extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
							}else{
								fillCellColorGreen();
								extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
							}
						}
						
					} else if(key.contains("Current Subscription")){
						String expectedValue = expectedData.getProperty(key);

						System.out.println("Key: " + key + " - value: " + value+" - Expected Data: "+expectedValue);
						if (expectedValue != null) {
							if (expectedValue.toString().contains("N/A") || expectedValue.toString().contains("false")) {
								fillCellColorGreen();
								extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + "false / N/A" + "</b>");
								
							}else{
								fillCellColor();
								extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
							}
						}
					}else {
						String expectedValue = expectedData.getProperty(key);

						System.out.println("Key: " + key + " - value: " + value+" - Expected Data: "+expectedValue);
						if (expectedValue != null) {
														
							if (!expectedValue.toString().equalsIgnoreCase(value.toString())) {
								fillCellColor();
								extent.extentLoggerFail("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
							}else{
								fillCellColorGreen();
								extent.extentLoggerPass("Parameter", "Parameter : <b>Key : " + key + " <br/> Actual value : "
										+ value + "<br/>Expected Value : " + expectedValue + "</b>");
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} else {
			System.out.println("Event not triggered");
			//extent.extentLoggerWarning("Event not triggered", "Event not triggered");
		}
		expectedData.clear();
		
		}else{
			System.out.println("No file");
		}

	}

	
	
	public static void main(String[] args) throws IOException, ParseException{
//		ExtractEventSpecificData("NonSubscribedUser","8234","af_complete_registration");
//		
//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_recording_ended");
//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_recording_started");
//
//		ExtractEventSpecificData("NonSubscribedUser","8234","user_followed");
//		ExtractEventSpecificData("NonSubscribedUser","8234","comment_added");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_liked");
//		
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_shared");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_shared_5");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_shared_10");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_shared_25");
//		
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_play");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_played_5");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_played_10");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_played_25");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_played_50");
//		ExtractEventSpecificData("NonSubscribedUser","8234","ugc_played_complete");
//		
//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_upload_result");

//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_upload_result_5");
//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_upload_result_10");
//		ExtractEventSpecificData("NonSubscribedUser","8234","clip_upload_result_25");
		
	
		
		ExtractEventSpecificData("Guest","3766951513","ugc_play");
		ExtractEventSpecificData("Guest","3766951513","ugc_played_10");
		ExtractEventSpecificData("Guest","3766951513","ugc_played_20");
		ExtractEventSpecificData("Guest","3766951513","ugc_played_25");
		ExtractEventSpecificData("Guest","3766951513","ugc_played_50");
		ExtractEventSpecificData("Guest","3766951513","ugc_played_complete");
		
		
		
		
		
	}
	
	
	
	
	
	public void extractFilesForValidationFunction(String userType, String idNumber,String event) throws IOException, ParseException{
		
		
		System.out.println("------------------------------------------------------");
		ExtractEventSpecificData(userType,idNumber,event);
		System.out.println("------------------------------------------------------");

	}
	
	
	public static void validate(String userType, String idNumber,String event,String tab) throws IOException, ParseException{
		String userValue = null;
		if(userType.equals("Guest")){
		//	userValue = "Free";
			userValue = "guest";
		}else if(userType.equals("NonSubscribedUser")){
			userValue = "Registered";
		}else{
			userValue = "Premium";
		}
		
		String topnavtab = null;
		String tabname = null;

		
		ResponseInstance.fetchExpectedDataforAppsFlyer(event,userType,idNumber,userValue,topnavtab,tabname,showIDUpdate,packIDUpdate,musicIDUpdate);
		System.out.println("------------------------------------------------------");
		validation(event,tab,userType);
	}
	
	

}

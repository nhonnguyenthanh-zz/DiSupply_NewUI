package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

import config.Constants;
import executionEngine.DriverScript;
    public class ExcelUtils {
                private static XSSFSheet ExcelWSheet;
                public static XSSFWorkbook ExcelWBook;
                private static org.apache.poi.ss.usermodel.Cell Cell, Cell_totalTCExec, Cell_totalTCPassed, Cell_totalTCFailed;
                private static XSSFRow Row;
                public static FileInputStream ExcelFile;
                //private static XSSFRow Row;

            public static void setExcelFile(String Path) throws Exception {
            	try {
                    ExcelFile = new FileInputStream(Path);
                    ExcelWBook = new XSSFWorkbook(ExcelFile);
            	} catch (Exception e){
            		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
            		DriverScript.bResult = false;
                	}
            	}
            
            public static void closeExcelFile(String Path)
            {
            	try {
            		FileOutputStream fileOut = new FileOutputStream(Path);
                    ExcelUtils.ExcelWBook.write(fileOut);
                    ExcelUtils.ExcelWBook.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
            }

            public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
                try{
                	String CellData=null;
                	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                   	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                   	if(Cell!=null)
                   	{
                    CellData = Cell.getStringCellValue().toString();
                    return CellData;
                   	}
                   	else {
						return CellData;
					}
                   	
                 }catch (Exception e){
                     Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
                     DriverScript.bResult = false;
                     return"";
                     }
                 }

        	public static int getRowCount(String SheetName){
        		int iNumber=0;
        		try {
        			ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			iNumber=ExcelWSheet.getLastRowNum()+1;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iNumber;
        		}

        	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
        		int iRowNum=0;	
        		try {
        		    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			int rowCount = ExcelUtils.getRowCount(SheetName);
        			for (; iRowNum<rowCount; iRowNum++){
        				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
        					break;
        				}
        			}       			
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iRowNum;
        		}

        	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
        		try {
	        		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
	        			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
	        				int number = i;
	        				return number;      				
	        				}
	        			}
	        		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	        		int number=ExcelWSheet.getLastRowNum()+1;
	        		return number;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			return 0;
                }
        	}

        	@SuppressWarnings("static-access")
        	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
                   try{

                	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
                       Row  = ExcelWSheet.getRow(RowNum);
                       Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
                       if (Cell == null) {
                    	   Cell = Row.createCell(ColNum);
                    	   Cell.setCellValue(Result);
                        } else {
                            Cell.setCellValue(Result);
                        }
                       ExcelUtils.closeExcelFile(Constants.Path_TestData);
                         //FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
                         //ExcelWBook.write(fileOut);
                         //fileOut.flush();
                         //fileOut.close();
                         ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
                     }catch(Exception e){
                    	 DriverScript.bResult = false;

                     }
                }
        	public static void reportTestCase(int totalTCExec, int totalTCFailed, int totalTCPassed, String sheetName, String path)
            {
                try
                {
                    Log.info("Run Report Test Case");
                    ExcelWSheet = ExcelWBook.getSheet(sheetName);
                    Cell_totalTCExec = ExcelWSheet.getRow(0).getCell(1);
                    Cell_totalTCExec.setCellValue(totalTCExec);
                    Cell_totalTCPassed = ExcelWSheet.getRow(1).getCell(1);
                    Cell_totalTCPassed.setCellValue(totalTCPassed);
                    Cell_totalTCFailed = ExcelWSheet.getRow(2).getCell(1);
                    Cell_totalTCFailed.setCellValue(totalTCFailed);

                    ExcelUtils.closeExcelFile(path);
                    Log.info("Report Successfully");
                }
                catch (Exception ex)
                {
                    Log.error("Class Utils | Method reportTestCase | Exception desc : " + ex);
                }
            }
            public static int totalTestCaseFail(int colResult, int colRunMode,String SheetName)
            {
                int count = 0;
                try
                {
                    for (int i=1 ; i <= ExcelUtils.getRowCount(SheetName); i++)
                    {
                        String result = ExcelUtils.getCellData(i, colResult, SheetName).toLowerCase().toString();
                        String runMode = ExcelUtils.getCellData(i, colRunMode, SheetName).toLowerCase().toString();
                        if (runMode.equals("yes") && result.equals("fail"))
                            count++;
                    }
                    return count;
                }
                catch (Exception ex)
                {
                    Log.error("Class Utils | Method totalTestCaseFail | Exception desc : " + ex);
                    return 0;
                }
            }

            public static int totalTestCasePass(int colResult, int colRunMode, String SheetName)
            {
                int count = 0;
                try
                {
                    for (int i = 1; i <= ExcelUtils.getRowCount(SheetName); i++)
                    {
                        String result = ExcelUtils.getCellData(i, colResult, SheetName).toLowerCase().toString();
                        String runMode = ExcelUtils.getCellData(i, colRunMode, SheetName).toLowerCase().toString();
                        if (runMode.equals("yes") && result.equals("pass"))
                            count++;
                    }
                    return count;
                }
                catch (Exception ex)
                {
                    Log.error("Class Utils | Method totalTestCaseFail | Exception desc : " + ex);
                    return 0;
                }
            }

            public static int totalTestCaseExecute(int colRunMode, String SheetName)
            {
                int count = 0;
                try
                {
                    for (int i = 1; i <= ExcelUtils.getRowCount(SheetName); i++)
                    {
                        String runMode = ExcelUtils.getCellData(i, colRunMode, SheetName).toLowerCase().toString();
                        if (runMode.equals("yes"))
                            count++;
                    }
                    return count;
                }
                catch (Exception ex)
                {
                    Log.error("Class Utils | Method totalTestCaseExecute | Exception desc : " + ex);
                    return 0;
                }
            }
            public static void setColorRowFailed(int RowNum, String SheetName) throws Exception
            {
            	try {
            		ExcelWSheet = ExcelWBook.getSheet(SheetName);
            		XSSFRow row = ExcelWSheet.getRow(RowNum);
            		XSSFCellStyle cellStyle = row.getRowStyle();
            		cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
            }

    	}
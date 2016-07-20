package config;

public class Constants {

	//System Variables
	public static final String URL = "http://disupply.dicentral.com.vn/DiSupply";
	public static final String Path_TestData = "src/dataEngine/DataEngine.xlsx";
	public static final String Path_OR = "src/config/ObjectRepository.properties";
	public static final String File_TestData = "DataEngine.xlsx";
	public static final String Path_FileReport = "src/report/Report.xlsx";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";

	//Data Sheet Column Numbers in Sheet TestCases
    public static int Col_TestCaseID = 0;
    public static int Col_Feature = 1;
    public static int Col_Title = 2;
    public static int Col_RunMode = 3;
    public static int Col_Result = 4;

    //Data Sheet Column Numbers in Sheet Features
    public static int Col_PageObject = 4;
    public static int Col_ActionKeyword = 5;
    public static int Col_DataSet = 6;
    public static int Col_TestStepResult = 7;
    public static int Col_Description = 2;

    //Data Sheet Column Numbers in Sheet DataTest
    //public static int Col_ProductName = 5;
    //public static int Col_Quantity = 6;
    //public static int Col_UoM = 7;

	// Data Engine Excel sheets
	//public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	//public static final String Sheet_TestData = "Test Data";
	
	public static final String Sheet_Report = "Sheet1";

}
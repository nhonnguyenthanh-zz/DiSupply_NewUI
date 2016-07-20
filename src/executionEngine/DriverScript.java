package executionEngine;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import utility.ExcelUtils;
import utility.Log;
import config.ActionKeywords;
import config.Constants;
import config.RespositoryParser;

public class DriverScript {

	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static Class<?> class1[];
	public static String sPageObject;
	public static Method method[];
	
	public static int iTestStep;
	public static int iTestLastStep;
	//public static int iTestDataStep;
	//public static int iTestDataLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sFeature;
	public static String sData;
	//public static String productName;
	//public static String quantity;
	//public static String UoM;
	public static boolean bResult;
	public static boolean bSkip;
	//public static List<addItemsFromExel> itemsResult = new ArrayList<DriverScript.addItemsFromExel>();

 
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
		
	}
 
    public static void main(String[] args) throws Exception {
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	
    	//open file object repository
    	String Path_OR = Constants.Path_OR;
    	new RespositoryParser(Path_OR);
    	
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
		
		int totalTCExec = ExcelUtils.totalTestCaseExecute(Constants.Col_RunMode, Constants.Sheet_TestCases);
		int totalTCFailed = ExcelUtils.totalTestCaseFail(Constants.Col_Result, Constants.Col_RunMode, Constants.Sheet_TestCases);
		int totalTCPassed = ExcelUtils.totalTestCasePass(Constants.Col_Result, Constants.Col_RunMode, Constants.Sheet_TestCases);
		
		ExcelUtils.closeExcelFile(Constants.Path_TestData);
		//open file excel report
		ExcelUtils.setExcelFile(Constants.Path_FileReport);
		ExcelUtils.reportTestCase(totalTCExec, totalTCFailed, totalTCPassed, Constants.Sheet_Report,Constants.Path_FileReport);
 
    }
 
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				bResult = true;
				bSkip = false;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				sFeature = ExcelUtils.getCellData(iTestcase, Constants.Col_Feature,Constants.Sheet_TestCases);
				if (sRunMode.equalsIgnoreCase("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sFeature);
					iTestLastStep = ExcelUtils.getTestStepsCount(sFeature, sTestCaseID, iTestStep);
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword, sFeature);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sFeature);
			    		//if(sActionKeyword.equals("inputItems")){
			    		//	iTestDataStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Col_Feature);
							//iTestDataLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestData, sTestCaseID, iTestDataStep);
						//	action_InputItems(iTestDataStep, iTestDataLastStep);
			    		//}
			    		//else{
			    			sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sFeature);
			    			execute_Actions();
			    		
						if(bResult==false){
							if(bSkip==true){
								ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							}
							else {
								ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
								Log.endTestCase(sTestCaseID);
								break;
								}
							}						
						}
					if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
						}					
					}
				}
    }
 
     private static void execute_Actions() throws Exception {
    	 try {
    		 for(int i=0;i<method.length;i++){
 				if(method[i].getName().equals(sActionKeyword)){
 					method[i].invoke(actionKeywords,sPageObject, sData);
 					if(bResult==true){
 						ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, sFeature);
 					}else{
 						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, sFeature);
 						ActionKeywords.closeBrowser("","");
 						break;
 						}
 					}
 			}
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
     }
     
     /*
     public static class addItemsFromExel
 	{
 		public String requestQty;
 		public String purchaseUoM;
 		public String name;
 	}
     
     
     public void action_InputItems(Integer iTestDataStep, Integer iTestDataLastStep) throws Exception{
    	 
    	 //iTestDataStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestData);
    	 //iTestDataLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestData, sTestCaseID, iTestDataStep);
    	 bResult=true;
    	 for (;iTestDataStep<iTestDataLastStep;iTestDataStep++){
    		 productName = ExcelUtils.getCellData(iTestDataStep, Constants.Col_ProductName, Constants.Sheet_TestData);
    		 quantity = ExcelUtils.getCellData(iTestDataStep, Constants.Col_Quantity, Constants.Sheet_TestData).toString();
    		 UoM = ExcelUtils.getCellData(iTestDataStep, Constants.Col_UoM, Constants.Sheet_TestData);
    		 addItemsFromExel item = new addItemsFromExel();
    		 item.name = productName;
    		 item.requestQty = quantity;
    		 item.purchaseUoM = UoM;
    		 itemsResult.add(item);
    	 }
    	 execute_Actions();
    	 
     }
     */
	
}

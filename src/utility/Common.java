package utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common {
	//public static WebDriver driver;
	public static boolean isPresentAlert(WebDriver driver){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
}

package modules;

import org.openqa.selenium.By;

import java.io.FileWriter;
import com.opencsv.CSVWriter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\aditi.inamdar\\Downloads\\chromedriver_win32\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("https://jira.community.veritas.com/login.jsp");
			driver.findElement(By.id("login-form-username")).sendKeys("Aditi.Inamdar");
			driver.findElement(By.id("login-form-password")).sendKeys("p#6DD6MFFW7q");
			driver.findElement(By.id("login-form-submit")).click();
			driver.get("https://jira.community.veritas.com/browse/CORTEX-235");
			driver.findElement(By.id("viewissue-export")).click();
			driver.findElement(By.id("jira.issueviews:issue-html")).click();
			String s = driver.findElement(By.id("customfield_10117-985906-value")).getText();
			System.out.print(s);
			CSVWriter writer = new CSVWriter(
					new FileWriter("C:\\Users\\aditi.inamdar\\Documents\\UserStoryJiraNew2.csv"));
			String line1[] = { "Test case ID", "User Story" };
			String line2[] = { "CORTEX-235", s };
			writer.writeNext(line1);
			writer.writeNext(line2);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

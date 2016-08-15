import java.text.ParseException;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AutoResortLock {

	public static void main(String[] args) throws InterruptedException, ParseException {
		WebDriver driver;
		String username, password;
		String startDate, endDate;
		String renterName = "Renter"; // TODO: Implement non-default.

		// Initialize username, password, start date, and end date variables.
		if (args.length == 4) {
			username = args[0];
			password = args[1];
			startDate = args[2];
			endDate = args[3];
			if (!Pattern.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$", startDate) ||
				!Pattern.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$", endDate)) {
				System.err.println("Error: Invalid date format.");
				System.err.println("Usage: java -jar AutoResortLock username password mm/dd/yyyy mm/dd/yyyy");
				return;
			}
		} else {
			System.err.println("Usage: java -jar AutoResortLock username password startDate endDate");
			return;
		}
		
		// Open webpage.
		// Use gecko as Firefox webdriver.
		System.setProperty("webdriver.gecko.driver", "WebDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://www.codes.resortlock.com/Account.aspx/Login");
		Thread.sleep(2000);

		// Log in.
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(2000);
		
		// Check to make sure it is the correct username/password.
		try {
			driver.findElement(By.xpath("//li[text() = 'The username or password provided is incorrect.']"));
			System.err.println("Error: The username or password provided is incorrect.");
			return;
		} catch (NoSuchElementException e) {
			// Should throw this. Continue.
		}
		
		// Access code generation webpage.
		driver.findElement(By.xpath("//img[@alt='Go to RemoteCode Generate page']")).click();
		Thread.sleep(2000);

		// Select property.
		driver.findElement(By.id("PropList")).click();
		driver.findElement(By.id("PropList")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.id("PropList")).sendKeys(Keys.ENTER);

		// Input guest information.
		driver.findElement(By.id("clientname")).sendKeys(renterName);

		// Set checkin/checkout dates and times.
		driver.findElement(By.id("START_DATE")).sendKeys(startDate);
		driver.findElement(By.id("START_TIMEid")).sendKeys("02:00PM");
		driver.findElement(By.id("END_DATE")).sendKeys(endDate);
		driver.findElement(By.id("END_TIMEid")).sendKeys("12:00PM");

		// Submit.
		//driver.findElement(By.id("issue_code")).click(); //TODO: Uncomment.
		
		driver.close();
		System.out.println("Code generation successful.");
	}
}

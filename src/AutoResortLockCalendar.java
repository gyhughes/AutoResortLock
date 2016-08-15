import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Books property in the calendar for a given time frame @ ResortLock.com.
 * Unfortunately, does not work with code generation.
 * Depreciated. Code reworked for code generation in AutoResortLock.java.
 * @author Grant
 */
public class AutoResortLockCalendar {

	static WebDriver driver;
	static String username, password;
	
	AutoResortLockCalendar(String un, String pw) {
		username = un;
		password = pw;
	}
	
	public void run(Date startDate, Date endDate) throws InterruptedException, ParseException {
		String blockname = "Automated Bob"; // Unsure of significance.

		//Initialize start date and end date.
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(startDate);
		int startM = calDate.get(Calendar.MONTH);
		int startD = calDate.get(Calendar.DAY_OF_MONTH);
		//int startY = calDate.get(Calendar.YEAR);
		calDate.setTime(endDate);
		int endM = calDate.get(Calendar.MONTH);
		int endD = calDate.get(Calendar.DAY_OF_MONTH);
		//int endY = calDate.get(Calendar.YEAR);
		
		// Use gecko as Firefox webdriver.
		System.setProperty("webdriver.gecko.driver", "WebDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// Open webpage.
		driver.get("https://www.codes.resortlock.com/Account.aspx/Login");
		Thread.sleep(2000);

		// Log in.
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(2000);
		
		//Check to make sure it is the correct username/password.
		try {
			driver.findElement(By.xpath("//li[text() = 'The username or password provided is incorrect.']"));
			System.err.println("Error: The username or password provided is incorrect.");
			return;
		} catch (NoSuchElementException e) {
			// Should throw this. Continue.
		}
		
		// Access calendar webpage.
		driver.findElement(By.xpath("//img[@alt='Go to Calendar page']")).click();
		Thread.sleep(2000);

		// Select property.
		driver.findElement(By.id("PropList")).click();
		driver.findElement(By.id("PropList")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.id("PropList")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// Get current date.
		String header = driver.findElement(By.className("ui-datepicker-header")).getText();
		Date date = new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH).parse(header);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int calM = cal.get(Calendar.MONTH) + 1;
		
		// Change to correct start month.
		changeMonth(startM, calM);
		calM = startM;
		
		// Select start date.
		driver.findElement(By.xpath("//a[text() = '"+ startD +"']")).click();
		
		// Change to correct end month.
		changeMonth(endM, calM);
		
		// Select end date.
		driver.findElement(By.xpath("//a[text() = '"+ endD +"']")).click();

		// Fill in username.
		driver.findElement(By.id("blockname")).sendKeys(blockname);
		
		// Submit.
		//driver.findElement(By.id("blockdate")).click(); //TODO: Uncomment.
		
		driver.close();
		System.out.println("Booking successful.");
	}
	
	/**
	 * Changes calendar from currMonth to destMonth.
	 * @param destMonth Month of the destination date
	 * @param currMonth Current month of the calendar
	 * @throws ParseException If no matching elements are found
	 * @throws InterruptedException If any thread has interrupted the current thread
	 */
	public static void changeMonth(int destMonth, int currMonth) throws ParseException, InterruptedException {
		Calendar cal = Calendar.getInstance();
		while (destMonth != currMonth) {
			driver.findElement(By.linkText("Next>")).click();
			Thread.sleep(1000);
			String header = driver.findElement(By.className("ui-datepicker-header")).getText();
			cal.setTime(new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH).parse(header));
			currMonth = cal.get(Calendar.MONTH) + 1;
		}
		Thread.sleep(2000);
	}

}

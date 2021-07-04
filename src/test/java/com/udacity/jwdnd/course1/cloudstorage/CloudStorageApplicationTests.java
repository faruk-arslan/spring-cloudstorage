package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signUpAndLogin() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,10);
		// Test sign up page.
		driver.get("http://localhost:"+this.port+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName"))).sendKeys("FName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName"))).sendKeys("LName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("selenium_test");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("12345..");
		driver.findElement(By.xpath("//button[@type='submit' and text()='Sign Up']")).click();
		// Test login page.
		Assertions.assertEquals("Login", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("selenium_test");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("12345..");
		driver.findElement(By.xpath("//button[@type='submit' and text()='Login']")).click();
		// Test if home page is opened after sign up and login.
		Assertions.assertEquals("Home", driver.getTitle());
		// Test logout
		driver.findElement(By.xpath("//button[@type='submit' and text()='Logout']")).click();
		driver.get("http://localhost:"+this.port+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedPages(){
		driver.get("http://localhost:"+this.port+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:"+this.port+"/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:"+this.port+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testNotesTab() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,10);

		driver.get("http://localhost:"+this.port+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("un");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("1");
		driver.findElement(By.xpath("//button[@type='submit' and text()='Login']")).click();

		Assertions.assertEquals("Home", driver.getTitle());
		// Go to the Notes tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
		List<WebElement> noteModalLabel=driver.findElements(By.id("resultsNote"));
		if (noteModalLabel.size()<1) Assertions.fail("Could not find the Notes label");
		// Add a new note.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='+ Add a New Note']"))).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-title"))).sendKeys("Selenium Title");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-description"))).sendKeys("Selenium Description");
		driver.findElement(By.xpath("//button[text()='Save changes']")).click();
		// Check if the note has been created.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
		Thread.sleep(2000);
		List<WebElement> th=driver.findElements(By.tagName("th"));
		List<WebElement> td=driver.findElements(By.tagName("td"));
		String newTitle=th.get(th.size()-1).getText();
		String newDescription=td.get(td.size()-1).getText();
		Assertions.assertEquals("Selenium Title", newTitle);
		Assertions.assertEquals("Selenium Description", newDescription);
		// Edit the note.
		List<WebElement> editBtnList=driver.findElements(By.xpath("//button[text()='Edit']"));
		if (editBtnList.size()>0) editBtnList.get(editBtnList.size()-1).click();
		else System.out.println("Could not find the Edit button.");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-title"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-title"))).sendKeys("Edited Title");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-description"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-description"))).sendKeys("Edited Description");
		driver.findElement(By.xpath("//button[text()='Save changes']")).click();
		// Check if the note has been edited.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
		Thread.sleep(2000);
		th=driver.findElements(By.tagName("th"));
		td=driver.findElements(By.tagName("td"));
		String editedTitle=th.get(th.size()-1).getText();
		String editedDescription=td.get(td.size()-1).getText();
		Assertions.assertEquals("Edited Title", editedTitle);
		Assertions.assertEquals("Edited Description", editedDescription);
		// Delete the note.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
		Thread.sleep(2000);
		List<WebElement> deleteBtnList=driver.findElements(By.xpath("//a[text()='Delete']"));
		if (deleteBtnList.size()>0) deleteBtnList.get(deleteBtnList.size()-1).click();
		else System.out.println("Could not find the Delete button.");
		Thread.sleep(1000);
		// Check if the note has been deleted.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
		Thread.sleep(2000);
		deleteBtnList=driver.findElements(By.xpath("//button[text()='Delete']"));
		Assertions.assertEquals(0, deleteBtnList.size());
	}

	@Test
	public void testCredentialsTab() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,10);

		driver.get("http://localhost:"+this.port+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("un");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("1");
		driver.findElement(By.xpath("//button[@type='submit' and text()='Login']")).click();

		Assertions.assertEquals("Home", driver.getTitle());
		// Go to the Credentials tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab"))).click();
		List<WebElement> credentialModalLabel=driver.findElements(By.id("resultsCredential"));
		if (credentialModalLabel.size()<1) Assertions.fail("Could not find the Credentials label");
		// Add a new credential.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='+ Add a New Credential']"))).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-url"))).sendKeys("https://www.facebook.com");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-username"))).sendKeys("fb_username");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-password"))).sendKeys("pass123secret##");
		driver.findElement(By.id("saveBtnCredentialModal")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-credentials-tab"))).click();
		Thread.sleep(2000);
		// Check if the note has been created.
		List<WebElement> rows=driver.findElements(By.xpath("//table[@id=\"credentialTable\"]//tr"));
		WebElement lastRow=rows.get(rows.size()-1);
		String url=lastRow.findElement(By.xpath("//th[@id=\"th_url\"]")).getText();
		String username=lastRow.findElement(By.xpath("//td[@id=\"td_username\"]")).getText();
		String password=lastRow.findElement(By.xpath("//td[@id=\"td_password\"]")).getText();
		WebElement editButton=lastRow.findElement(By.xpath("//button[text()='Edit']"));
		WebElement deleteButton=lastRow.findElement(By.xpath("//a[text()='Delete']"));

		Assertions.assertEquals("https://www.facebook.com", url);
		Assertions.assertEquals("fb_username", username);
		Assertions.assertNotEquals("pass123secret##", password);
		editButton.click();
		Thread.sleep(2000);
		Assertions.assertEquals("pass123secret##", driver.findElement(By.id("credential-password")).getText());
		// Edit the credentials.
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-url"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-username"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-password"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-url"))).sendKeys("https://www.facebook.com2");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-username"))).sendKeys("fb_username2");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-password"))).sendKeys("pass123secret##2");
		driver.findElement(By.id("saveBtnCredentialModal")).click();
		// Check if the credentials has been edited.
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-credentials-tab"))).click();
		Thread.sleep(2000);
		rows=driver.findElements(By.xpath("//table[@id=\"credentialTable\"]//tr"));
		lastRow=rows.get(rows.size()-1);
		url=lastRow.findElement(By.xpath("//th[@id=\"th_url\"]")).getText();
		username=lastRow.findElement(By.xpath("//td[@id=\"td_username\"]")).getText();
		password=lastRow.findElement(By.xpath("//td[@id=\"td_password\"]")).getText();
		editButton=lastRow.findElement(By.xpath("//button[text()='Edit']"));
		deleteButton=lastRow.findElement(By.xpath("//a[text()='Delete']"));


		Assertions.assertEquals("https://www.facebook.com2", url);
		Assertions.assertEquals("fb_username2", username);
		Assertions.assertNotEquals("pass123secret##2", password);

		editButton.click();
		Thread.sleep(2000);
		Assertions.assertEquals("pass123secret##2", driver.findElement(By.id("credential-password")).getText());
		driver.findElement(By.id("closeBtnCredentialModal")).click();
		// Delete the credentials.
		rows=driver.findElements(By.xpath("//table[@id=\"credentialTable\"]//tr"));
		lastRow=rows.get(rows.size()-1);
		lastRow.findElement(By.xpath("//a[text()='Delete']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab"))).click();
		rows=driver.findElements(By.xpath("//table[@id=\"credentialTable\"]//tr"));
		Assertions.assertEquals(1, rows.size());
	}

}

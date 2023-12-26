package seleniumgluecode;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebInteractionSteps {

    private WebDriver driver;
    private List<String> searchResultUrls; // Store the URLs as a class variable
    
    
    @Given("the user opens a Chrome browser")
    public void openChromeBrowser() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/ChromeDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
     
       
    }

    @When("the user visits the Google search page")
    public void visitGoogleSearchPage() {
        driver.get("https://www.google.com");
    }

    @When("the user searches for the keyword {string}")
    public void searchForKeyword(String keyword) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(keyword);
        searchBox.submit();
    }

    @When("the user retrieves the links from the search results")
    public void retrieveLinksFromSearchResults() {
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3//a")));
    	List<WebElement> searchResults = driver.findElements(By.xpath("//h3//a")); // Adjust the XPath as needed

    	// Store URLs in the class variable
        searchResultUrls = new ArrayList<>();
        for (WebElement result : searchResults) {
            String url = result.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                searchResultUrls.add(url);
            }
        }

        // Display the top N links to the console
        int topN = 5; // Adjust this value as needed
        System.out.println("Top " + topN + " links:");
        for (int i = 0; i < Math.min(topN, searchResultUrls.size()); i++) {
            System.out.println((i + 1) + ". " + searchResultUrls.get(i));
        }
    }
    

    @When("the user visits the first link from the search results")
    public void visitFirstLink() {
        // Assume the first search result link is clicked
    	if (searchResultUrls != null && !searchResultUrls.isEmpty()) {
            // Visit the first link from the stored URLs
            String firstLinkUrl = searchResultUrls.get(0);
            driver.get(firstLinkUrl);
        } else {
            // Handle the case where no URLs are retrieved
            System.out.println("No search result URLs available to visit.");
        }
    
    }

    @Then("the user takes a screenshot of the first webpage")
    public void takeScreenshotOfFirstPage() throws IOException {
        // Takes the screenshot and saves it as a file
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	System.out.println("Waiting 5 sec");
    	try {
            // Takes the screenshot and saves it as a file
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Define the destination directory
            String destinationDirectory = System.getProperty("user.dir") + "/Screenshots/";

            // Create the directory if it doesn't exist
            new File(destinationDirectory).mkdirs();

            // Generate a timestamp for the file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Define the file name with a timestamp
            String fileName = "screenshot_" + timestamp + ".png";

            // Copy the screenshot file to the destination directory with the timestamped file name
            FileUtils.copyFile(screenshotFile, new File(destinationDirectory + fileName));
        } catch (IOException e) {
            // Handle the exception, e.g., print an error message
            System.err.println("Failed to take a screenshot: " + e.getMessage());
        }
    }

    @When("the user goes back to the search results page")
    public void goBackToSearchResultsPage() {
        driver.navigate().back();
    }

    @When("the user visits the second link from the search results")
    public void visitSecondLink() {
        // Assume the second search result link is clicked
    	if (searchResultUrls != null && !searchResultUrls.isEmpty() && searchResultUrls.size()>1) {
            // Visit the first link from the stored URLs
            String firstLinkUrl = searchResultUrls.get(1);
            driver.get(firstLinkUrl);
        } else {
            // Handle the case where no URLs are retrieved
            System.out.println("No search result URLs available to visit.");
        }
    }

    @Then("the user takes a screenshot of the second webpage")
    public void takeScreenshotOfSecondPage() throws IOException {
        // Takes the screenshot and saves it as a file
    	if (searchResultUrls != null && !searchResultUrls.isEmpty()&& searchResultUrls.size()>1) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Copy the screenshot file to a destination directory
        FileUtils.copyFile(screenshotFile, new File(System.getProperty("user.dir")+"/Screenshots"));
    	} else {
            // Handle the case where no URLs are retrieved
            System.out.println("No 2nd result URLs available to visit.");
        }
    	}

    @Then("the user closes the browser")
    public void closeBrowser() {
        driver.quit();
    }
}

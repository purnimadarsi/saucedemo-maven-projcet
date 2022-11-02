package login;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPageTest {

    WebDriver driver = null;
    WebElement user_Name ;
    WebElement user_Password;
    WebElement login_Button;


    @BeforeTest
     public void driver_Setup() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeMethod
    public void openDemo_URL(){
        driver = new ChromeDriver();
        // navigate to demo url
        driver.get("https://www.saucedemo.com/");
        //finding address of username,password and loginbutton
         user_Name =  driver.findElement(By.id("user-name"));
         user_Password= driver.findElement(By.id("password"));
         login_Button = driver.findElement(By.id("login-button"));

    }
    @DataProvider // test data for login
    public Object[][] login_Data(){
        return new Object[][]  {{"standard_user","secret_sauce"},{"problem_user","secret_sauce"},
                                {"performance_glitch_user","secret_sauce"}};
    }

    @Test(dataProvider = "login_Data")
    public void verifyUserLogin_WithValidCredentials(String login_User_Name,String login_password) throws InterruptedException {
      //entering username and password
       user_Name.sendKeys(login_User_Name);
       user_Password.sendKeys(login_password);
       //clicking login button
       login_Button.click();
       // checking login is successful or not
       WebElement product = driver.findElement(By.className("title"));
       Assert.assertEquals(product.getText(),"PRODUCTS");
       Thread.sleep(1000);
    }
    @AfterMethod
    public void close_Window(){
        //closing the current window
        driver.close();
    }
    @Test
    public void verifyUserLogin_WithInValidCredentials(){
        //entering wrong username and wrong password
        user_Name.sendKeys("standard_user345");
        user_Password.sendKeys("secret_sauce123");
        //clicking login button
        login_Button.click();
        //checking for error message
        String descriptionTextXPath = "//div[contains(@class,'error-message')]/h3";

        WebElement h3Element = driver.findElement(By.xpath(descriptionTextXPath));
        String error_MsgText = h3Element.getText();
        Assert.assertEquals(error_MsgText,"Epic sadface: Username and password do not match any user in this service");



    }

    @Test(enabled = false)
    public void verifyUserLogin_WithWrongUsername(){
        //entering wrong username and correct password
        user_Name.sendKeys("standard_user345");
        user_Password.sendKeys("secret_sauce");
        //clicking login button
        login_Button.click();
        //checking for error message
        String descriptionTextXPath = "//div[contains(@class,'error-message')]/h3";

        WebElement h3Element = driver.findElement(By.xpath(descriptionTextXPath));
        String error_MsgText = h3Element.getText();
        Assert.assertEquals(error_MsgText,"Epic sadface: Username and password do not match any user in this service");


    }
    @Test
    public void verifyUserLogin_WithWrongPassword(){
        //entering correct username and wrong password
        user_Name.sendKeys("standard_user");
        user_Password.sendKeys("secret_sauce123");
        //clicking login button
        login_Button.click();
        //checking for error message
        String descriptionTextXPath = "//div[contains(@class,'error-message')]/h3";

        WebElement h3Element = driver.findElement(By.xpath(descriptionTextXPath));
        String error_MsgText = h3Element.getText();
        Assert.assertEquals(error_MsgText,"Epic sadface: Username and password do not match any user in this service");

    }
    @Test
    public void verify_Locked_Out_User(){
        //entering locked out user credentials
        user_Name.sendKeys("locked_out_user");
        user_Password.sendKeys("secret_sauce");
        //clicking login button
        login_Button.click();
        //checking for error message
        String descriptionTextXPath = "//div[contains(@class,'error-message')]/h3";

        WebElement h3Element = driver.findElement(By.xpath(descriptionTextXPath));
        String error_MsgText = h3Element.getText();
        Assert.assertEquals(error_MsgText,"Epic sadface: Sorry, this user has been locked out.");

    }
    @AfterTest
    public void closeDemo_URL(){
        //closing the session
        driver.quit();
    }
}

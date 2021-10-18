import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/*============*******=============*/

public class PageTestsMinhaRedeLinkedin {

    //Variables declarations
    private WebDriver driver;
    private List<WebElement> infoAbaLateral;
    private List<WebElement> contactCards;

    public Integer cards;
    public Integer connectionsQtd;
    public Integer pagesQtd;
    public WebElement generalBoxMessage;
    public WebElement firstContactInGeneralBoxMessage;
    public WebElement privateBoxMessage;

    /*============  TESTs  =============*/

    @BeforeEach
    public void PreparingTestAndAccessPage() {

        System.setProperty("webdriver.chrome.driver",
                "C:\\drivers-nav-para-testes\\chromedriver.exe");
        driver = new ChromeDriver();

        //Page Access
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.linkedin.com/login");
        driver.findElement(By.id("username")).sendKeys(System.getenv("LINKEDIN USERNAME"));
        driver.findElement(By.id("password")).sendKeys(System.getenv("LINKEDIN PASSWORD"));
        driver.findElement(By.cssSelector("[type=submit]")).click();
        driver.findElement(By.cssSelector("[data-test-global-nav-link=mynetwork]")).click();

        infoAbaLateral = driver.findElements(By.className("pl3"));
    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }

    @Test
    public void connectionsShowValidCards() {
        contactCards = driver.findElements(By.className("discover-entity-type-card__info-container"));
        cards= contactCards.size();

        Assertions.assertTrue(cards != 0);
    }

    @Test
    public void connectionsValidate() {
        connectionsQtd = Integer.valueOf(infoAbaLateral.get(0).getText());

        assert connectionsQtd > 0;
    }

    @Test
    public void pagesValidate() {
        pagesQtd = Integer.valueOf(infoAbaLateral.get(3).getText());

        assert pagesQtd > 0;
    }

    @Test
    public void BoxAllMessagesIsOpen() {
        generalBoxMessage = driver.findElement(By.cssSelector(".msg-overlay-list-bubble"));

        assert generalBoxMessage.isDisplayed();
    }

    @Test
    public void BoxForPrivateMessagesIsDisplayedWhenClicked() {
        firstContactInGeneralBoxMessage = driver.findElement(By.cssSelector("div.msg-conversation-card__row"));
        firstContactInGeneralBoxMessage.click();
        privateBoxMessage = driver.findElement(By.cssSelector(".msg-s-message-list-container.relative.display-flex.mtA.ember-view"));

        assert privateBoxMessage.isDisplayed();
    }
}
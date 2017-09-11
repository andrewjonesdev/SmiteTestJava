package byAJ;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;

/**
 * Created by andrewdjones on 9/11/17.
 */
public class Scrape {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("phantomjs.page.settings.userAgent", USER_AGENT);
        String baseUrl = "https://www.inshorts.com/en/read";
        initPhantomJS();
        driver.get(baseUrl);
        int nbArticlesBefore = driver.findElements(By.xpath("//div[@class='card-stack']/div")).size();
        driver.findElement(By.id("load-more-btn")).click();

        // We wait for the ajax call to fire and to load the response into the page
        Thread.sleep(800);
        int nbArticlesAfter = driver.findElements(By.xpath("//div[@class='card-stack']/div")).size();
        System.out.println(String.format("Initial articles : %s Articles after clicking : %s", nbArticlesBefore, nbArticlesAfter));
    }
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    private static DesiredCapabilities desiredCaps ;
    private static WebDriver driver ;


    public static void initPhantomJS(){
        desiredCaps = new DesiredCapabilities();
        desiredCaps.setJavascriptEnabled(true);
        desiredCaps.setCapability("takesScreenshot", false);
        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", USER_AGENT);

        ArrayList<String> cliArgsCap = new ArrayList();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        cliArgsCap.add("--webdriver-loglevel=ERROR");

        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        driver = new PhantomJSDriver(desiredCaps);
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }
}

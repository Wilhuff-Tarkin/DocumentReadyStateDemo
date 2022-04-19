package test;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

// >>>  Ready state vs page load strategy:
//www.selenium.dev/documentation/webdriver/capabilities/shared/#pageloadstrategy
//https://developer.mozilla.org/en-US/docs/Web/API/Document/readyState

// >>>  Potential use:
//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

// When navigating to a new page via URL, by default Selenium will wait until the Document’s Ready State is “complete.”
// The document.readyState property of a document describes the loading state of the current document.
// By default, WebDriver will hold off on completing a navigation method (e.g., driver.navigate().get()) until the document ready state is complete.

public class StateDemo extends TestBase {

    private static final Logger log = LoggerFactory.getLogger("StateDemo.class");

    private static final int scheduledDuration = 3;
    private static final int sleepDurationNano = 50;

    @Test
    void presentLoadingStatusNormal() {

        long startTime = System.nanoTime();
        long endTime;
        long durationInSeconds;
        long durationInNano;

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get("https://www.warhammer-community.com/");

        while (true) {
            sleepMethod();
            String ready = (String) jse.executeScript("return document.readyState");
            log.info("Page loading status is >>>" + ready + "<<<");

            endTime = System.nanoTime();
            durationInNano = (endTime - startTime); // Execution time in nano seconds
            durationInSeconds = TimeUnit.NANOSECONDS.toSeconds(durationInNano); // Execution time in seconds

            if (durationInSeconds >= scheduledDuration) {
                System.out.println(scheduledDuration + " seconds done");
                break;
            }
        }
    }

    private static void sleepMethod() {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepDurationNano);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

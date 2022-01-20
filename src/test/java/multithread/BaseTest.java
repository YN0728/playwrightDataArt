package multithread;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    static ThreadLocal<Browser> browser = new ThreadLocal<>();

//    @BeforeAll
//    static void launchBrowser() {
//        playwright.set(Playwright.create());
//        browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions()
//                .setHeadless(false)
//                .setSlowMo(50)));
//    }
//
//    // New instance for each test method.
    ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    ThreadLocal<Page> page = new ThreadLocal<>();

//
//    @AfterAll
//    static void closeBrowser() {
//        playwright.get().close();
//    }

    @BeforeEach
    void createContextAndPage() {

        playwright.set(Playwright.create());
        browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(50)));

        context.set(browser.get().newContext(new Browser.NewContextOptions()
                .setViewportSize(1786, 990)
                .setDeviceScaleFactor(3)
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1786, 990)
                .setPermissions(Arrays.asList("geolocation"))
                .setGeolocation(52.52, 13.39)
                .setColorScheme(ColorScheme.DARK)
                .setLocale("de-DE")));


        page.set(context.get().newPage());
        page.get().navigate("https://www.imdb.com/", new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE));
    }

    @AfterEach
    void closeContext() {
        context.get().close();
        playwright.get().close();

    }
}


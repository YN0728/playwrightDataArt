import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    static Playwright playwright;
    static Browser browser;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
//                .setDevtools(true)
                .setSlowMo(50));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
//        context = browser.newContext(
//                new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json")));

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1786, 990)
                .setDeviceScaleFactor(3)
//                .setIsMobile(true)
//                .setHasTouch(true)
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1786, 990)
                .setPermissions(Arrays.asList("geolocation"))
                .setGeolocation(52.52, 13.39)
                .setColorScheme(ColorScheme.DARK)
                .setLocale("de-DE"));


        page = context.newPage();
        page.navigate("https://www.imdb.com/", new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE));

//        page.navigate("https://console.development.peg.qless.com/");
//        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));

    }

    @AfterEach
    void closeContext() {
        context.close();
        playwright.close();
    }
}

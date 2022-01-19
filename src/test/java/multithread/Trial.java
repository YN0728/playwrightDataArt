package multithread;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class Trial extends BaseTest {

    @Test
    void printTopFiveMovies() {
        page.get().frames().get(0).click("id=imdbHeader-navDrawerOpen--desktop", new Frame.ClickOptions().setForce(true));
        page.get().click("text=Top 250 Movies");
        page.get().waitForSelector(".titleColumn");
        for (int i = 0; i < 5; i++) {
            System.out.println(page.get().querySelectorAll(".titleColumn").get(i).textContent());
        }
    }

    @Test
    void searchAmongCelebs() {
        final int order = 0;
        final String celebrity = "Gor Vardanyan";
//        page.pause(); //opens browser inspector
        page.get().click("id=iconContext-arrow-drop-down");
        page.get().click("[id='navbar-search-category-select-contents'] [class*='ipc-icon ipc-icon--people']");
        page.get().type("[id='suggestion-search']", celebrity);
        page.get().waitForSelector("[class='react-autosuggest__suggestions-list anim-enter-done']");
        page.get().waitForSelector("id=react-autowhatever-1--item-" + order);
        page.get().click("id=react-autowhatever-1--item-" + order);
        String[] textAboutCelebrity = page.get().textContent("id=name-bio-text").trim().split("\n");
        System.out.println(textAboutCelebrity[0]);
    }

    @Test
    void screenshotAndSave() {
        page.get().screenshot(new Page.ScreenshotOptions().setClip(265, 65, 848, 540).setPath(Paths.get("example.png")));

    }

    @Test
    void viewAssertions(){
//        https://playwright.dev/java/docs/assertions

        final String filter = "[for='navbar-search-category-select']";
        String text = page.get().innerText(filter);
        assertEquals("All", text);

        String content = page.get().textContent("[class^=\"SlideCaption__WithPeekCaptionHeadingText\"] >> nth=1");
        assertTrue(content.contains("Moon Knight"));

//        boolean checked = page.isChecked("[id='suggestion-search']");
//        assertTrue(checked);

        page.get().locator("id='iconContext-menu'").waitFor();
        boolean visible = page.get().isVisible("id='iconContext-menu'");
        assertFalse(visible);

        //Custom Assertions
        Object userId = page.get().evaluate("() => window.localStorage.getItem('userId')");
        assertNull(userId);

        Object fontSize = page.get().locator("[id='featured-today']").evaluate("el => window.getComputedStyle(el).fontSize");
        assertEquals("16px", fontSize);

    }
}

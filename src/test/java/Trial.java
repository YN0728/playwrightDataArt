import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Trial extends BaseTest {

    @Test
    void printTopFiveMovies() {
        page.frames().get(0).click("id=imdbHeader-navDrawerOpen--desktop", new Frame.ClickOptions().setForce(true));
        page.hover("text=Top 250 Movies");
        page.click("text=Top 250 Movies");
        page.waitForSelector(".titleColumn");
        for (int i = 0; i < 5; i++) {
            System.out.println(page.querySelectorAll(".titleColumn").get(i).textContent());
        }
    }

    @Test
    void searchAmongCelebs() {
        final int order = 0;
        final String celebrity = "Gor Vardanyan";
        page.pause(); //opens browser inspector
        page.click("id=iconContext-arrow-drop-down");
        page.click("[id='navbar-search-category-select-contents'] [class*='ipc-icon ipc-icon--people']");
        page.type("[id='suggestion-search']", celebrity);
        page.waitForSelector("[class='react-autosuggest__suggestions-list anim-enter-done']");
        page.waitForSelector("id=react-autowhatever-1--item-" + order);
        page.click("id=react-autowhatever-1--item-" + order);
        String[] textAboutCelebrity = page.textContent("id=name-bio-text").trim().split("\n");
        System.out.println(textAboutCelebrity[0]);
    }

    @Test
    void screenshotAndSave() {
        page.screenshot(new Page.ScreenshotOptions().setClip(265, 65, 848, 540).setPath(Paths.get("example.png")));

    }

    @Test
    void viewAssertions(){
//        https://playwright.dev/java/docs/assertions

        final String filter = "[for='navbar-search-category-select']";
        page.locator("[for='navbar-search-category-select']").waitFor();
        String text = page.innerText(filter);
        assertEquals("All", text);

        String content = page.textContent("[class^=\"SlideCaption__WithPeekCaptionHeadingText\"] >> nth=1");
        assertTrue(content.contains("Moon Knight"));

//        boolean checked = page.isChecked("[id='anyCheckboxHere']");
//        assertTrue(checked);

        boolean visible = page.isVisible("id='iconContext-menu'");
        assertFalse(visible);

        //Custom Assertions
        Object userId = page.evaluate("() => window.localStorage.getItem('userId')");
        assertNull(userId);

        Object fontSize = page.locator("[id='featured-today']").evaluate("el => window.getComputedStyle(el).fontSize");
        assertEquals("16px", fontSize);

    }
}

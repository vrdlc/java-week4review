import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Band");
  }

  @Test //HAS STALE ELEMENT EXCEPTION! WHY?!?
  public void authorFormInputsNewTitleAndNewAuthor() {
    goTo("http://localhost:4567/");
    fill("#newBandName").with("Firewater");
    fill("#newBandGenre").with("rock");
    submit(".btn");
    fill("#newVenueName").with("Berbatis");
    fill("#newVenueStyle").with("stand-up");

    assertThat(pageSource()).contains("Firewater");
    assertThat(pageSource()).contains("rock");
    assertThat(pageSource()).contains("Berbatis");
    assertThat(pageSource()).contains("stand-up");
  }
//
//   // @TEST
//   // public void patronFormInputsNewPatron() {
//   //   goTo("http://localhost:4567/patrons");
//   //   fill("#newPatron").with("Bob Smith");
//   //   submit(".btn");
//   //   assertThat(pageSource()).contains("Bob Smith");
//   // }
//
//
// }
}

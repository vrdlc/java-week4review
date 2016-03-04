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
  public void formsInputNewBandAndNewVenue() {
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

  @Test
  public void addVenueToBand() {
    Band newBand = new Band ("Firewater", "rock");
    newBand.save();
    Venue newVenue = new Venue ("Berbati's", "stand-up");
    newVenue.save();
    String bandPath = String.format("http://localhost:4567/band/%d", newBand.getId());
    goTo(bandPath);
    assertThat(pageSource()).contains("Firewater");
    assertThat(pageSource()).contains("Berbati's");
  }

  @Test
  public void addBandtoVenue() {
    Band newBand = new Band ("Firewater", "rock");
    newBand.save();
    Venue newVenue = new Venue ("Berbati's", "stand-up");
    newVenue.save();
    String venuePath = String.format("http://localhost:4567/venue/%d", newVenue.getId());
    goTo(venuePath);
    assertThat(pageSource()).contains("Firewater");
    assertThat(pageSource()).contains("Berbati's");
  }

}

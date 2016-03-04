import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfVenuesAreTheSame() {
    Venue firstVenue = new Venue("Berbati's", "stand-up");
    Venue secondVenue = new Venue("Berbati's", "stand-up");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_addsInstaceOfVenueToDatabase() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertTrue(newVenue.equals(savedVenue));
  }

  @Test
  public void save_assignsIdToObject() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_locatesAllInstancesOfVenueInDatabaseUsingID() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    Venue savedVenue = Venue.find(newVenue.getId());
    assertTrue(newVenue.equals(savedVenue));
  }

  @Test
  public void updateVenueName_updatesVenueNameInDatabase() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    newVenue.updateVenueName("Dante's");
    assertTrue(Venue.all().get(0).getVenueName().equals("Dante's"));
  }

  @Test
  public void updateStyle_updatesStyleInDatabase() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    newVenue.updateStyle("sit-down");
    assertEquals(Venue.all().get(0).getStyle(), ("sit-down"));
  }

  @Test
  public void deleteVenue_deleteVenueObject() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();
    newVenue.delete();
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void addBand_addsBandsToVenue() {
    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();

    Band newBand = new Band("Firewater", "rock");
    newBand.save();

    newVenue.addBand(newBand);
    Band savedBand = newVenue.getBands().get(0);
    assertTrue(newBand.equals(savedBand));
  }

  @Test
  public void getBands_getsBandVenuesByVenueID() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();

    Venue newVenue = new Venue("Berbati's", "stand-up");
    newVenue.save();

    newVenue.addBand(newBand);
    List savedBand = newVenue.getBands();
    assertEquals(savedBand.size(), 1);
  }
}

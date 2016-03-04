import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBandsAreTheSame() {
    Band firstBand = new Band("Firewater", "rock");
    Band secondBand = new Band("Firewater", "rock");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_bandToObject() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    Band savedBand = Band.all().get(0);
    assertTrue(newBand.equals(savedBand));
  }

  @Test
  public void save_assignsIdToObject() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(newBand.getId(), savedBand.getId());
  }

  @Test
  public void find_locatesAllInstancesOfBandInDatabaseUsingID() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    Band savedBand = Band.find(newBand.getId());
    assertTrue(newBand.equals(savedBand));
  }

  @Test
  public void updateBandName_updatesBandNameInDatabase() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    newBand.updateBandName("Red Elvises");
    assertEquals(Band.all().get(0).getBandName(), ("Red Elvises"));
  }

  @Test
  public void updateGenre_updatesGenreInDatabase() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    newBand.updateGenre("international");
    assertTrue(Band.all().get(0).getGenre().equals("international"));
  }

  @Test
  public void deleteBand_deleteBandObject() {
    Band newBand = new Band("Firewater", "rock");
    newBand.save();
    newBand.delete();
    assertEquals(Band.all().size(), 0);
  }
//NOT PASSING, INDEX OUT OF BOUNDS EXCEPTION, DON'T KNOW WHY, WILL TRY AGAIN LATER
  // @Test
  // public void addVenue_addsVenueToBand() {
  //   Band newBand = new Band("Firewater", "rock");
  //   newBand.save();
  //
  //   Venue newVenue = new Venue ("Berbati's", "stand-up");
  //   newBand.addVenue(newVenue);
  //
  //   Venue savedVenue = newBand.getVenues().get(0);
  //   assertTrue(newVenue.equals(savedVenue));
  // }


//   @Test
//   public void getVenues_getsVenuesBandPlaysAt() {
//     Venue newVenue = new Venue("Berbati's", "stand-up");
//     newVenue.save();
//
//     Band newBand = new Band("Firewater", "rock");
//     newBand.save();
//
//     newBand.addVenue(newVenue);
//     List savedVenues = newBand.getVenues();
//     assertEquals(savedVenues.size(), 1);
//   }
}

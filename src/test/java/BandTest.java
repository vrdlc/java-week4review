import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class CopiesTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copies.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCopiessAreTheSame() {
    Copies firstCopies = new Copies(5, "3/05/2017", 1);
    Copies secondCopies = new Copies(5, "3/05/2017", 1);
    assertTrue(firstCopies.equals(secondCopies));
  }

  @Test
  public void save_DueDateToObject() {
    Copies newCopies = new Copies(5, "3/05/2017", 1);
    newCopies.save();
    Copies savedCopies = Copies.all().get(0);
    assertTrue(newCopies.equals(savedCopies));
  }

  @Test
  public void save_assignsIdToObject() {
    Copies newCopies = new Copies(5, "3/05/2017", 1);
    newCopies.save();
    Copies savedCopies = Copies.all().get(0);
    assertEquals(newCopies.getId(), savedCopies.getId());
  }

  @Test
  public void find_locatesAllInstancesOfCopiesInDatabaseUsingID() {
    Copies newCopies = new Copies(5, "3/05/2017", 1);
    newCopies.save();
    Copies savedCopies = Copies.find(newCopies.getId());
    assertTrue(newCopies.equals(savedCopies));
  }

  @Test
  public void updateCopies_updatesCopiesInDatabase() {
    Copies newCopies = new Copies(5, "3/15/2017", 1);
    newCopies.save();
    newCopies.updateCopies(7);
    assertEquals(Copies.all().get(0).getCopies(), 7);
  }

  @Test
  public void updateDueDate_updatesDueDateInDatabase() {
    Copies newCopies = new Copies(5, "3/15/2017", 1);
    newCopies.save();
    newCopies.updateDueDate("3/16/2017");
    assertTrue(Copies.all().get(0).getDueDate().equals("3/16/2017"));
  }

  @Test
  public void updateTitleId_updatesTitleIdInDatabase() {
    Copies newCopies = new Copies(5, "3/15/2017", 1);
    newCopies.save();
    newCopies.updateTitleId(7);
    assertEquals(Copies.all().get(0).getTitleId(), 7);
  }

  @Test
  public void deleteCopies_deleteCopiesObject() {
    Copies newCopies = new Copies(5, "3/05/2017", 1);
    newCopies.save();
    newCopies.delete();
    assertEquals(Copies.all().size(), 0);
  }
}

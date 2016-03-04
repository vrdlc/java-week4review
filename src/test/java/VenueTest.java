import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfPatronsAreTheSame() {
    Patron firstPatron = new Patron("Bob Smith");
    Patron secondPatron = new Patron("Bob Smith");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void save_addsInstaceOfPatronToDatabase() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();
    Patron savedPatron = Patron.all().get(0);
    assertTrue(newPatron.equals(savedPatron));
  }

  @Test
  public void save_assignsIdToObject() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();
    Patron savedPatron = Patron.all().get(0);
    assertEquals(newPatron.getId(), savedPatron.getId());
  }

  @Test
  public void find_locatesAllInstancesOfPatronInDatabaseUsingID() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();
    Patron savedPatron = Patron.find(newPatron.getId());
    assertTrue(newPatron.equals(savedPatron));
  }

  @Test
  public void updateName_updatesNameInDatabase() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();
    newPatron.updateName("John");
    assertEquals(Patron.all().get(0).getName(), ("John"));
  }

  // @Test
  // public void updateDueDate_updatesDueDateInDatabase() {
  //   Patron newPatron = new Patron("Bob Smith");
  //   newPatron.save();
  //   newPatron.updateDueDate("3/15/2016");
  //   assertEquals(Patron.all().get(0).getDueDate(), ("3/15/2016"));
  // }
  //
  // @Test
  // public void updateIdTitleAuthor_updatesIdTitleAuthorInDatabase() {
  //   Patron newPatron = new Patron("Bob Smith");
  //   newPatron.save();
  //   newPatron.updateIdTitleAuthor(3);
  //   assertEquals(Patron.all().get(0).getIdTitleAuthor(), 3);
  // }

  @Test
  public void deletePatron_deletePatronObject() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();
    newPatron.delete();
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void addCopies_addsCopiesToPatron() {
    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();

    Copies newCopies = new Copies(2, "3/12/2016", 2);
    newCopies.save();

    newPatron.addCopies(newCopies);
    Copies savedCopies = newPatron.getCopies().get(0);
    assertTrue(newCopies.equals(savedCopies));
  }

  @Test
  public void getCopies_getsCopiesPatronsByPatronID() {
    Copies newCopies = new Copies(2, "3/12/2016", 2);
    newCopies.save();

    Patron newPatron = new Patron("Bob Smith");
    newPatron.save();

    newPatron.addCopies(newCopies);
    List savedCopies = newPatron.getCopies();
    assertEquals(savedCopies.size(), 1);
  }
}

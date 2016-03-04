import org.sql2o.*;
import java.util.List;

public class Band {
  private int id;
  private String band_name;
  private String genre;
  public int venue_id;

  public int getId() {
    return id;
  }

  public String getBandName() {
    return band_name;
  }

  public String getGenre() {
    return genre;
  }

  public int getVenueId() {
    return venue_id;
  }

  public Band (int copies, String band_name, String genre, int venue_id) {
    this.copies = copies;
    this.band_name = band_name;
    this.genre = genre;
    this.venue_id = venue_id;
  }

  public static List<Band> all() {
    String sql = "SELECT id, band_name, genre, venue_id FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if(!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return  this.getBandName().equals(newBand.getBandName()) &&
              this.getGenre().equals(newBand.getGenre()) &&
              this.getVenueId() == (newBand.getVenueId());
    }
  }

  public void save() {
    String sql = "INSERT INTO bands (band_name, genre, venue_id) VALUES (:band_name, :genre, :venue_id)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
          .addParameter("band_name", band_name)
          .addParameter("genre", genre)
          .addParameter("venue_id", venue_id)
          .executeUpdate()
          .getKey();
    }
  }

  public static Band find(int id) {
    String sql = "SELECT id, band_name, genre, venue_id FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Band bands = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return bands;
    }
  }

  public void updateBandName(String band_name) {
    String sql = "UPDATE bands SET band_name = :band_name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("band_name", band_name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateGenre(String genre) {
    String sql = "UPDATE bands SET genre = :genre WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("genre", genre)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateVenueId(int venue_id) {
    String sql = "UPDATE bands SET venue_id = :venue_id WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("venue_id", venue_id)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  public void delete() {
    String sql = "DELETE FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
       .addParameter("id", id)
       .executeUpdate();
    }
  }

  public void addVenue (Venue venue) {
    String sql = "INSERT INTO band_venue (band, venue) VALUES (:id_band, :id_venue)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("id_band", this.getId())
         .addParameter("id_venue", band.getId())
         .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
//STIL NEEDS DOING WITH A FRESH HEAD
      String sql = "SELECT patron.* FROM copies " +
      "JOIN patron_copy ON (copies.id = patron_copy.id_title) " +
      "JOIN patron ON (patron_copy.id_patron = patron.id) " +
      "WHERE copy.id = :id";
      List<Patron> patrons = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Patron.class);
      return patrons;
    }
  }
}

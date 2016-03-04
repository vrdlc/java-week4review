import org.sql2o.*;
import java.util.List;

public class Venue {
  private int id;
  private String venue_name;
  private String style; //CONCERT HALL, UNDERGROUND, STAND-UP, ETC.
  public int band_id;

  public int getId() {
    return id;
  }

  public String getVenueName() {
    return venue_name;
  }

  public String getStyle() {
    return style;
  }

  public int getBandId() {
    return band_id;
  }

  public Venue (String venue_name, String style, int band_id) {
    this.venue_name = venue_name;
    this.style = style;
    this.band_id = band_id;
  }

  public static List<Venue> all() {
    String sql = "SELECT id, venue_name, style, band_id FROM venues";
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
              this.getStyle().equals(newBand.getStyle()) &&
              this.getBandId() == (newBand.getBandId());
    }
  }

  public void save() {
    String sql = "INSERT INTO venues (venue_name, style, band_id) VALUES (:venue_name, :style, :band_id)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
          .addParameter("venue_name", venue_name)
          .addParameter("style", style)
          .addParameter("band_id", band_id)
          .executeUpdate()
          .getKey();
    }
  }

  public static Band find(int id) {
    String sql = "SELECT id, venue_name, style, band_id FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Venue venues = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venues;
    }
  }

  public void updateVenueName(String venue_name) {
    String sql = "UPDATE venues SET venue_name = :venue_name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("venue_name", venue_name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateStyle(String style) {
    String sql = "UPDATE venues SET style = :style WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("style", style)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateBandId(int band_id) {
    String sql = "UPDATE venues SET band_id = :band_id WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("band_id", band_id)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  public void delete() {
    String sql = "DELETE FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
       .addParameter("id", id)
       .executeUpdate();
    }
  }

  public void addBand (Band band) {
    String sql = "INSERT INTO band_venue (band, venue) VALUES (:id_band, :id_venue)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("id_band", band.getId())
         .addParameter("id_venue", venue.getId())
         .executeUpdate();
    }
  }

  public List<Band> getBands() {
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

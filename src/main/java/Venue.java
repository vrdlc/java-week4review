import org.sql2o.*;
import java.util.List;

public class Venue {
  private int id;
  private String venue_name;
  private String style; //CONCERT HALL, UNDERGROUND, STAND-UP, ETC.

  public int getId() {
    return id;
  }

  public String getVenueName() {
    return venue_name;
  }

  public String getStyle() {
    return style;
  }

  public Venue (String venue_name, String style) {
    this.venue_name = venue_name;
    this.style = style;
  }

  public static List<Venue> all() {
    String sql = "SELECT id, venue_name, style FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if(!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return  this.getVenueName().equals(newVenue.getVenueName()) &&
              this.getStyle().equals(newVenue.getStyle());
    }
  }

//CREATE
  public void save() {
    String sql = "INSERT INTO venues (venue_name, style) VALUES (:venue_name, :style)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
          .addParameter("venue_name", venue_name)
          .addParameter("style", style)
          .executeUpdate()
          .getKey();
    }
  }

//READ
  public static Venue find(int id) {
    String sql = "SELECT id, venue_name, style FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Venue venues = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venues;
    }
  }

//UPDATE
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

//DESTROY
  public void delete() {
    String sql = "DELETE FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
       .addParameter("id", id)
       .executeUpdate();
    }
  }


//JOIN
  public void addBand (Band band) {
    String sql = "INSERT INTO band_venue (id_band, id_venue) VALUES (:id_band, :id_venue)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("id_band", band.getId())
         .addParameter("id_venue", this.getId())
         .executeUpdate();
    }
  }

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT bands.* FROM venues " +
      "JOIN band_venue ON (venues.id = band_venue.id_venue) " +
      "JOIN bands ON (band_venue.id_band = bands.id) " +
      "WHERE venues.id = :id";
      List<Band> bands = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Band.class);
      return bands;
    }
  }
}

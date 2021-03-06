import org.sql2o.*;
import java.util.List;

public class Band {
  private int id;
  private String band_name;
  private String genre;

  public int getId() {
    return id;
  }

  public String getBandName() {
    return band_name;
  }

  public String getGenre() {
    return genre;
  }

  public Band (String band_name, String genre) {
    this.band_name = band_name;
    this.genre = genre;
  }

  public static List<Band> all() {
    String sql = "SELECT id, band_name, genre FROM bands ORDER BY band_name";
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
              this.getGenre().equals(newBand.getGenre());
    }
  }

//CREATE
  public void save() {
    String sql = "INSERT INTO bands (band_name, genre) VALUES (:band_name, :genre)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
          .addParameter("band_name", band_name)
          .addParameter("genre", genre)
          .executeUpdate()
          .getKey();
    }
  }

//READ
  public static Band find(int id) {
    String sql = "SELECT id, band_name, genre FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Band bands = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return bands;
    }
  }

//UPDATE
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

//DESTROY
  public void delete() {
    String sql = "DELETE FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
       .addParameter("id", id)
       .executeUpdate();

    String joinDeleteQuery = "DELETE FROM band_venue WHERE id_band = :id";
       con.createQuery(joinDeleteQuery)
       .addParameter("id", this.getId())
       .executeUpdate();
     }
    }


  public static void deleteAll() {
  try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM bands;";
    con.createQuery(deleteQuery)
    .executeUpdate();
  }
}

//JOIN
  public void addVenue (Venue venue) {
    String sql = "INSERT INTO band_venue (id_band, id_venue) VALUES (:id_band, :id_venue)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("id_band", this.getId())
         .addParameter("id_venue", venue.getId())
         .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT venues.* FROM bands " +
      "JOIN band_venue ON (bands.id = band_venue.id_band) " +
      "JOIN venues ON (band_venue.id_venue = venues.id) " +
      "WHERE bands.id = :id";
      List<Venue> venues = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Venue.class);
      return venues;
    }
  }//HUNG UP HERE. DOES SHOWDATE GET ITS OWN METHODS? OR CAN IT FIT INTO THESE JOIN STATEMENTS? AND IF SO, HOW?

  // public void addVenue (Venue venue) {
  //   String sql = "INSERT INTO band_venue (id_band, id_venue, show_date) VALUES (:id_band, :id_venue, :show_date)";
  //   try(Connection con = DB.sql2o.open()) {
  //     con.createQuery(sql)
  //        .addParameter("id_band", this.getId())
  //        .addParameter("id_venue", venue.getId())
  //        .addParameter("show_date", showDate.getShowDate())
  //        .executeUpdate();
  //   }
  // }
}

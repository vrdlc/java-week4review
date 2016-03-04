// import org.sql2o.*;
// import java.util.List;
//
// public class ShowDate {
//   private int id;
//   // private int id_band;
//   // private int id_venue;
//   private String show_date;
//
//   public int getId() {
//     return id;
//   }
//   //
//   // public int getIdBand() {
//   //   return id_band;
//   // }
//   //
//   // public int getIdVenue() {
//   //   return id_venue;
//   // }
//
//   public String getShowDate() {
//     return show_date;
//   }
//
//   public ShowDate (String show_date) {
//     this.show_date = show_date;
//   }
//
//   public static List<ShowDate> all() {
//     String sql = "SELECT id, show_date FROM band_venue";
//     try(Connection con = sql2o.open()) {
//       return con.createQuery(sql).executeAndFetch(ShowDate.class);
//     }
//   }
//
//   @Override
//   public boolean equals(Object otherShowDate) {
//     if(!(otherShowDate instanceof ShowDate)) {
//     } else {
//       ShowDate newShowDate = (ShowDate) otherShowDate;
//       return this.getShowDate().equals(newShowDate.getShowDate());
//     }
//   }
//
// //CREATE
//   public void save() {
//     String sql = "INSERT INTO band_venue (show_date) VALUES (:show_date)";
//     try(Connection con = sql2o.open()) {
//       this.id = (int) con.createQuery(sql, true)
//           .addParameter("show_date", show_date)
//           .executeUpdate()
//           .getKey();
//         }
//   }
//
// //READ
//   public static ShowDate find(int id) {
//     String sql = "SELECT id, show_date FROM band_venue WHERE id = :id";
//     try(Connection con = sql2o.open()) {
//       ShowDate showDate = con.createQuery(sql)
//       .addParameter("id", id)
//       .executeAndFetchFirst(ShowDate.class);
//       return showDate;
//   }
// }
//
// //UPDATE
//   public void updateShowDate(String show_date) {
//     String sql = "UPDATE band_venue SET show_date = :show_date WHERE id = :id";
//     try(Connection con = sql2o.open()) {
//       con.createQuery(sql)
//       .addParameter("show_date", show_date)
//       .addParameter("id", id)
//       .executeUpdate();
//     }
//   }
//
// //DESTROY
//   public void delete() {
//     String sql = "DELETE FROM band_venue WHERE id = :id";
//     try(Connection con = sql2o.open()) {
//       con.createQuery(sql)
//           .addParameter("id", id)
//           .executeUpdate();
//         }
//   }
//
//
//
//
//
//
//
//
//
//
// }

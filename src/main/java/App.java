import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request,response) -> { //HOME PAGE W/ BANDS AND VENUES
      HashMap model = new HashMap();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band", (request, response) -> { //POSTS TITLES TO BOOKS PAGE
      HashMap model = new HashMap();
      String band_name = request.queryParams("newBandName");
      String genre = request.queryParams("newBandGenre");
      Band newBand = new Band(band_name, genre);
      newBand.save();
      response.redirect("/");
      return null;
    });

    post("/venue", (request, response) -> { //POSTS AUTHORS TO BOOKS PAGE
      HashMap model = new HashMap();
      String venue_name = request.queryParams("newVenue");
      String style = request.queryParams("newVenueStyle");
      Venue newVenue = new Venue(venue_name, style);
      newVenue.save();
      response.redirect("/");
      return null;
    });

    get("/band/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.params("id"));
      Band band = Band.find(id);
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/:id", (request, response) -> {
      HashMap model = new HashMap();
      int bandId = Integer.parseInt(request.queryParams("bandId"));
      int venueId = Integer.parseInt(request.queryParams("venueId"));
      Venue venue = Venue.find(venueId);
      Band band = Band.find(bandId);
      band.addVenue(venue);
      response.redirect("/band/" + bandId);
      return null;
    });

    get("/venue/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.params("id"));
      Venue venue = Venue.find(id);
      model.put("venue", venue);
      model.put("allBands", Band.all());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venue/:id", (request, response) -> {
      HashMap model = new HashMap();
      int venueId = Integer.parseInt(request.queryParams("venueId"));
      int bandId = Integer.parseInt(request.queryParams("bandId"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(venueId);
      venue.addBand(band);;
      response.redirect("/venue/" + venueId);
      return null;
    });
  //
  //   get("/patrons", (request,response) -> { //PATRONS PAGE W/ FORM TO ENTER PATRON NAME
  //     HashMap model = new HashMap();
  //     model.put("patrons", Patron.all());
  //     model.put("template", "templates/patrons.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   post("/patrons", (request, response) -> { //POSTS PATRONS TO PATRON PAGE
  //     HashMap model = new HashMap();
  //     String patron = request.queryParams("newPatron");
  //     Patron newPatron = new Patron(patron);
  //     newPatron.save();
  //     response.redirect("/patrons");
  //     return null;
  //   });
  //
  //
  //   get("/patrons/:id", (request, response) -> {
  //     HashMap model = new HashMap();
  //     int id = Integer.parseInt(request.params("id"));
  //     Patron patron = Patron.find(id);
  //     model.put("patron", patron);
  //     model.put("allCopies", Copies.all());
  //     model.put("template", "templates/patron.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   post("/patrons/:id", (request, response) -> {
  //     HashMap model = new HashMap();
  //     int patronId = Integer.parseInt(request.params("id"));
  //     int copyId = Integer.parseInt(request.queryParams("copy"));
  //     Copies copies = Copies.find(copyId);
  //     Patron newPatron = Patron.find(patronId);
  //     newPatron.addCopies(copies);
  //     response.redirect("/patrons/" + patronId);
  //     return null;
  //   });
  }

}

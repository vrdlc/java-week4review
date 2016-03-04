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

    post("/band", (request, response) -> { //POSTS BANDS TO HOME PAGE
      HashMap model = new HashMap();
      String band_name = request.queryParams("newBandName");
      String genre = request.queryParams("newBandGenre");
      Band newBand = new Band(band_name, genre);
      newBand.save();
      response.redirect("/");
      return null;
    });

    post("/venue", (request, response) -> { //POSTS VENUES TO HOME PAGE
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

    post("/update/band/:id", (request, response) -> {
      HashMap model = new HashMap();
      int bandId = (Integer.parseInt(request.queryParams("bandId")));
      Band band = Band.find(bandId);
      String bandName = request.queryParams("bandName");
      String genre = request.queryParams("genre");
      band.updateBandName(bandName);
      band.updateGenre(genre);
      response.redirect("/band/" + bandId);
      return null;
    });

    post("/delete/band/:id", (request, response) -> { //DELETES BANDS INDIVIDUALLY
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.queryParams("bandId"));
      Band band = Band.find(id);
      band.delete();
      response.redirect("/");
      return null;
    });
    // get("/update/band/:id", (request, response) -> {
    //   HashMap model = new HashMap();
    //   String bandId = request.params(":id");
    //   Band band = Band.find(Integer.parseInt(bandId));
    //   model.put("band", band);
    //   model.put("genre", genre);
    //   model.put("template", "templates/update-band.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());


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

    post("/delete/venue/:id", (request, response) -> { //DELETES BANDS INDIVIDUALLY
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.queryParams("venueId"));
      Venue venue = Venue.find(id);
      venue.delete();
      response.redirect("/");
      return null;
    });


  }

}

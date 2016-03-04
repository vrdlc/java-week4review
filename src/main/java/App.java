import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> { //HOME PAGE
      HashMap model = new HashMap();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books", (request,response) -> { //BOOKS PAGE W/ TITLES AND AUTHORS
      HashMap model = new HashMap();
      model.put("titles", Title.all());
      model.put("authors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/title", (request, response) -> { //POSTS TITLES TO BOOKS PAGE
      HashMap model = new HashMap();
      String title = request.queryParams("newBookTitle");
      Title newTitle = new Title(title);
      newTitle.save();
      response.redirect("/books");
      return null;
    });

    post("/author", (request, response) -> { //POSTS AUTHORS TO BOOKS PAGE
      HashMap model = new HashMap();
      String author = request.queryParams("newAuthor");
      Author newAuthor = new Author(author);
      newAuthor.save();
      response.redirect("/books");
      return null;
    });


    get("/title/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.params("id"));
      Title title = Title.find(id);
      model.put("title", title);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/title.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/title/:id", (request, response) -> {
      HashMap model = new HashMap();
      int titleId = Integer.parseInt(request.queryParams("titleId"));
      int authorId = Integer.parseInt(request.queryParams("authorName"));
      Author author = Author.find(authorId);
      Title title = Title.find(titleId);
      title.addAuthor(author);
      response.redirect("/title/" + titleId);
      return null;
    });

    get("/author/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.params("id"));
      Author author = Author.find(id);
      model.put("author", author);
      model.put("allTitles", Title.all());
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/author/:id", (request, response) -> {
      HashMap model = new HashMap();
      int authorId = Integer.parseInt(request.queryParams("authorId"));
      int titleId = Integer.parseInt(request.queryParams("titleName"));
      Title title = Title.find(titleId);
      Author author = Author.find(authorId);
      author.addTitle(title);
      response.redirect("/author/" + authorId);
      return null;
    });

    get("/patrons", (request,response) -> { //PATRONS PAGE W/ FORM TO ENTER PATRON NAME
      HashMap model = new HashMap();
      model.put("patrons", Patron.all());
      model.put("template", "templates/patrons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons", (request, response) -> { //POSTS PATRONS TO PATRON PAGE
      HashMap model = new HashMap();
      String patron = request.queryParams("newPatron");
      Patron newPatron = new Patron(patron);
      newPatron.save();
      response.redirect("/patrons");
      return null;
    });


    get("/patrons/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.params("id"));
      Patron patron = Patron.find(id);
      model.put("patron", patron);
      model.put("allCopies", Copies.all());
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons/:id", (request, response) -> {
      HashMap model = new HashMap();
      int patronId = Integer.parseInt(request.params("id"));
      int copyId = Integer.parseInt(request.queryParams("copy"));
      Copies copies = Copies.find(copyId);
      Patron newPatron = Patron.find(patronId);
      newPatron.addCopies(copies);
      response.redirect("/patrons/" + patronId);
      return null;
    });
  }
//
//   //public static 'Returntype' 'FuncName' (Paramtype param) {}  //first business logic function

}

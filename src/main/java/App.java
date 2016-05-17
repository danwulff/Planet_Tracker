import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;



public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users", (request, response) -> {
      String userName = request.queryParams("newUserName");
      String userEmail = request.queryParams("userEmail");
      String userTelephone = request.queryParams("userTelephone");
      String month = request.queryParams("month");
      String day = request.queryParams("day");
      String year = request.queryParams("year");
      String time = request.queryParams("time");
      User user = new User(userName, userEmail, userTelephone);
      String userPhone = user.getPhone();
      ArrayList<User> userList = new ArrayList<User>();

      for(User eachUser : User.all()) {
        userList.add(eachUser);
        }

      for(User newuser : userList) {
        if (!(userPhone.equals(newuser.getPhone()))){
        user.save();
        user.setTime(month, day, year, time);
        }
      }

      response.redirect("/");
      return null;
    });

    get("/users", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String nameLogin = request.queryParams("nameLogin");
      String emailLogin = request.queryParams("emailLogin");
      String phoneLogin = request.queryParams("phoneLogin");
      User newUser = new User(nameLogin, emailLogin, phoneLogin);

      for(User user : User.all()) {
        if(newUser.getPhone().equals(user.getPhone())){
          User newerUser = User.find(user.getId());
          model.put("user", newerUser);
        }
      }

      String[] planetNames = {"mars", "venus", "neptune", "uranus", "mercury", "jupiter", "saturn", "pluto"};
      ArrayList<Planet> planets = new ArrayList<Planet>();
      // Planet foundPlanet = Planet.find(newerUser, "mars");
      // planets.add(foundPlanet);
      // for(String planetName : planetNames) {
      //   Planet foundPlanet = Planet.find(user, planetName);
      //   planets.add(foundPlanet);
      // }
      model.put("users", User.all());
      model.put("planets", planets);
      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}

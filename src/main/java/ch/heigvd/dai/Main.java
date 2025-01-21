package ch.heigvd.dai;

import static io.javalin.apibuilder.ApiBuilder.*;

import ch.heigvd.dai.cache.Cache;
import ch.heigvd.dai.db.Database;
import ch.heigvd.dai.endpoints.*;
import io.javalin.Javalin;
import io.javalin.config.Key;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    Database db;
    Cache cache = new Cache();

    try {
      db = new Database("localhost", "bdr", "bdr", "bdr", "gpg_keyserver", 5432);
      System.out.println("Connected to database!");
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return;
    }

    Dummy dummy = new Dummy();

    var test = db.users.getAll();

    var app =
        Javalin.create(
            config -> {
              config.appData(new Key<>("database"), db);
              config.appData(new Key<>("cache"), cache);

              config.router.apiBuilder(
                  () -> {
                    path(
                        "/",
                        () -> {
                          get(dummy::DummyEndpoint);
                        });
                  });
            });

    app.start(7070);

    try {
      db.close();
      System.out.println("Connection to database closed!");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}

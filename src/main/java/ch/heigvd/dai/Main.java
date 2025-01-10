package ch.heigvd.dai;
import ch.heigvd.dai.cache.Cache;
import ch.heigvd.dai.db.Database;
import ch.heigvd.dai.endpoints.*;
import io.javalin.Javalin;
import io.javalin.config.Key;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

  public static void main(String[] args) {
    Database db = new Database();
    Cache cache = new Cache();

    Dummy dummy = new Dummy();

    var app = Javalin.create(config -> {
      config.appData(new Key<>("database"), db);
      config.appData(new Key<>("cache"), cache);

          config.router.apiBuilder(() -> {
            path("/", () -> {
              get(dummy::DummyEndpoint);
            });
          });
        });

    app.start(7070);
  }
}

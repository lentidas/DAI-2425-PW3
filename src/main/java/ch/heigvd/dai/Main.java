package ch.heigvd.dai;

import static io.javalin.apibuilder.ApiBuilder.*;

import ch.heigvd.dai.cache.Cache;
import ch.heigvd.dai.db.Database;
import ch.heigvd.dai.endpoints.*;
import io.javalin.Javalin;
import io.javalin.config.Key;
import io.javalin.http.ContentType;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class Main {

  public static final int PORT = 7070;

  public static void main(String[] args) {
    Database db;
    Cache cache = new Cache();

    try {
      // TODO Improve the way we variabilize the database connection parameters
      db =
          new Database(
              "localhost", "gpg_keyserver_db", "gpg_keyserver", "password", "gpg_keyserver", 5432);
      System.out.println("Connected to database!");
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return;
    }

    Dummy dummy = new Dummy();
    UsersEndpoint users = new UsersEndpoint();
    EmailsEndpoint emails = new EmailsEndpoint();
    GPGKeysEndpoints gpgKeys = new GPGKeysEndpoints();

    var app =
        Javalin.create(
            config -> {
              config.appData(new Key<>("database"), db);
              config.appData(new Key<>("cache"), cache);

              // FIXME Does not work because it is overridden by the .json() method, maybe need to
              //  use the .result() method and find another way to generate the JSON string
              config.http.defaultContentType =
                  ContentType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name();

              // TODO Remove dummy endpoint
              config.router.apiBuilder(() -> path("/", () -> get(dummy::DummyEndpoint)));

              // Configure the API routes for the /users domain.
              config.router.apiBuilder(
                  () ->
                      path(
                          "/users",
                          () -> {
                            get(users::getUsers);
                            post(users::createUser);
                            path(
                                "/{username}",
                                () -> {
                                  get(users::getUser);
                                  put(users::updateUser);
                                  delete(users::deleteUser);

                                  path(
                                      "/{email-id}",
                                      () -> {
                                        put(users::updateUserEmail);
                                        delete(users::deleteUserEmail);
                                      });
                                });
                          }));

              // Configure the API routes for the /emails domain.
              config.router.apiBuilder(
                  () ->
                      path(
                          "/emails",
                          () -> {
                            get(emails::getEmails);
                            path(
                                "/{username}",
                                () -> {
                                  post(emails::addUserEmail);
                                  get(emails::getUserEmails);
                                  delete(emails::deleteUserEmail);
                                });
                          }));

              // Configure the API routes for the /gpg-keys domain.
              config.router.apiBuilder(
                  () ->
                      path(
                          "/gpg-keys",
                          () -> {
                            get(gpgKeys::getGPGKeys);
                            post(gpgKeys::addGPGKey);
                            path(
                                "/{fingerprint}",
                                () -> {
                                  get(gpgKeys::getGPGKey);
                                  put(gpgKeys::updateGPGKey);
                                  delete(gpgKeys::deleteGPGKey);
                                });
                          }));
            });

    app.start(PORT);

    // FIXME See if we need to close the database connection or simply quitting the application
    //  should suffice. Leaving this here would close the database although the application
    //  is still running...
    // try {
    //   db.close();
    //   System.out.println("Connection to database closed!");
    // } catch (SQLException e) {
    //   throw new RuntimeException(e);
    // }
  }
}

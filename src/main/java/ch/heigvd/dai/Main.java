package ch.heigvd.dai;

import static io.javalin.apibuilder.ApiBuilder.*;

import ch.heigvd.dai.cache.Cache;
import ch.heigvd.dai.db.Database;
import ch.heigvd.dai.endpoints.*;
import io.javalin.Javalin;
import io.javalin.config.Key;
import io.javalin.http.ContentType;
import java.nio.charset.StandardCharsets;

public class Main {

  public static final int PORT = 7070;

  public static void main(String[] args) {
    Database db = new Database();
    Cache cache = new Cache();

    Dummy dummy = new Dummy();

    Users users = new Users();
    GPGKeys gpgKeys = new GPGKeys();

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
              config.router.apiBuilder(
                  () -> {
                    path(
                        "/",
                        () -> {
                          get(dummy::DummyEndpoint);
                        });
                  });

              // Configure the API routes for the /users domain.
              config.router.apiBuilder(
                  () -> {
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
                        });
                  });

              // Configure the API routes for the /gpg-keys domain.
              config.router.apiBuilder(
                  () -> {
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
                        });
                  });
            });

    app.start(7070);
  }
}

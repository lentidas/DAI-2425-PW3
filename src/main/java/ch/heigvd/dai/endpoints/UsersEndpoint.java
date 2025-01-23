/*
 * gpg-keyserver - a web app to store user's GPG keys
 * Copyright (C) 2024 Pedro Alves da Silva, Gonçalo Carvalheiro Heleno
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.heigvd.dai.endpoints;

import ch.heigvd.dai.db.Database;
import ch.heigvd.dai.db.Users.User;
import io.javalin.config.Key;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class UsersEndpoint {

  public UsersEndpoint() {}

  public void getUsers(@NotNull Context ctx) {
    // TODO
    final Database database = ctx.appData(new Key<>("database"));
    List<User> users;

    String firstName = ctx.queryParam("firstName");
    String lastName = ctx.queryParam("lastName");

    if (firstName == null && lastName == null) {
      users = database.getUsers().getAll();
    } else {
      users = database.getUsers().search(firstName, lastName);
    }

    ctx.json(users);

    // FIXME Consider how to better set the content type to JSON with UTF-8 encoding because the
    //  default Content-Type is overridden by the .json() method.
    // Set content type to JSON with UTF-8 encoding to avoid problems with special characters,
    // because the .json() method does not allow setting the charset.
    // ctx.contentType(ContentType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name());
  }

  public void getUser(@NotNull Context ctx) {
    final Database database = ctx.appData(new Key<>("database"));

    String username = ctx.pathParamAsClass("username", String.class).get();
    User user = database.getUsers().getOne(username);

    if (user == null) {
      throw new NotFoundResponse();
    } else {
      ctx.json(user);
    }
  }

  public void createUser(@NotNull Context ctx) {
    // TODO
  }

  public void updateUser(@NotNull Context ctx) {
    // TODO
  }

  public void deleteUser(@NotNull Context ctx) {
    // TODO

    // TODO Do not forget that this should cascade and delete all the related data of the user from
    //  the database
  }

  public void updateUserEmail(@NotNull Context ctx) {
    // TODO
  }

  public void deleteUserEmail(@NotNull Context ctx) {
    // TODO
  }
}

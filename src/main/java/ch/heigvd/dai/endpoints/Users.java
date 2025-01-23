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

import ch.heigvd.dai.objects.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class Users {

  public Users() {}

  public void getUsers(@NotNull Context ctx) {
    // TODO

    User user = new User("lentidas", "Gonçalo", "Heleno");

    ctx.json(user);

    // Set content type to JSON with UTF-8 encoding to avoid problems with special characters,
    // because the .json() method does not allow setting the charset.
    // ctx.contentType(ContentType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name());
  }

  public void getUser(@NotNull Context ctx) {
    // TODO
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
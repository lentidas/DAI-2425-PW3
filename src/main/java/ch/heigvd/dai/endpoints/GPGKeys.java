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

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class GPGKeys {

  public GPGKeys() {}

  public void getGPGKeys(@NotNull Context ctx) {
    // TODO Should provide a way to filter the keys by email or fingerprint (maybe username?)
  }

  public void addGPGKey(@NotNull Context ctx) {
    // TODO When adding a GPG key, the email must already exist in the database
  }

  public void getGPGKey(@NotNull Context ctx) {}

  public void updateGPGKey(@NotNull Context ctx) {
    // TODO Should provide a way to update the key's emails or the key itself
  }

  public void deleteGPGKey(@NotNull Context ctx) {}
}

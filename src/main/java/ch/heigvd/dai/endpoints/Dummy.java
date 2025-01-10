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

import ch.heigvd.dai.cache.Cache;
import ch.heigvd.dai.db.Database;
import io.javalin.config.Key;
import io.javalin.http.*;
import org.jetbrains.annotations.NotNull;

public class Dummy {
  public Dummy() {}

  public void DummyEndpoint(@NotNull Context ctx) throws Exception {
    final Database database = ctx.appData(new Key<>("database"));
    final Cache cache = ctx.appData(new Key<>("cache"));

    ctx.result("404 - Found");
  }
}

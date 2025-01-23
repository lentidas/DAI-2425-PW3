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

package ch.heigvd.dai.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GPGKeys {

  /**
   * Record that represents a GPG key.
   *
   * @param fingerprint a {@link String} that is the hexadecimal fingerprint of the key
   * @param key a {@link String} that is the key itself in ASCII armor format
   */
  public record GPGKey(String fingerprint, String key) {}

  private final Database db;

  /**
   * Default constructor for the GPGKeys class.
   *
   * @param ownerDb a {@link Database} that is the database that owns this instance
   */
  public GPGKeys(Database ownerDb) {
    this.db = ownerDb;
  }

  /**
   * Extracts all needed information from ResultSet to a GPGKey instance
   *
   * @param rs a {@link ResultSet} to extract information from
   * @return a {@link GPGKey} that is the instance with the extracted information
   * @throws SQLException if one or more of the columns hasn't been found
   */
  private GPGKey parseResult(ResultSet rs) throws SQLException {
    String fingerprint = rs.getString("fingerprint");
    String key = rs.getString("key");
    return new GPGKey(fingerprint, key);
  }

  public List<GPGKey> getAll() {
    ArrayList<GPGKey> keys = new ArrayList<>();

    try (Statement stmt = db.getStatement()) {
      String query = "SELECT * FROM gpg_keyserver.gpg_keys;";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        keys.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return keys;
  }
}

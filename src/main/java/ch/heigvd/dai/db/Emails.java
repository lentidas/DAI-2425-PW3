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

public class Emails {

  /**
   * Record that holds all the information about an email address row
   *
   * @param email Email address
   * @param username User that owns the email address
   */
  public record Email(String email, String username) {}

  private final Database _db;

  /**
   * Creates a new emails table instance
   *
   * @param ownerDb Owner database instance
   */
  public Emails(Database ownerDb) {
    _db = ownerDb;
  }

  /**
   * Extracts all needed information from ResultSet to for a Email instance
   *
   * @param rs Result set to extract information from
   * @return Email instance
   * @throws SQLException Raised if one or more of the columns hasn't been found
   */
  private Email parseResult(ResultSet rs) throws SQLException {
    String email = rs.getString("email");
    String username = rs.getString("username");
    return new Email(email, username);
  }

  /**
   * Gets all emails from the database
   *
   * @return A list of emails, or null if an error occurred
   */
  public Email[] getAll() {
    ArrayList<Email> emails = new ArrayList<>();

    try (Statement stmt = _db.getStatement()) {
      String query = "select * from gpg_keyserver.emails;";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        emails.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return emails.toArray(new Email[0]);
  }

  /**
   * Gets a specific email
   *
   * @param email Email address to find
   * @return Email's instance, or null if an error occurred
   */
  public Email getOne(String email) {
    Email found_email;

    try (Statement stmt = _db.getStatement()) {
      String query = "select * from gpg_keyserver.emails" + " where email = '" + email + "';";

      ResultSet queryResult = stmt.executeQuery(query);
      if (!queryResult.next()) {
        return null;
      }

      found_email = parseResult(queryResult);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return found_email;
  }

  /**
   * Gets all emails owned by the specified user
   *
   * @return A list of emails, or null if an error occurred
   */
  public Email[] getByUser(String username) {
    ArrayList<Email> emails = new ArrayList<>();

    try (Statement stmt = _db.getStatement()) {
      String query = "select * from gpg_keyserver.emails" + " where username = '" + username + "';";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        emails.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return emails.toArray(new Email[0]);
  }
}

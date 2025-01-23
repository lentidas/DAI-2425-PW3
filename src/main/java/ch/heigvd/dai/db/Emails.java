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
import org.apache.commons.validator.routines.EmailValidator;

public class Emails {

  /**
   * Record that holds all the information about an email address row
   *
   * @param email Email address
   * @param username User that owns the email address
   */
  public record Email(String email, String username) {}

  private final Database db;

  /**
   * Creates a new emails table instance
   *
   * @param ownerDb Owner database instance
   */
  public Emails(Database ownerDb) {
    db = ownerDb;
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

    try (Statement stmt = db.getStatement()) {
      String query = "select * from " + db.schema + ".emails;";

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

    try (Statement stmt = db.getStatement()) {
      String query = "select * from " + db.schema + ".emails" + " where email = '" + email + "';";

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

    try (Statement stmt = db.getStatement()) {
      String query =
          "select * from " + db.schema + ".emails" + " where username = '" + username + "';";

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
   * Checks whether the provided username already has the provided email associated to them
   *
   * @param username Username to use for query
   * @param email Email to check
   * @return True if email is associated to user, false if not or if an error occurred
   */
  public boolean userHasEmail(String username, String email) {
    boolean found = false;

    try (Statement stmt = db.getStatement()) {
      String query =
          "select * from " + db.schema + ".emails" + " where username = '" + username + "';";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        if (parseResult(queryResult).email.equals(email)) {
          found = true;
          break;
        }
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

    return found;
  }

  /**
   * Associates the provided email to the provided user
   *
   * @param email Email model object to use for query
   * @return -1 if an error occurred, or 0 if successful
   */
  public int addToUser(Email email) {
    try (Statement stmt = db.getStatement()) {
      String query =
          "insert into "
              + db.schema
              + ".emails (email, username) values ('"
              + email.email()
              + "', '"
              + email.username()
              + "');";
      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Detaches the provided email from its user. Function does not check whether email is associated
   * to the user that the caller wants to detach the email from
   *
   * @param email Email to detach
   * @return -1 if an error occurred, or 0 if successful
   */
  public int detach(String email) {
    try (Statement stmt = db.getStatement()) {
      String query = "delete from " + db.schema + ".emails where email = '" + email + "');";
      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Validates an email address.
   *
   * @param email a {@link String} with the email to validate
   * @return {@code true} if the email address is valid, {@code false} otherwise
   */
  public static boolean validateEmailAddress(String email) {
    return EmailValidator.getInstance().isValid(email);
  }
}

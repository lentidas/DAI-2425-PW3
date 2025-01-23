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

public class Users {

  /**
   * Record that holds all the information about a user.
   *
   * @param username a {@link String} with the user's username
   * @param firstName a {@link String} with the user's first name
   * @param lastName a {@link String} with the user's last name
   */
  public record User(String username, String firstName, String lastName) {}

  /**
   * Record that holds all the information about a user, except the username.
   *
   * <p>Used in special cases, like when updating the user's information.
   *
   * @param firstName a {@link String} with the user's first name
   * @param lastName a {@link String} with the user's last name
   */
  public record UserWithoutUsername(String firstName, String lastName) {}

  private final Database db;

  /**
   * Default constructor for the Users class.
   *
   * @param ownerDb a {@link Database} that is the database that owns this instance
   */
  public Users(Database ownerDb) {
    this.db = ownerDb;
  }

  /**
   * Extracts all needed information from ResultSet to a User instance
   *
   * @param rs a {@link ResultSet} to extract information from
   * @return a record of type {@link User} with the extracted information
   * @throws SQLException if one or more of the columns hasn't been found
   */
  private User parseResult(ResultSet rs) throws SQLException {
    String username = rs.getString("username");
    String firstName = rs.getString("first_name");
    String lastName = rs.getString("last_name");
    return new User(username, firstName, lastName);
  }

  /**
   * Gets all users from the database.
   *
   * @return a {@link List} of {@link User} instances, or {@code null} if an error occurred
   */
  public List<User> getAll() {
    ArrayList<User> users = new ArrayList<>();

    try (Statement stmt = db.getStatement()) {
      String query = "SELECT * FROM gpg_keyserver.users;";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        users.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return users;
  }

  /**
   * Searches for a user in the database using their first or last names.
   *
   * @param firstName a {@link String} with the user's first name
   * @param lastName a {@link String} with the user's last name
   * @return a {@link List} of {@link User} instances, or {@code null} if an error occurred
   */
  public List<User> search(String firstName, String lastName) {
    // If all parameters are null, return null, because the query cannot work with null String
    // values.
    if (firstName == null && lastName == null) {
      return null;
    }

    ArrayList<User> users = new ArrayList<>();

    try (Statement stmt = db.getStatement()) {
      StringBuilder query = new StringBuilder("SELECT * FROM gpg_keyserver.users WHERE ");

      if (firstName != null) {
        query.append("first_name iLIKE '%").append(firstName).append("%'");
        if (lastName != null) {
          query.append(" AND ");
        }
      }
      if (lastName != null) {
        query.append("last_name iLIKE '%").append(lastName).append("%'");
      }

      ResultSet queryResult = stmt.executeQuery(query.toString());
      while (queryResult.next()) {
        users.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return users;
  }

  /**
   * Gets a specific user from the database using their username.
   *
   * @param username a {@link String} with the user's username
   * @return a {@link User} instance, or {@code null} if an error occurred or if no user was found
   */
  public User getOne(String username) {
    User user;

    try (Statement stmt = db.getStatement()) {
      String query = "SELECT * FROM gpg_keyserver.users WHERE username = '" + username + "';";

      ResultSet queryResult = stmt.executeQuery(query);
      if (!queryResult.next()) {
        return null;
      }

      user = parseResult(queryResult);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return user;
  }

  /**
   * Creates a new user row.
   *
   * @param user User to insert into database
   * @return -1 in case of an error, other value if no error
   */
  public int createUser(User user) {
    try (Statement stmt = db.getStatement()) {
      StringBuilder query =
          new StringBuilder(
              "INSERT INTO gpg_keyserver.users (username, first_name, last_name) VALUES (");
      query.append("'").append(user.username).append("', ");
      query.append("'").append(user.firstName).append("', ");
      query.append("'").append(user.lastName).append("');");

      return stmt.executeUpdate(query.toString());
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Updates an existing user with new datas
   *
   * @param user a {@link User} instance with the new user data
   * @return -1 in case of an error, other value if no error
   */
  public int updateUser(User user) {
    try (Statement stmt = db.getStatement()) {
      StringBuilder query = new StringBuilder("UPDATE gpg_keyserver.users SET ");
      query.append("first_name = '").append(user.firstName).append("', ");
      query.append("last_name = '").append(user.lastName).append("' ");
      query.append("WHERE username = '").append(user.username).append("';");

      return stmt.executeUpdate(query.toString());
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Deletes a specific user from the database.
   *
   * @param user a {@link User} instance with the user to delete
   * @return -1 in case of an error, other value if no error
   */
  public int deleteUser(User user) {
    try (Statement stmt = db.getStatement()) {
      String query =
          "DELETE FROM gpg_keyserver.users" + " WHERE username = '" + user.username + "';";

      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Validates a username.
   *
   * <p>A valid username must have between 3 and 32 characters and can only contain letters,
   * numbers, underscores, hyphens, and dots.
   *
   * @param username a {@link String} with the username to validate
   * @return {@code true} if the username is valid, {@code false} otherwise
   */
  public static boolean validateUsername(String username) {
    return username.matches("^[a-zA-Z0-9._-]{3,32}$");
  }
}

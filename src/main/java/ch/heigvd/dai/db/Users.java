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

public class Users {

  /**
   * Record that holds all the information about a user
   *
   * @param username Username
   * @param first_name User's first name
   * @param last_name User's last name
   */
  public record User(String username, String first_name, String last_name) {}

  private final Database _db;

  /**
   * Creates a new users table instance
   *
   * @param ownerDb Owner database instance
   */
  public Users(Database ownerDb) {
    _db = ownerDb;
  }

  /**
   * Extracts all needed information from ResultSet to for a User instance
   *
   * @param rs Result set to extract information from
   * @return User instance
   * @throws SQLException Raised if one or more of the columns hasn't been found
   */
  private User parseResult(ResultSet rs) throws SQLException {
    String username = rs.getString("username");
    String first_name = rs.getString("first_name");
    String last_name = rs.getString("last_name");
    return new User(username, first_name, last_name);
  }

  /**
   * Gets all users from the database
   *
   * @return A list of users, or null if an error occurred
   */
  public User[] getAll() {
    ArrayList<User> users = new ArrayList<>();

    try (Statement stmt = _db.getStatement()) {
      String query = "select * from gpg_keyserver.users;";

      ResultSet queryResult = stmt.executeQuery(query);
      while (queryResult.next()) {
        users.add(parseResult(queryResult));
      }
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return null;
    }

    return users.toArray(new User[0]);
  }

  /**
   * Gets a user, based on their username
   *
   * @param username Username to find
   * @return User's instance, or null if an error occurred
   */
  public User getOne(String username) {
    User user;

    try (Statement stmt = _db.getStatement()) {
      String query = "select * from gpg_keyserver.users where username = '" + username + "';";

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
   * Creates a new user row
   *
   * @param user User to insert into database
   * @return -1 in case of an error, other value if no error
   */
  public int createUser(User user) {
    try (Statement stmt = _db.getStatement()) {
      String query =
          "insert into gpg_keyserver.users"
              + "('"
              + user.username
              + "','"
              + user.first_name
              + "','"
              + user.last_name
              + "');";

      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Updates an existing user with new data
   *
   * @param user User to updated in the database
   * @return -1 in case of an error, other value if no error
   */
  public int updateUser(User user) {
    try (Statement stmt = _db.getStatement()) {
      String query =
          "update gpg_keyserver.users"
              + " set first_name = '"
              + user.first_name
              + "'"
              + " set last_name = '"
              + user.last_name
              + "'"
              + " where username = '"
              + user.username
              + "';";

      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Deletes a specific user from the database
   *
   * @param user User to deleted from the database
   * @return -1 in case of an error, other value if no error
   */
  public int deleteUser(User user) {
    try (Statement stmt = _db.getStatement()) {
      String query =
          "delete from gpg_keyserver.users" + " where username = '" + user.username + "';";

      return stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return -1;
    }
  }
}

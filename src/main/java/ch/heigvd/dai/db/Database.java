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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

  private final Connection _connection;
  final String schema;
  public final Users users;

  /**
   * Initiates a connection to a PostgresSQL database
   *
   * @param host Database host
   * @param database Database name
   * @param username Username to connect with
   * @param password User's password
   * @param schema Database schema
   * @param port Database port
   * @throws SQLException See java.sql.Connection.DriverManager.getConnection() for more information
   */
  public Database(
      String host, String database, String username, String password, String schema, int port)
      throws SQLException {

    String connectionString = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    _connection = DriverManager.getConnection(connectionString, username, password);
    this.schema = schema;
    _connection.setCatalog(schema);

    users = new Users(this);
  }

  Statement getStatement() throws SQLException {
    return _connection.createStatement();
  }

  public void close() throws SQLException {
    _connection.close();
  }
}

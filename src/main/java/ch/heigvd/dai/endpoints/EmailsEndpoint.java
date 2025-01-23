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
import ch.heigvd.dai.db.Emails;
import ch.heigvd.dai.db.Emails.Email;
import ch.heigvd.dai.db.GPGKeys.GPGKey;
import ch.heigvd.dai.db.Users;
import io.javalin.config.Key;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;

public class EmailsEndpoint {

  /**
   * Recording holding both an email address and a GPG fingerprint. Used for POSTing an email
   * address
   *
   * @param email Email address
   * @param fingerprint Fingerprint to link to provided email address
   */
  private record EmailAndFingerprint(String email, String fingerprint) {}

  /**
   * Validates the provided username by checking whether it is valid, and exists in the database
   *
   * @param username Username to check
   * @param database Database instance
   * @throws BadRequestResponse Thrown when username is invalid
   * @throws NotFoundResponse Thrown when user does not exist in the database
   */
  private void validateUser(String username, Database database)
      throws BadRequestResponse, NotFoundResponse {
    if (!Users.validateUsername(username)) {
      throw new BadRequestResponse();
    }

    if (null == database.getUsers().getOne(username)) {
      throw new NotFoundResponse();
    }
  }

  /**
   * Validates both the username's validity and existence, as well as the email's validity and,
   * depending on the value of expectToExist, whether the email is attached to provider user, or not
   * attached to any user
   *
   * @param username Username to check
   * @param email Email to check
   * @param database Database instance
   * @param expectToExist When false, expects emails to not exist in the database. When true,
   *     expects email to exist, and to be attached to provided user
   * @throws BadRequestResponse Thrown when either username or email are malformed
   * @throws NotFoundResponse Thrown when user does not exist, or when email is not attached, when
   *     it is expected to
   * @throws ConflictResponse Thrown when an email is not supposed to be attached to anyone, but is
   */
  private void validateUserEmail(
      String username, String email, Database database, boolean expectToExist)
      throws BadRequestResponse, NotFoundResponse, ConflictResponse {
    // Before doing anything else, validate the user
    this.validateUser(username, database);

    if (!Emails.validateEmailAddress(email)) {
      throw new BadRequestResponse();
    }

    boolean userHasEmail = database.getEmails().userHasEmail(username, email);
    boolean emailIsAttached = database.getEmails().getOne(email) != null;
    if (expectToExist && !userHasEmail) {
      throw new NotFoundResponse();
    } else if (!expectToExist && emailIsAttached) {
      throw new ConflictResponse();
    }
  }

  /**
   * Gets all emails
   *
   * @param ctx Request context
   */
  public void getEmails(@NotNull Context ctx) {
    final Database database = ctx.appData(new Key<>("database"));
    ctx.json(database.getEmails().getAll());
    ctx.status(HttpStatus.OK);
  }

  /**
   * Attaches an email, passed in request body, to a user, passed in URL parameters
   *
   * @param ctx Request context
   */
  public void addUserEmail(@NotNull Context ctx) {
    String username = ctx.pathParamAsClass("username", String.class).get();
    final Database database = ctx.appData(new Key<>("database"));

    EmailAndFingerprint reqData =
        ctx.bodyValidator(EmailAndFingerprint.class)
            .check(obj -> obj.email() != null, "Missing new email")
            .check(
                obj -> obj.fingerprint() != null && obj.fingerprint().length() == 40,
                "Fingerprint must be 40 characters long")
            .get();

    GPGKey improvGpgObj = new GPGKey(reqData.fingerprint, null);

    // We expect the email to be valid, and not be associated to any user yet
    this.validateUserEmail(username, reqData.email(), database, false);

    // Fingerprint not yet added
    if (database.getGPGKeys().getOne(improvGpgObj.fingerprint()) == null) {
      throw new NotFoundResponse();
    }

    // Fingerprint already linked to an email
    if (null != database.getEmails().getByFingerprint(improvGpgObj)) {
      throw new ConflictResponse();
    }

    Email emailObj = new Email(reqData.email(), username, null);
    if (-1 == database.getEmails().attach(emailObj)) {
      throw new InternalServerErrorResponse();
    }

    if (-1 == database.getEmails().linkFingerprint(emailObj, improvGpgObj)) {
      // Detach the email we just attached to the user
      database.getEmails().detach(emailObj.email());
      throw new InternalServerErrorResponse();
    }

    ctx.status(HttpStatus.CREATED);
  }

  /**
   * Retrieves all email addresses associated with the username provided in the URL parameters
   *
   * @param ctx Request context
   */
  public void getUserEmails(@NotNull Context ctx) {
    String username = ctx.pathParamAsClass("username", String.class).get();
    final Database database = ctx.appData(new Key<>("database"));
    this.validateUser(username, database);

    Email[] userEmails = database.getEmails().getByUser(username);
    ctx.json(userEmails);
  }

  /**
   * Detaches the email provided in the request body from the username provided in the URL
   * parameters
   *
   * @param ctx Request context
   */
  public void deleteUserEmail(@NotNull Context ctx) {
    String username = ctx.pathParamAsClass("username", String.class).get();
    final Database database = ctx.appData(new Key<>("database"));

    Email reqEmail =
        ctx.bodyValidator(Email.class).check(obj -> obj.email() != null, "Missing new email").get();

    // We expect the email to be valid, and to be associated to one user yet
    this.validateUserEmail(username, reqEmail.email(), database, true);

    if (-1 == database.getEmails().detach(reqEmail.email())) {
      throw new InternalServerErrorResponse();
    }

    ctx.status(HttpStatus.NO_CONTENT);
  }
}

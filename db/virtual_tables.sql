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

-- Script that creates the virtual tables of the SQL database.

SET SEARCH_PATH TO gpg_keyserver;

CREATE VIEW gpg_key_per_user(username, fingerprint) AS
(
SELECT users.username, gpg_keys_emails.fingerprint
FROM users
     JOIN emails
     ON users.username = emails.username
     JOIN gpg_keys_emails
     ON emails.email = gpg_keys_emails.email
GROUP BY users.username, gpg_keys_emails.fingerprint);

CREATE VIEW gpg_key_per_email(email, fingerprint) AS
(
SELECT emails.email, gpg_keys_emails.fingerprint
FROM emails
     JOIN gpg_keys_emails
     ON emails.email = gpg_keys_emails.email
ORDER BY emails.email);

CREATE VIEW gpg_key_count_per_user(username, count) AS
(
SELECT gpg_key_per_user.username, COUNT(*) AS gpg_key_count
FROM gpg_key_per_user
GROUP BY gpg_key_per_user.username);

CREATE VIEW gpg_key_count_per_email(email, count) AS
(
SELECT gpg_key_per_email.email, COUNT(*) AS gpg_key_count
FROM gpg_key_per_email
GROUP BY gpg_key_per_email.email);

CREATE VIEW emails_count_per_user(username, email) AS
(
SELECT emails.username,
       COUNT(*) AS email_count
FROM emails
GROUP BY emails.username);

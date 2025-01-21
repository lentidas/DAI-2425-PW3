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

-- Script that creates the basic scheme and tables of the SQL database.

CREATE SCHEMA IF NOT EXISTS gpg_keyserver;

SET search_path TO gpg_keyserver;

CREATE TABLE IF NOT EXISTS users
(
   username   VARCHAR(32),
   first_name VARCHAR(32),
   last_name  VARCHAR(32),
   PRIMARY KEY (username)
);

COMMENT ON COLUMN gpg_keyserver.users.username IS 'Username of the user';
COMMENT ON COLUMN gpg_keyserver.users.first_name IS 'First name of the user';
COMMENT ON COLUMN gpg_keyserver.users.last_name IS 'Last name of the user';

CREATE TABLE IF NOT EXISTS emails
(
   email    VARCHAR(64),
   username VARCHAR(32),
   PRIMARY KEY (email),
   FOREIGN KEY (username) REFERENCES users (username)
);

COMMENT ON COLUMN gpg_keyserver.emails.email IS 'E-mail address of the user';
COMMENT ON COLUMN gpg_keyserver.emails.username IS 'Username of the user associated with the e-mail';

CREATE TABLE IF NOT EXISTS gpg_keys
(
   fingerprint CHAR(40),
   key         TEXT NOT NULL,
   PRIMARY KEY (fingerprint)
);

COMMENT ON COLUMN gpg_keyserver.gpg_keys.fingerprint IS 'Fingerprint of the GPG key';
COMMENT ON COLUMN gpg_keyserver.gpg_keys.key IS 'GPG public key in ASCII armor format';

CREATE TABLE IF NOT EXISTS gpg_keys_emails
(
   fingerprint CHAR(40)    NOT NULL,
   email       VARCHAR(64) NOT NULL,
   PRIMARY KEY (fingerprint, email),
   FOREIGN KEY (fingerprint) REFERENCES gpg_keys (fingerprint),
   FOREIGN KEY (email) REFERENCES emails (email)
);

COMMENT ON COLUMN gpg_keyserver.gpg_keys_emails.fingerprint IS 'Fingerprint of the GPG key associated with the e-mail';
COMMENT ON COLUMN gpg_keyserver.gpg_keys_emails.email IS 'E-mail address associated with the GPG key';

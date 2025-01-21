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

-- Script that creates the triggers of the SQL database.

CREATE OR REPLACE FUNCTION check_key_does_not_belong_to_multiple_users()
   RETURNS TRIGGER
   LANGUAGE plpgsql
AS
$$
BEGIN
   IF EXISTS (SELECT fingerprint
                FROM gpg_keyserver.gpg_keys_emails
               WHERE fingerprint = new.fingerprint) THEN
      IF EXISTS (SELECT gpg_keyserver.emails.username
                   FROM gpg_keyserver.gpg_keys_emails
                        JOIN gpg_keyserver.emails
                        ON gpg_keyserver.emails.email = gpg_keyserver.gpg_keys_emails.email
                  WHERE gpg_keys_emails.fingerprint = new.fingerprint
                    AND gpg_keyserver.emails.username NOT IN (SELECT username
                                                                FROM gpg_keyserver.emails
                                                               WHERE email = new.email)) THEN
         RAISE EXCEPTION 'The GPG key with fingerprint % already belongs to another user', new.fingerprint;
      END IF;
   END IF;

   RETURN new;
END
$$;

CREATE TRIGGER check_key_does_not_belong_to_multiple_users_insert
   BEFORE INSERT
   ON gpg_keyserver.gpg_keys_emails
   FOR EACH ROW
EXECUTE FUNCTION check_key_does_not_belong_to_multiple_users();

CREATE TRIGGER check_key_does_not_belong_to_multiple_users_update
   BEFORE UPDATE
   ON gpg_keyserver.gpg_keys_emails
   FOR EACH ROW
EXECUTE FUNCTION check_key_does_not_belong_to_multiple_users();

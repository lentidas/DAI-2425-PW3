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

-- Script to insert starting data into the database.

SET search_path TO gpg_keyserver;

INSERT INTO users (username, first_name, last_name)
VALUES ('PedroAS7', 'Pedro', 'Alves da Silva');
INSERT INTO emails (email, username)
VALUES ('pedrocas-pt@hotmail.com', 'PedroAS7');
INSERT INTO gpg_keys (fingerprint, key)
VALUES ('F9CB3BE035C4B03F47AEC4F8D006718F8EBFDC86', '-----BEGIN PGP PUBLIC KEY BLOCK-----

         mG8EZyoR9RMFK4EEACIDAwRV4qPyF9GKMmL8MKxhmG/IWuIcNhrFpaEkypioMpd8
         4YiRdA4AaHCtKk9kz3r/SWsaiJcn0VJ+oMq/85Q/9fRLiFCTYxUWj223/xmWs5Jx
         YfdQ6jJgfvahKSJDghCG3MO0MVBlZHJvQVM3IChMYXB0b3AgY291cnMpIDxwZWRy
         b2Nhcy1wdEBob3RtYWlsLmNvbT6IswQTEwkAOxYhBPnLO+A1xLA/R67E+NAGcY+O
         v9yGBQJnKhH1AhsDBQsJCAcCAiICBhUKCQgLAgQWAgMBAh4HAheAAAoJENAGcY+O
         v9yGNQkBgMJmTMEth/4uVqD5zaTzDNW0i3yC2INtn+dJLIfLInxXPIQJ3r54OnBk
         4ZLTy8pFtwGA/USLmQZeJigufmREbuu2QEozzncqRIN+2HM3OT0tiMxH5V3miIbf
         I50MARchyGvbtCJQZWRyb0FTNyA8cGVkcm9jYXMtcHRAaG90bWFpbC5jb20+iLME
         ExMJADsWIQT5yzvgNcSwP0euxPjQBnGPjr/chgUCZyoVwwIbAwULCQgHAgIiAgYV
         CgkICwIEFgIDAQIeBwIXgAAKCRDQBnGPjr/cho/BAX0fjqTFJScTw4QsLuSOawa+
         5fwJhSeBaf/JpXa6rYo0RwiEMkWX9ZYTt3lKYkBwK3wBgNcL2hgud4xLjRhF531v
         BR7+BNcyuILtyZ6UqRaNqZPVjUMbAjFN1RgpO95plon5L7hzBGcqEfUSBSuBBAAi
         AwMEM1VISUWXVMV8ZEd7gafuUGywgfajt6oDkcnLGwofHUZiZ3jVVq9rNpSXIbeL
         nc+ZR0oqRE0mQpTI0Xng9xFnkurCGxmZ0F6nf+rNuvi9DZLfo0AlXXVnN7Tn1mXo
         G2QiAwEJCYiYBBgTCQAgFiEE+cs74DXEsD9HrsT40AZxj46/3IYFAmcqEfUCGwwA
         CgkQ0AZxj46/3IZ4ywGA5jHH2F/PyIBxoZe4p9b6vmF5Sm//F0rMv0AitUKgfuO6
         mzrhmEAFd7c+1K2Xwr2oAYCObcnjbMJfJhL6VNyXoECuWzchetxYjYk4RbcRLkKV
         otxjIgQVSctEcDRomsFL5Q8=
         =BY7F
         -----END PGP PUBLIC KEY BLOCK-----
        ');
INSERT INTO gpg_keys_emails (fingerprint, email)
VALUES ('F9CB3BE035C4B03F47AEC4F8D006718F8EBFDC86', 'pedrocas-pt@hotmail.com');

INSERT INTO users (username, first_name, last_name)
VALUES ('lentidas', 'Gonçalo', 'Heleno');
INSERT INTO emails (email, username)
VALUES ('goncalocheleno@atomp.eu', 'lentidas');
INSERT INTO emails (email, username)
VALUES ('goncalo.heleno@gmail.com', 'lentidas');
INSERT INTO gpg_keys (fingerprint, key)
VALUES ('49AADE0B91D3AC1593B951803BE1E5F4EC19100F', '-----BEGIN PGP PUBLIC KEY BLOCK-----

        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
        =LdKL
        -----END PGP PUBLIC KEY BLOCK-----
        ');

INSERT INTO gpg_keys_emails(fingerprint, email)
VALUES ('49AADE0B91D3AC1593B951803BE1E5F4EC19100F', 'goncalocheleno@atomp.eu');
INSERT INTO gpg_keys_emails(fingerprint, email)
VALUES ('49AADE0B91D3AC1593B951803BE1E5F4EC19100F', 'goncalo.heleno@gmail.com');




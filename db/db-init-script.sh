#!/bin/bash

#
# gpg-keyserver - a web app to store user's GPG keys
# Copyright (C) 2024 Pedro Alves da Silva, Gonçalo Carvalheiro Heleno
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

# This script is used to create the database and insert the starting data into it.
# IMPORTANT: Note that the paths to the scripts are relative to the root of the container of the
# database, so this script will not run properly outside of the container.

export PGPASSWORD=$POSTGRESQL_PASSWORD

# Create the database
psql --dbname "$POSTGRESQL_DATABASE" --username "$POSTGRESQL_USERNAME" -f /db-init-scripts/db_scheme.sql
psql --dbname "$POSTGRESQL_DATABASE" --username "$POSTGRESQL_USERNAME" -f /db-init-scripts/triggers.sql
psql --dbname "$POSTGRESQL_DATABASE" --username "$POSTGRESQL_USERNAME" -f /db-init-scripts/functions.sql
psql --dbname "$POSTGRESQL_DATABASE" --username "$POSTGRESQL_USERNAME" -f /db-init-scripts/virtual_tables.sql

# Insert the data
psql --dbname "$POSTGRESQL_DATABASE" --username "$POSTGRESQL_USERNAME" -f /db-init-scripts/starting_data.sql
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

package ch.heigvd.dai.cache;

import java.time.Instant;

/** A cache entry to be used inside a cache's map */
class CacheEntry {

  static final int CACHE_EXPIRY_MINUTES = 5;
  private final Object _storedObject;
  private final long expiryTime;

  /**
   * Creates a new cache entry
   *
   * @param storedObject Object to be stored
   * @param expiryMinutes How many minutes it takes until the entry expires
   */
  CacheEntry(Object storedObject, int expiryMinutes) {
    _storedObject = storedObject;
    expiryTime = Instant.now().getEpochSecond() + (expiryMinutes * 60L);
  }

  /**
   * Returns whether this entry has expired or not
   *
   * @return True if expired, false if not
   */
  boolean hasExpired() {
    return expiryTime < Instant.now().getEpochSecond();
  }

  /**
   * Returns the stored object
   *
   * @return The stored object
   */
  Object storedObject() {
    return _storedObject;
  }
}

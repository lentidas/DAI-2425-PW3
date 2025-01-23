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

import java.util.ArrayList;
import java.util.HashMap;
import javax.management.openmbean.KeyAlreadyExistsException;

/** A cache-managing class */
public class Cache {

  private final HashMap<String, CacheEntry> entryMap;

  /** Public constructor for the cache instance */
  public Cache() {
    entryMap = new HashMap<>();
  }

  /** Removes all expired cache entries */
  private void purge() {
    ArrayList<String> toDelete = new ArrayList<>();
    for (HashMap.Entry<String, CacheEntry> pair : entryMap.entrySet()) {
      if (pair.getValue().hasExpired()) {
        toDelete.add(pair.getKey());
      }
    }

    for (String key : toDelete) {
      entryMap.remove(key);
    }
  }

  /**
   * Checks whether the cache has an entry with the specified key
   *
   * @param key Key to search for
   * @return True if entry exists, false if not
   */
  public boolean hasEntry(String key) {
    return entryMap.containsKey(key);
  }

  /**
   * Gets an entry from the cache
   *
   * @param key Key to search for
   * @return Found object
   * @throws IndexOutOfBoundsException Raised when cache does not have the provided key
   */
  public Object getEntry(String key) throws IndexOutOfBoundsException {
    purge();

    if (!hasEntry(key)) {
      throw new IndexOutOfBoundsException("Unknown key '" + key + "'");
    }

    CacheEntry entry = entryMap.get(key);
    return entry.storedObject();
  }

  /**
   * Pushes a new value to the cache
   *
   * @param key Key to identify value by
   * @param value Object to be stored
   * @throws KeyAlreadyExistsException Raised if key already exists
   */
  public void push(String key, Object value) throws KeyAlreadyExistsException {
    if (hasEntry(key)) {
      throw new KeyAlreadyExistsException("Key '" + key + "' already exists");
    }

    entryMap.put(key, new CacheEntry(value, CacheEntry.CACHE_EXPIRY_MINUTES));
  }

  /**
   * Removes a specific key from the cache map
   *
   * @param key Key to remove
   * @throws IndexOutOfBoundsException Raised if key does not exist
   */
  public void pop(String key) throws IndexOutOfBoundsException {
    if (!hasEntry(key)) {
      throw new IndexOutOfBoundsException("Unknown key '" + key + "'");
    }

    entryMap.remove(key);
  }

  /**
   * Replaces an already-existing key's value with another value
   *
   * @param key Key to search for
   * @param newValue New value
   * @throws IndexOutOfBoundsException Raised if key does not exist
   */
  public void replace(String key, Object newValue) throws IndexOutOfBoundsException {
    this.pop(key);
    this.push(key, newValue);
  }

  /** Empties the cache */
  public void clear() {
    entryMap.clear();
  }
}

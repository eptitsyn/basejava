package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractFilesystemStorageTest {

  public JsonPathStorageTest() {
    super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
  }
}

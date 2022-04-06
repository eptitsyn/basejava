package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractFilesystemStorageTest {

  public PathStorageTest() {
    super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
  }
}

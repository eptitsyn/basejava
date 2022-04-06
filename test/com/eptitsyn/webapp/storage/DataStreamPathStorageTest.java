package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractFilesystemStorageTest {

  public DataStreamPathStorageTest() {
    super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
  }
}

package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractFilesystemStorageTest {

  public XmlPathStorageTest() {
    super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
  }
}

package com.eptitsyn.webapp.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public abstract class AbstractFilesystemStorageTest extends AbstractStorageTest {

  public AbstractFilesystemStorageTest(Storage storage) {
    super(storage);
  }

  @Override
  @Test
  void update() {
    storage.update(expectedResumes.get(3));
    assertEquals(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
  }
}

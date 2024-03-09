package com.hit.service;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;

public interface IServiceTest {

    void checkEntityAdditionSuccess();
    void checkEntityAdditionFailure();
    void checkEntityRetrievalSuccess();
    void checkEntityRetrievalFailure();
    void checkEntityDeletionSuccess();
    void checkEntityDeletionFailure();
    void checkIfServiceInitialized();
    void checkIfTestFileDeleted();
}

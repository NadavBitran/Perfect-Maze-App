package com.hit.service;

import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.exceptions.ServiceRequestFailed;
import org.junit.AfterClass;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;

public interface IServiceTest {

    void checkEntityAdditionSuccess() throws ServiceRequestFailed;
    void checkEntityRetrievalSuccess() throws ServiceRequestFailed;
    void checkEntityRetrievalFailure() throws ServiceRequestFailed;
    void checkEntityDeletionSuccess() throws ServiceRequestFailed;
    void setup() throws ServiceRequestFailed;
    void teardown() throws IOException;
}

package com.hit.service;

import com.hit.util.ServiceRequestFailedException;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;

public interface IServiceTest {

    void checkEntityAdditionSuccess() throws ServiceRequestFailedException;
    void checkEntityRetrievalSuccess() throws ServiceRequestFailedException;
    void checkEntityRetrievalFailure() throws ServiceRequestFailedException;
    void checkEntityDeletionSuccess() throws ServiceRequestFailedException;
    void setup() throws ServiceRequestFailedException;
    void teardown();
}

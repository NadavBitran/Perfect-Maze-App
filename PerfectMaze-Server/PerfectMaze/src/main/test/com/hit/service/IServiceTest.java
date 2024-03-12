package com.hit.service;

import com.hit.exceptions.ServiceRequestFailedException;

import java.io.IOException;

public interface IServiceTest {

    void checkEntityAdditionSuccess() throws ServiceRequestFailedException;
    void checkEntityRetrievalSuccess() throws ServiceRequestFailedException;
    void checkEntityRetrievalFailure() throws ServiceRequestFailedException;
    void checkEntityDeletionSuccess() throws ServiceRequestFailedException;
    void setup() throws ServiceRequestFailedException;
    void teardown() throws IOException;
}

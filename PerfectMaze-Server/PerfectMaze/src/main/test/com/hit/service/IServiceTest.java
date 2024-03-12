package com.hit.service;

import com.hit.exceptions.ServiceRequestFailed;

import java.io.IOException;

public interface IServiceTest {

    void checkEntityAdditionSuccess() throws ServiceRequestFailed;
    void checkEntityRetrievalSuccess() throws ServiceRequestFailed;
    void checkEntityRetrievalFailure() throws ServiceRequestFailed;
    void checkEntityDeletionSuccess() throws ServiceRequestFailed;
    void setup() throws ServiceRequestFailed;
    void teardown() throws IOException;
}

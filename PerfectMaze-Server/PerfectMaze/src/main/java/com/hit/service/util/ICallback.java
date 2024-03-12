package com.hit.service.util;

import com.hit.exceptions.ServiceRequestFailedException;

public interface ICallback {

    void execute() throws ServiceRequestFailedException;
}

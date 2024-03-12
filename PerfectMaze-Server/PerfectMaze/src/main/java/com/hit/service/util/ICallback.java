package com.hit.service.util;

import com.hit.exceptions.ServiceRequestFailed;

public interface ICallback {

    void execute() throws ServiceRequestFailed;
}

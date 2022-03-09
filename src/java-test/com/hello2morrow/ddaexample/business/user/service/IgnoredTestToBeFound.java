package com.hello2morrow.ddaexample.business.user.service;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoredTestToBeFound
{

    @Test
    @Ignore("To be found in tutorial")
    public void ignored()
    {
        fail("Not yet implemented");
    }

}

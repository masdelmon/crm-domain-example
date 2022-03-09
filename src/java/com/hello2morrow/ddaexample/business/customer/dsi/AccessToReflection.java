package com.hello2morrow.ddaexample.business.customer.dsi;

import java.lang.reflect.Array;

public class AccessToReflection
{
    public Object accessReflection()
    {
        return Array.newInstance(AccessToReflection.class, 3);
    }
}

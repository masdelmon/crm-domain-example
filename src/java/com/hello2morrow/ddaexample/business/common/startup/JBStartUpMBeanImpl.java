package com.hello2morrow.ddaexample.business.common.startup;

import com.hello2morrow.dda.business.common.startup.SetupFactories;
import com.hello2morrow.ddaexample.business.distributionpartner.controller.DistributionPartnerController;

public class JBStartUpMBeanImpl implements JBStartUpMBean
{
    @Override
    public void start() throws Exception
    {
        SetupFactories.initialize();
        final DistributionPartnerController dummyReference = new DistributionPartnerController();
        System.out.println("Dummy reference to controller to produce architecture violation: " + dummyReference);
    }

    @Override
    public void stop() throws Exception
    {
        SetupFactories.cleanUp();
    }

    @Override
    public int getInt() throws Exception
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
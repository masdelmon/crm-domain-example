package com.hello2morrow.ddaexample.integration.contact.adapter;

import com.hello2morrow.ddaexample.integration.contact.esi.AddressCheckEsi;

public final class AddressCheckWithFailures implements AddressCheckEsi
{
    //Step9: DistributionPartnerController controller;

    @Override
    public boolean checkAddress(final String street, final String city, final String zipCode)
    {
        return false;
    }
}

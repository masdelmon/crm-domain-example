package com.hello2morrow.ddaexample.business.distributionpartner.controller;

import org.apache.log4j.Logger;

import com.hello2morrow.dda.business.common.dsi.DomainObjectWithDataSupplier;
import com.hello2morrow.dda.business.common.startup.SetupFactories;
import com.hello2morrow.dda.foundation.common.ObjectIdIf;
import com.hello2morrow.dda.foundation.common.exception.AssertionUtility;
import com.hello2morrow.dda.foundation.common.exception.BusinessException;
import com.hello2morrow.dda.foundation.common.exception.TechnicalException;
import com.hello2morrow.ddaexample.business.contact.controller.AddressDtoMapper;
import com.hello2morrow.ddaexample.business.contact.domain.Address;
import com.hello2morrow.ddaexample.business.contact.service.AddressDto;
import com.hello2morrow.ddaexample.business.customer.controller.CustomerDtoMapper;
import com.hello2morrow.ddaexample.business.customer.domain.Customer;
import com.hello2morrow.ddaexample.business.customer.service.CustomerDto;
import com.hello2morrow.ddaexample.business.distributionpartner.domain.RequestForInformation;
import com.hello2morrow.ddaexample.business.distributionpartner.domain.RequestForOffer;
import com.hello2morrow.ddaexample.business.distributionpartner.domain.RequestForTestDrive;
import com.hello2morrow.ddaexample.business.distributionpartner.domain.SalesAssistant;
import com.hello2morrow.ddaexample.business.distributionpartner.service.DistributionPartnerControllerServiceIf;
import com.hello2morrow.ddaexample.business.distributionpartner.service.RequestForInformationDto;
import com.hello2morrow.ddaexample.business.distributionpartner.service.RequestForInformationDtoVal;
import com.hello2morrow.ddaexample.business.distributionpartner.service.RequestForOfferDto;
import com.hello2morrow.ddaexample.business.distributionpartner.service.RequestForTestDriveDto;
import com.hello2morrow.ddaexample.business.distributionpartner.service.SalesAssistantDto;
import com.hello2morrow.ddaexample.business.user.controller.UserController;
import com.hello2morrow.ddaexample.business.user.domain.User;
import com.hello2morrow.ddaexample.business.user.service.ContextDto;

/**
 * @dda-generate-service
 */
public final class DistributionPartnerController implements DistributionPartnerControllerServiceIf
{
    private static Logger s_Logger = Logger.getLogger(DistributionPartnerController.class);
    public static String FIELD_TO_DEMONSTRATE_VIOLATION = "only for test";

    /**
     * @dda-service ASSIGN_CUSTOMERS_TO_SALES_ASSISTANT_CMD = "DistributionPartner::AssignCustomersToSalesAssistantCmd"
     */
    @Override
    public void assignCustomersToSalesAssistant(final ContextDto contextDto, final ObjectIdIf[] customerIds, final ObjectIdIf salesAssistantId)
            throws BusinessException, TechnicalException
    {
        //        new Canvas();

        SetupFactories.initialize();
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.ASSIGN_CUSTOMERS_TO_SALES_ASSISTANT_CMD);
        final User user = User.findUserByName("only a dummy method call to demonstrate architecture check");
        System.out.println("Found user: " + user.getName());

        final RequestForInformationDtoVal dummyRequest = new RequestForInformationDtoVal();
        System.out.println("Dummy request dto val: " + dummyRequest);

        assert AssertionUtility.checkArray(customerIds);
        assert salesAssistantId != null;

        final SalesAssistant salesAssistant = (SalesAssistant) DomainObjectWithDataSupplier.findByObjectId(salesAssistantId);
        assert salesAssistant != null;

        for (int i = 0; i < customerIds.length; i++)
        {
            final Customer customerToAssign = (Customer) DomainObjectWithDataSupplier.findByObjectId(customerIds[i]);
            assert customerToAssign != null;
            salesAssistant.addToCustomers(customerToAssign);
        }

        //Add another dependency to test SystemDiff in step 9
        //SetupFactories.initialize();
    }

    /**
     * @dda-service RETRIEVE_ASSIGNED_CUSTOMERS_FOR_SALES_ASSISTANT_CMD = "DistributionPartner::RetrieveAssignedCustomersForSalesAssistantCmd"
     */
    @Override
    public CustomerDto[] retrieveAssignedCustomersForSalesAssistant(final ContextDto contextDto, final ObjectIdIf salesAssistantId)
            throws BusinessException, TechnicalException
    {
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.RETRIEVE_ASSIGNED_CUSTOMERS_FOR_SALES_ASSISTANT_CMD);
        assert salesAssistantId != null;

        final SalesAssistant salesAssistant = (SalesAssistant) DomainObjectWithDataSupplier.findByObjectId(salesAssistantId);
        final Customer[] assigned = salesAssistant.getCustomers();
        return CustomerDtoMapper.createDtosFromDomainObjects(assigned);
    }

    /**
     * @dda-service CREATE_SALES_ASSISTANT_CMD = "DistributionPartner::CreateSalesAssistantCmd"
     */
    @Override
    public ObjectIdIf createSalesAssistant(final ContextDto contextDto, final SalesAssistantDto salesAssistantDto, final AddressDto addressDto)
            throws BusinessException, TechnicalException
    {
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.CREATE_SALES_ASSISTANT_CMD);
        assert salesAssistantDto != null;
        assert addressDto != null;
        salesAssistantDto.validate(DistributionPartnerControllerServiceIf.class, DistributionPartnerControllerServiceIf.CREATE_SALES_ASSISTANT_CMD);
        addressDto.validate(DistributionPartnerControllerServiceIf.class, DistributionPartnerControllerServiceIf.CREATE_SALES_ASSISTANT_CMD);

        final Address address = new Address(DomainObjectWithDataSupplier.TRANSIENT);
        AddressDtoMapper.mapDtoToDomainObject(addressDto, address, false);
        if (address.isValid())
        {
            address.save();
        }
        else
        {
            address.delete();
            throw new BusinessException("address not valid");
        }

        final SalesAssistant[] found = SalesAssistant.findSalesAssistantByFirstNameAndLastName(salesAssistantDto.getFirstName(),
                salesAssistantDto.getLastName());
        if (found.length > 0)
        {
            for (int i = 0; i < found.length; i++)
            {
                final Address foundCustomerAddress = found[i].getAddress();
                assert foundCustomerAddress != null;
                if (foundCustomerAddress.isSameAddress(address))
                {
                    address.delete();
                    throw new BusinessException("sales assistant already exists - (firstname/lastname) = " + salesAssistantDto.getFirstName() + " "
                            + salesAssistantDto.getLastName());
                }
            }
        }

        final SalesAssistant salesAssistant = new SalesAssistant();
        SalesAssistantDtoMapper.mapDtoToDomainObject(salesAssistantDto, salesAssistant, false);
        salesAssistant.setAddress(address);

        return salesAssistant.getObjectId();
    }

    /**
     * @dda-service CREATE_REQUEST_FOR_INFORMATION_CMD = "DistributionPartner::CreateRequestForInformationCmd"
     */
    @Override
    public ObjectIdIf createRequestForInformation(final ContextDto contextDto, final RequestForInformationDto requestDto)
            throws BusinessException, TechnicalException
    {
        assert requestDto != null;
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_INFORMATION_CMD);
        requestDto.validate(DistributionPartnerControllerServiceIf.class, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_INFORMATION_CMD);
        final RequestForInformation request = new RequestForInformation();
        RequestForInformationDtoMapper.mapDtoToDomainObject(requestDto, request, false);
        return request.getObjectId();
    }

    /**
     * @dda-service CREATE_REQUEST_FOR_OFFER_CMD = "DistributionPartner::CreateRequestForOfferCmd"
     */
    @Override
    public ObjectIdIf createRequestForOffer(final ContextDto contextDto, final RequestForOfferDto requestDto)
            throws BusinessException, TechnicalException
    {
        assert requestDto != null;
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_OFFER_CMD);
        requestDto.validate(DistributionPartnerControllerServiceIf.class, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_OFFER_CMD);
        final RequestForOffer request = new RequestForOffer();
        RequestForOfferDtoMapper.mapDtoToDomainObject(requestDto, request, false);
        return request.getObjectId();
    }

    /**
     * @dda-service CREATE_REQUEST_FOR_TEST_DRIVE_CMD = "DistributionPartner::CreateRequestForTestDriveCmd"
     */
    @Override
    public ObjectIdIf createRequestForTestDrive(final ContextDto contextDto, final RequestForTestDriveDto requestDto)
            throws BusinessException, TechnicalException
    {
        assert requestDto != null;
        UserController.checkPermission(contextDto, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_TEST_DRIVE_CMD);
        requestDto.validate(DistributionPartnerControllerServiceIf.class, DistributionPartnerControllerServiceIf.CREATE_REQUEST_FOR_TEST_DRIVE_CMD);

        final RequestForTestDrive request = new RequestForTestDrive();
        RequestForTestDriveDtoMapper.mapDtoToDomainObject(requestDto, request, false);
        return request.getObjectId();
    }
}
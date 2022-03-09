package com.hello2morrow.ddaexample.business.user.domain;

import org.apache.log4j.Logger;

import com.hello2morrow.dda.business.common.dsi.DataSupplierReadMarker;
import com.hello2morrow.dda.business.common.dsi.DomainObjectWithDataSupplier;
import com.hello2morrow.dda.foundation.common.exception.AssertionUtility;
import com.hello2morrow.dda.foundation.common.exception.BusinessException;
import com.hello2morrow.ddaexample.business.user.dsi.RoleDsi;
import com.hello2morrow.ddaexample.business.user.dsi.UserDmi;
import com.hello2morrow.ddaexample.business.user.dsi.UserDsi;

/**
 * @dda-generate-cmp
 */
public class User extends DomainObjectWithDataSupplier
{
    private static Logger s_Logger = Logger.getLogger(User.class);

    /**
     * @dda-dmi-find
     */
    public static User[] findAllUsers()
    {
        final UserDmi dmi = (UserDmi) getDataManager(UserDsi.class);
        final UserDsi[] all = dmi.findAllUsers();
        return (User[]) getDomainObjects(all, User.class);
    }

    /**
     * @dda-dmi-find
     */
    @Deprecated
    public static User findUserByName(final String name)
    {
        assert name != null;
        assert name.length() > 0;
        final UserDmi dmi = (UserDmi) getDataManager(UserDsi.class);
        final UserDsi dsi = dmi.findUserByName(name);
        return (User) getDomainObject(dsi);
    }

    /**
     * required for creation direct from data source
     */
    public User(final UserDsi dsi, final DataSupplierReadMarker marker)
    {
        super(dsi, marker);
    }

    /**
     * creates a persistent user
     */
    public User()
    {
        super(getDataManager(UserDsi.class).createDataSupplier(UserDsi.class, true));
    }

    /**
     * creates a persistent user
     */
    public User(final String name, final String password)
    {
        super(getDataManager(UserDsi.class).createDataSupplier(UserDsi.class, true));
        setName(name);
        setPassword(password);
    }

    /**
     * @dda-dto
     * @dda-dsi
     */
    public String getName()
    {
        return ((UserDsi) getDataSupplier()).getName();
    }

    public void setName(final String name)
    {
        assert name != null;
        assert name.length() > 0;
        ((UserDsi) getDataSupplier()).setName(name);
    }

    /**
     * @dda-dto
     * @dda-dsi
     */
    public String getPassword()
    {
        return ((UserDsi) getDataSupplier()).getPassword();
    }

    public void setPassword(final String password)
    {
        assert password != null;
        assert password.length() > 0;
        ((UserDsi) getDataSupplier()).setPassword(password);
    }

    public void login(final String pwd)
    {
        assert pwd != null;
        if (pwd.equals(getPassword()))
        {
            s_Logger.debug("login for user '" + getName() + "'");
            new LoginEvent(getName());
        }
        else
        {
            throw new BusinessException("unable to login - wrong password for user '" + getName() + "'");
        }
    }

    /**
     * @dda-dto
     * @dda-dsi n-m no-duplicates
     */
    public Role[] getRoles()
    {
        final RoleDsi[] roles = ((UserDsi) getDataSupplier()).getRoles();
        return (Role[]) getDomainObjects(roles, Role.class);
    }

    public void setRoles(final Role[] roles)
    {
        assert AssertionUtility.checkArray(roles);
        ((UserDsi) getDataSupplier()).setRoles((RoleDsi[]) getDataSuppliers(roles, RoleDsi.class));
    }

    public void addRole(final Role role)
    {
        assert role != null;
        ((UserDsi) getDataSupplier()).addToRoles((RoleDsi) role.getDataSupplier());
    }

    public boolean hasPermissionToExecute(final ServerCommand serverCommand)
    {
        assert serverCommand != null;

        s_Logger.debug("check permission (username/server-command) : " + getName() + "/" + serverCommand.getName());

        final Role[] allRoles = getRoles();
        for (int i = 0; i < allRoles.length; i++)
        {
            s_Logger.debug("user has role : " + allRoles[i].getName());

            if (allRoles[i].hasServerCommand(serverCommand))
            {
                s_Logger.debug("permission granted");
                return true;
            }
        }

        s_Logger.debug("permission denied");
        return false;
    }
}
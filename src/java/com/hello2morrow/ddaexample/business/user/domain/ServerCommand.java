package com.hello2morrow.ddaexample.business.user.domain;

import com.hello2morrow.dda.business.common.dsi.DataSupplierReadMarker;
import com.hello2morrow.dda.business.common.dsi.DomainObjectWithDataSupplier;
import com.hello2morrow.ddaexample.business.user.dsi.ServerCommandDmi;
import com.hello2morrow.ddaexample.business.user.dsi.ServerCommandDsi;

/**
 * @dda-generate-cmp
 */
@Deprecated
public final class ServerCommand extends DomainObjectWithDataSupplier
{
    /**
     * @dda-dmi-find
     */
    public static ServerCommand[] findAllServerCommands()
    {
        final ServerCommandDmi dmi = (ServerCommandDmi) getDataManager(ServerCommandDsi.class);
        final ServerCommandDsi[] all = dmi.findAllServerCommands();
        return (ServerCommand[]) getDomainObjects(all, ServerCommand.class);
    }

    /**
     * @dda-dmi-find
     */
    public static ServerCommand findServerCommandByName(final String name)
    {
        assert name != null;
        assert name.length() > 0;
        final ServerCommandDmi dmi = (ServerCommandDmi) getDataManager(ServerCommandDsi.class);
        final ServerCommandDsi dsi = dmi.findServerCommandByName(name);
        return (ServerCommand) getDomainObject(dsi);
    }

    /**
     * required for creation direct from data source
     */
    public ServerCommand(final ServerCommandDsi dsi, final DataSupplierReadMarker marker)
    {
        super(dsi, marker);
    }

    /**
     * creates a persistent server command
     */
    public ServerCommand(final String name)
    {
        super(getDataManager(ServerCommandDsi.class).createDataSupplier(ServerCommandDsi.class, true));
        setName(name);
    }

    /**
     * @dda-dto
     * @dda-dsi
     */
    public String getName()
    {
        return ((ServerCommandDsi) getDataSupplier()).getName();
    }

    private void setName(final String name)
    {
        assert name != null;
        assert name.length() > 0;
        ((ServerCommandDsi) getDataSupplier()).setName(name);
    }
}
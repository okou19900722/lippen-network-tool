package org.okou.lippen.network.tool.ui.select;

import java.net.InetSocketAddress;

public class INetSocketAddressOption extends AbstractOption {
    private InetSocketAddress address;

    public INetSocketAddressOption(InetSocketAddress address) {
        super();
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address.getHostString() + ":" + address.getPort();
    }

}

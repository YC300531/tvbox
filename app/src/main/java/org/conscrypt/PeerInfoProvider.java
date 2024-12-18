package org.conscrypt;
abstract class PeerInfoProvider {
    private static final PeerInfoProvider NULL_PEER_INFO_PROVIDER = new PeerInfoProvider() {
        @Override
        public String getHostname() {
            return null;
        }

        @Override
        public String getHostnameOrIP() {
            return null;
        }

        @Override
        public int getPort() {
            return -1;
        }
    };

    public static PeerInfoProvider forHostAndPort(final String str, final int i) {
        return new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return str;
            }

            @Override
            public String getHostnameOrIP() {
                return str;
            }

            @Override
            public int getPort() {
                return i;
            }
        };
    }

    public static PeerInfoProvider nullProvider() {
        return NULL_PEER_INFO_PROVIDER;
    }

    public abstract String getHostname();

    public abstract String getHostnameOrIP();

    public abstract int getPort();
}

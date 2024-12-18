package com.forcetech.service;
public class P2PService extends PxPService {
    @Override
    public int getPort() {
        return PxpUtil.P2P;
    }
}

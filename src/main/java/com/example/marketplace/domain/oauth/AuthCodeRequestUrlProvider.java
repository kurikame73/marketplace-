package com.example.marketplace.domain.oauth;

public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();

    String provide();
}

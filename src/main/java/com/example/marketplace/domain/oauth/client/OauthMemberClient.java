package com.example.marketplace.domain.oauth.client;

import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.oauth.OauthServerType;

public interface OauthMemberClient {
    OauthServerType supportServer();

    Member fetchCustom(String code);
}

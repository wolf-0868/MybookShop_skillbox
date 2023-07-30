package com.example.bookshop.security;

import com.example.bookshop.data.entities.user.UserEntity;

public class PhoneNumberUserDetails extends BookshopUserDetails {

    private static final long serialVersionUID = 7889786396343661525L;

    public PhoneNumberUserDetails(UserEntity aUser) {
        super(aUser);
    }

    @Override
    public String getUsername() {
        return getUser().getPhone();
    }

}

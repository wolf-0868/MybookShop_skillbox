package com.example.bookshop.security;

import com.example.bookshop.data.entities.user.UserEntity;

public class PhoneNumberUserDetails extends BookshopUserDetails {

    public PhoneNumberUserDetails(UserEntity aUser) {
        super(aUser);
    }

    @Override
    public String getUsername() {
        return getUser().getPhone();
    }

}

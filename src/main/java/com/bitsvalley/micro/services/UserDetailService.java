package com.bitsvalley.micro.services;

import com.bitsvalley.micro.model.response.UserDetails;

public interface UserDetailService {

  UserDetails getUserDetails(String username);
}

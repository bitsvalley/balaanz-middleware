package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.model.requests.CreateUserPayload;

import java.util.List;

public interface UserRootService {
  void create(CreateUserPayload createUserPayload);
  List<User> getAgents(long orgId, String username);
}

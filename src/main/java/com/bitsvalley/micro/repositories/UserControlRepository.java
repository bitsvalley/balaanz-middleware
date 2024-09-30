package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.domain.UserControl;
import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.utils.AccountStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserControlRepository extends CrudRepository<UserControl, Long> {

}

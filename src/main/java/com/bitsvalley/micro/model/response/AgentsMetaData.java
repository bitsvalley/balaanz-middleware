package com.bitsvalley.micro.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgentsMetaData {
  List<UserVO> activeAgents;
  List<UserVO> inActiveAgents;
}

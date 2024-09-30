package com.bitsvalley.micro.webdomain;

import com.bitsvalley.micro.domain.LedgerAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateParam {

    private String startDate;
    private String endDate;

}
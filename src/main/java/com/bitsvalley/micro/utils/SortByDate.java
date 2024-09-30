package com.bitsvalley.micro.utils;
import com.bitsvalley.micro.webdomain.SavingBilanz;

import java.util.Comparator;

public class SortByDate implements Comparator<SavingBilanz> {
        @Override
        public int compare(SavingBilanz a, SavingBilanz b) {
            return a.getCreatedDate().compareTo(b.getCreatedDate());
        }
    }
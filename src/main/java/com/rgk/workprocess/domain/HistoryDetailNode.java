package com.rgk.workprocess.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDetailNode {
    String identity;
    String result;
    String message;
    Date date;
}

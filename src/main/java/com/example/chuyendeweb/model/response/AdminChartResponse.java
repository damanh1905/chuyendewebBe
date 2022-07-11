package com.example.chuyendeweb.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChartResponse {
    private double totalPriceOrder;
    private Date dateCreated;
}

package com.example.chuyendeweb.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChartCiResponse {
   List<String> title;
   List<Double>  data;
}

package com.stockmarket.studycase.models;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HisseSenediRequest {
    private List<HisseSenediOlusturModel> senetler;
    private Long tertipId;
}

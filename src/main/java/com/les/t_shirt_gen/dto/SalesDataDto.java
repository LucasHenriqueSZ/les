package com.les.t_shirt_gen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesDataDto {
    private String product;
    private List<SalesVolumeDataDto> data;
}

package com.les.t_shirt_gen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeItemDto {
    private Long itemId;
    private Integer quantity;
    private Boolean selected;
}
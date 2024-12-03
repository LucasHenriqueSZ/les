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
public class ExchangeRequestDto {
    private Long orderId;
    private List<ExchangeItemDto> exchangeItems;
    private String reason;
    private String description;
}

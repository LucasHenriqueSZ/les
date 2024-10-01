package com.les.vest_fut.dto;

import com.les.vest_fut.model.order.OrderSend;
import com.les.vest_fut.model.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderFormDto {
    private List<Payment> payments = new ArrayList<Payment>();
    private OrderSend orderSend;
}

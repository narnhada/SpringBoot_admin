package com.example.test.practice.repository;

import com.example.test.practice.PracticeApplicationTests;
import com.example.test.practice.model.entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class OrderDetailRepositoryTest extends PracticeApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create(){
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setStatus("WAITING");
        orderDetail.setArrivalDate(LocalDateTime.now().plusDays(2));
        orderDetail.setQuantity(1);
        orderDetail.setTotalPrice(BigDecimal.valueOf(900000));
        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setCreatedBy("AdminServer");
//        orderDetail.setItemId(1L);
//        orderDetail.setOrderGroupId(1L);

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        Assertions.assertNotNull(newOrderDetail);

/*        //어떤사람??
        orderDetail.setUserId(1L); // User id: 1

        //어떤 상품??
        orderDetail.setItemId(1L); // Item id: 1
*/

    }

}
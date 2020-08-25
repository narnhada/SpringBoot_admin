package com.example.test.practice.model.network.response;

import com.example.test.practice.model.entity.OrderGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderInfoApiResponse {

/*
    private User user;

    private List<OrderGroup> orderGroupList; // 1 : N 연관 관계 설정이 되어 있으므로
*/


    private UserApiResponse userApiResponse; //1 : 1(2(3))

//    private List<OrderGroupApiResponse> orderGroupApiResponseList; //2 2(3)

//    private List<ItemApiResponse> itemApiResponseList; // orderGroup내에 있어야 하므로 3


}

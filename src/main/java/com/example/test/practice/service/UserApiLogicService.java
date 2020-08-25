package com.example.test.practice.service;

import com.example.test.practice.ifs.CrudInterface;
import com.example.test.practice.model.entity.OrderGroup;
import com.example.test.practice.model.entity.User;
import com.example.test.practice.model.network.Header;
import com.example.test.practice.model.network.Pagination;
import com.example.test.practice.model.network.request.UserApiRequest;
import com.example.test.practice.model.network.response.ItemApiResponse;
import com.example.test.practice.model.network.response.OrderGroupApiResponse;
import com.example.test.practice.model.network.response.UserApiResponse;
import com.example.test.practice.model.network.response.UserOrderInfoApiResponse;
import com.example.test.practice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class UserApiLogicService implements CrudInterface <UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 11
    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;



    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request Data
        UserApiRequest userApiRequest = request.getData();

        // 2. user 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        // 3. 생성된 데이터  >> userApiResponse return
//        return response(newUser);
        return Header.OK(response(newUser)) ;

    }


    @Override
    public Header<UserApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<User> optional = userRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))                   // user가 있다면
                .map(userApiResponse -> Header.OK(userApiResponse)) // search 추가 후
                .orElseGet(() ->Header.ERROR("데이터 없음"));    // user가 없다면

    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1. data 가져오고
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user 데이터를 찾고
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

        return optional.map(user -> { // user가 있다면
            // 3_1. update_set만 된 상태
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt()); // 여기까지는 DB X

            return user;

        })
            .map(user2 -> userRepository.save(user2))       // 3_2. update_db -> 새로운 user객체(updateUser) 반환
            .map(updateUser -> response(updateUser))        // 4. userApiResponse // response 보여주는용
            .map(updateResponse -> Header.OK(updateResponse))/// search 추가 후
            .orElseGet(() -> Header.ERROR("데이터 없음"));    // map() >> return 값을 받고, 다른값을 리턴 해줌
    }                                                       // user 이 user2로 >> new user객체 (updateUser) 넘겨줌
                                                            // >> reponse 로 다시 내림



    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);

        // 2. repository -> delete
        return optional.map(user -> {
            userRepository.delete(user);

            // 3. response return
            return Header.OK();
        })
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }


//    private Header<UserApiResponse> response(User user){
    private UserApiResponse response(User user){
        // user -> userApiReponse

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

//        return Header.OK(userApiResponse);
        return userApiResponse;

    };

    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElement(users.getTotalElements())
                .currentPage(users.getNumber())
                .currnetElements(users.getNumberOfElements())
                .build();


        //List<UserApiResponse> 이므로
        //Header<List<UserApiResponse>> 로 return 해줘야됨
        return Header.OK(userApiResponseList, pagination);

    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

        // user
        User user = userRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        // orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse =
                            orderGroupApiLogicService.response(orderGroup).getData();

                    // item api response
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                    .map(detail -> detail.getItem())
                    .map(item -> itemApiLogicService.response(item).getData())
                    .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }
}

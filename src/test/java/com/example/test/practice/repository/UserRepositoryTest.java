package com.example.test.practice.repository;

import com.example.test.practice.PracticeApplicationTests;
import com.example.test.practice.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


public class UserRepositoryTest extends PracticeApplicationTests {

    @Autowired // Repository를 이용하기 위해 >> 싱글톤(변경사항 없음)이므로 new 로 객체를 만들지 않음
    private UserRepository userRepository;

    @Test
    public void create() {
        String account = "Test03";
        String email = "Test03@gmail.com";
        String password = "Test01";
        String status = "REGISTERED";
        String phoneNumber = "010-0000-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
/*
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);
*/
        // Builder패턴 >> 생성자 순서, 갯수 상관없이 수정 가능
        User u = User.builder()
                .account(account)
                .password(password)
                .email(email)
                .build();

      User newUser = userRepository.save(user);
        System.out.println("newUser: " + newUser);

        Assertions.assertNotNull(newUser);

    }

    @Test
    @Transactional
    public void read() {

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-0000-1111");

        if (user != null) {
            user.getOrderGroupList().forEach(orderGroup -> {
                System.out.println("-----------------주문묶음-----------------");
                    System.out.println("수령인 : " + orderGroup.getRevName());
                    System.out.println("수령지 : " + orderGroup.getRevAddress());
                    System.out.println("총금액 : " + orderGroup.getTotalPrice());
                    System.out.println("총수량 : " + orderGroup.getTotalQuantity());

                System.out.println("-----------------주문상세-----------------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 : " + orderDetail.getStatus());
                    System.out.println("도착예정일자 : " + orderDetail.getArrivalDate());
                });


            });

            Assertions.assertNotNull(user);

//        Optional<User> user = userRepository.findById(2L);
//        user.ifPresent(selectUser -> {
//            System.out.println("User: " + selectUser);
//        });
        }

    }

    @Test
    public void update() {
        Optional<User> updateUser = userRepository.findById(2L);

        updateUser.ifPresent(selectUser -> {
           userRepository.save(selectUser);

        });

    }

    @Test
    @Transactional // spl문은 실행하지만 실제 db에 값들은 건드리지 않음
    public void delete() {
        Optional<User> deleteUser = userRepository.findById(1L);

        deleteUser.ifPresent(selectUser -> {

            userRepository.delete(selectUser);

        });

    }
}
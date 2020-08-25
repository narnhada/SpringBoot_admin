package com.example.test.practice.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orderDetailList", "partner"})
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  status; // 등록 / 해지 / 검수중(등록대기중)

    private String name;

    private String title;

    private String content;

    private BigDecimal price;

    private String brandName;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

//    private Long partnerId;


    // Item N : 1 partner;
    @ManyToOne
    private Partner partner;

    // Item 1 : N OrderDetail
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderDetail> orderDetailList;



/*
    // 1 : N
    // LAZY = 지연로딩 >> 따로 get 메소드를 호출하지 않는이상 연관 테이블에 대해 select 안함 ,
    // LAZY = SELECT * FROM item where id = ?

    // 1 : 1
    //EAGER = 즉시로딩 >> 연관된것 join해서 다 보여줌
    // EAGER =
    // item_id = order_detail.item_id, (join 있음음
    // user_id = order_detail.user_id,
    // where item.id = ?
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "item") //OrderDetail에 있는 'item'
    private List<OrderDetail> orderDetailList;

 */
}
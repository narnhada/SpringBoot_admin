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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orderGroup", "item"})
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime arrivalDate;

    private Integer quantity;

    private BigDecimal totalPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;



//    private Long orderGroupId;
    // OrderDetail N : 1 OrderGroup
    @ManyToOne
    private OrderGroup orderGroup;

//    private Long itemId;
    @ManyToOne
    private Item item;


/*
    // N : 1
    @ManyToOne

    private User user;       // Long >> User , userId >> user


    @ManyToOne

    private Item item;

 */
}
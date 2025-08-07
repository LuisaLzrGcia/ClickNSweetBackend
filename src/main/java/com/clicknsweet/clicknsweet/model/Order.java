package com.clicknsweet.clicknsweet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "orders")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ID", nullable = false, unique = true)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "User_ID", nullable = false)
//    private User user;

    @Column(name = "User_ID", nullable = false)
    private Integer userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Shipping_Address_ID", nullable = false)
//    private Address shippingAddress;

    @Column(name = "Shipping_Address_ID", nullable = false)
    private Integer shippingAddressId;

    @Column(name = "Status", length = 50, nullable = false)
    private String status;

    @Column(name = "Total_Amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Tracking_Number", length = 100)
    private String trackingNumber;

    @Column(name = "Shipping_Carrier", length = 100)
    private String shippingCarrier;

    @Column(name = "Created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderLine> orderLines;

}

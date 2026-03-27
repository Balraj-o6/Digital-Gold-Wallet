package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "vendor_branches")
public class VendorBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable=false, precision=18, scale=2)
    private BigDecimal quantity;

    @Column(name="created_At", insertable = false, updatable=false)
    private LocalDateTime createdAt;

}
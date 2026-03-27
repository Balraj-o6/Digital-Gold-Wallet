package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "virtual_gold_holdings")
public class VirtualGoldHoldings {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer holdingId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private VendorBranch vendorBranch;

    @Column(nullable=false, precision = 18, scale=2)
    private BigDecimal quantity;
    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
	
	
	
}

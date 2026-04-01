package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vendors")
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	private Integer vendorId;
	@Column(name = "vendor_name", length = 100)
	private String vendorName;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(name = "contact_person_name", length = 100)
	private String contactPersonName;
	@Column(name = "contact_email", length = 100)
	private String contactEmail;
	@Column(name = "contact_phone", length = 20)
	private String contactPhone;
	@Column(name = "website_url", length = 255)
	private String websiteUrl;
	@Column(name = "total_gold_quantity", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalGoldQuantity = BigDecimal.ZERO;
	@Column(name = "current_gold_price", nullable = false, precision = 18, scale = 2)
	private BigDecimal currentGoldPrice = new BigDecimal("13460.00");
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;
}

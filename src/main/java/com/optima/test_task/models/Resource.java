package com.optima.test_task.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resources")
@SQLDelete(sql = "UPDATE resources SET is_deleted = true, is_active = false WHERE id = ? AND version = ?")
@SQLRestriction("is_deleted = false")
public class Resource extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Column(name = "amount")
    private BigDecimal amount;
    @Version
    @Column(name = "version")
    private Long version;

}

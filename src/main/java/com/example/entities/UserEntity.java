package com.example.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "User", schema = "qbits-lic-dashboard")
@NamedQuery(name = "UserEntity.findAll", query = "SELECT a from UserEntity a")
@Where(clause = "soft_delete = 0")
@Data
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String uuid;
    private String name;
    private String description;
    private Boolean softDelete;
    //private String modifiedBy;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    // Generate Getters and Setters (if you are using lombok is not necesary).
}

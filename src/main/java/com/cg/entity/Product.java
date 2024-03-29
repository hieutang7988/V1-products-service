package com.cg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    @JsonIgnore
    private Category category;
}

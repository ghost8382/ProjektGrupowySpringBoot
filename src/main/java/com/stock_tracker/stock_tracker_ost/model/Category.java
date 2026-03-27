package com.stock_tracker.stock_tracker_ost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Category> children;

    public Long getId() { return id; }
    public String getName() { return name; }
    public Category getParent() { return parent; }
    public List<Category> getChildren() { return children; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setParent(Category parent) { this.parent = parent; }
    public void setChildren(List<Category> children) { this.children = children; }
}
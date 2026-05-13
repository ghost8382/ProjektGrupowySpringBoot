package com.stock_tracker.stock_tracker_ost.DataTransferObject;

public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId;
    private String parentName;

    public CategoryDTO(Long id, String name, Long parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public String getParentName() { return parentName; }
}

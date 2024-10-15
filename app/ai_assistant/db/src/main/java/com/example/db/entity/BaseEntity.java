package com.example.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    private String isDeleted;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;
}

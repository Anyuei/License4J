package com.license4j.license.entity;

import lombok.Data;

@Data
public class Pagination {
    private Integer currentPage=1;
    private Integer pageSize=10;

}

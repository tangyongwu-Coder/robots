package com.sirius.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询
 *
 * @author lijing
 * @date 2017/10/23 0023
 */
@Getter
@Setter
@ToString
@ApiModel("分页结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDTO<T> implements Serializable{
    private static final long serialVersionUID = 5035384056065218030L;


    private static final int MAX_PAGE_SIZE = 1000;
    /** 页码 */
    @ApiModelProperty("页码(必填)")
    @JsonProperty("page")
    private Integer page;
    /** 每页记录数 */
    @ApiModelProperty("每页记录数(必填)")
    @JsonProperty("page_size")
    private Integer pageSize;
    /** 总记录数 */
    @ApiModelProperty("总记录数")
    @JsonProperty("count")
    private Long count;
    /** 总记录数 */
    @ApiModelProperty("总页数")
    @JsonProperty("pages")
    private Integer pages;
    /** 总记录数 */
    @ApiModelProperty("总记录数")
    @JsonProperty("size")
    private Integer size;
    /** 分页查询结果集 */
    @ApiModelProperty("查询结果集")
    private List<T> result;
    /**
     * 处理页码和每页记录数
     */
    public void checkPage(){
        if(page<1){
            page=1;
        }
        if(pageSize<1||pageSize>MAX_PAGE_SIZE){
            pageSize=20;
        }
    }

    /**
     * 分页对象转换
     * @param list 分页结果list
     */
    public PageDTO(List<T> list){
        if (list instanceof Page){
            Page<T> page = (Page<T>) list;
            this.page = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.count = page.getTotal();
            this.pages = page.getPages();
            this.result = page.getResult();
            this.size = page.size();
        }
    }

    public PageDTO(){
    }
}

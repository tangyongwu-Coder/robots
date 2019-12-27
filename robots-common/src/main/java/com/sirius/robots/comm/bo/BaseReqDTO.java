package com.sirius.robots.comm.bo;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author liang_shi
 * @date on 2019/5/31 14:22
 * @description 基本请求对象类
 */
@Data
public class BaseReqDTO implements Serializable {

    private static final long serialVersionUID = 3217844656173196417L;

    /** 开始时间 */
    private String startDate;

    /** 结束时间 */
    private String endDate;

    /**日期类型：day || month || week*/
    @Pattern(regexp = "day|month|week",message = "日期类型格式错误")
    private String dateType;

    /** 页码 */
    private Integer currentPage;

    /** 每页记录数 */
    private Integer pageSize;

}

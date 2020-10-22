package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum MsgTypeEnum implements BaseMsgTypeEnum{

    /**
     * 用户信息
     */
    USER_AUTH("USER_AUTH", "个人信息","userAuthMsgService",1,false, UserAuthMsgTypeEnum.values(),"我的"),
    /**
     * 线报
     */
    WOOL("WOOL", "线报","woolMsgService",2,false,WoolMsgTypeEnum.values(),"线报,活动,羊毛"),
    /**
     * 开支
     */
    SPEND("SPEND", "开支","spendMsgService",3,false,SpendMsgTypeEnum.values(),"开支,算账"),

    /**
     * 优惠券
     */
    TICKET("TICKET", "优惠券","ticketMsgService",3,false,TicketMsgTypeEnum.values(),"优惠券,券码"),

    /**
     * 退出
     */
    OUT("OUT", "退出","",666,false,null,"退出,out,OUT"),

    /**
     * 帮助
     */
    HELP("HELP", "帮助","helpMsgService",999,false,null,"帮助,help,HELP"),

    /**
     * 无
     */
    NULL("NULL", "无","helpMsgService",9999,false,null,""),
    ;




    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
    /**
     * 处理类
     */
    private String service;

    /**
     * 优先级
     */
    private Integer order;


    /**
     * 类型
     */
    private Boolean manager;

    private BaseMsgTypeEnum[] msgTypeEnums;

    /**
     * 关键字
     */
    private String keyWords;

    public static MsgTypeEnum explain(String code) {

        for (MsgTypeEnum statusEnum : MsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

}
package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.msg.*;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.BigDecimalUtil;
import com.sirius.robots.dal.model.SpendInfo;
import com.sirius.robots.manager.SpendInfoManager;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import com.sirius.robots.manager.util.SpendUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Slf4j
@Service
public class SpendMsgService extends BaseMsgService<SpendInfo> {
    @Autowired
    private SpendInfoManager spendInfoManager;
    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected MsgTypeEnum getMsgType() {
        return MsgTypeEnum.SPEND;
    }

    /**
     * 消息类型
     *
     * @param userBO
     * @param wxMessage
     * @return 消息类型
     */
    @Override
    protected BaseMsgBO<SpendInfo> processData(WxUserBO userBO, WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();
        MsgTypeEnum msgType = getMsgType();
        BaseMsgTypeEnum base = SpendUtil.getMsgType(content, msgType);
        if(Objects.isNull(base)){
            String unknown = HelpUtil.getUnknown(msgType, userBO);
            throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
        }
        SpendInfo spend;
        if(SpendMsgTypeEnum.DELETE.getCode().equals(base.getCode())){
            String newMsg = MsgUtil.removeContainsKeyWord(content, base);
            Integer id = MsgUtil.isNumber(newMsg);
            if(Objects.isNull(id)){
                String unknown = HelpUtil.getUnknown(msgType, userBO);
                throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
            }
            spend = new SpendInfo();
            spend.setRemark(content);
            spend.setId(id);
        }else{
            spend = SpendUtil.getSpend(content, base,userBO);
        }
        BaseMsgBO<SpendInfo> baseMsgBO = new BaseMsgBO<>();
        baseMsgBO.setBaseMsgTypeEnum(base);
        baseMsgBO.setT(spend);
        return baseMsgBO;
    }

    /**
     * 验证
     *
     * @param userBO    用户信息
     * @param wxMessage 请求信息
     * @param baseMsgBO     基础消息对象
     */
    @Override
    protected Boolean validate(WxUserBO userBO, WxMpXmlMessage wxMessage, BaseMsgBO<SpendInfo> baseMsgBO) {
        Boolean isFamily = userBO.getIsFamily();
        if(!isFamily){
            throw new RobotsServiceException(MsgErrorEnum.USER_NOT_FAMILY);
        }
        BaseMsgTypeEnum msgType = baseMsgBO.getBaseMsgTypeEnum();
        if(userBO.getIsManager()){
            return Boolean.TRUE;
        }
        Boolean isAuth = userBO.getIsAuth();
        if(!isAuth){
            throw new RobotsServiceException(MsgErrorEnum.USER_NOT_AUTH);
        }
        return msgType.getManager();
    }

    /**
     * 处理
     *
     * @param userBO    用户信息
     * @param wxMessage 请求信息
     * @param baseMsgBO     基础消息对象
     */
    @Override
    protected String process(WxUserBO userBO, WxMpXmlMessage wxMessage, BaseMsgBO<SpendInfo> baseMsgBO) {
        SpendMsgTypeEnum msgType = (SpendMsgTypeEnum) baseMsgBO.getBaseMsgTypeEnum();
        SpendInfo spendInfo = baseMsgBO.getT();
        List<SpendInfo> all = null;
        String querySpendType = msgType.getCode();
        switch (msgType){
            case INCOME:
                all = spendInfoManager.addSpend(spendInfo,userBO);
                break;
            case PAY:
                all = spendInfoManager.addSpend(spendInfo,userBO);
                break;
            case DELETE:
                SpendInfo deleteOne = spendInfoManager.delete(spendInfo,userBO);
                querySpendType = deleteOne.getSpendType();
                all = spendInfoManager.queryByType(null,deleteOne.getCountDate(),userBO);
                break;
            case QUERY:
                all = spendInfoManager.queryByType(null,spendInfo.getCountDate(),userBO);
                break;
            case COUNT:
                all = spendInfoManager.queryByType(null,spendInfo.getCountDate(),userBO);
                break;
            case DATE:
                all = spendInfoManager.queryDate(userBO);
                break;
        }
        return getRes(all,querySpendType);
    }


    private String getRes(List<SpendInfo> all,String msgType){

        if(SpendMsgTypeEnum.DATE.getCode().equals(msgType)){
            if(CollectionUtils.isEmpty(all)){
                throw new RobotsServiceException(MsgErrorEnum.RESULT_NULL);
            }
            Set<String> dates = all.stream()
                    .map(SpendInfo::getCountDate)
                    .collect(Collectors.toSet());
            StringBuilder sb = new StringBuilder();
            sb.append("计算日期==>\n");
            for (String date : dates) {
                sb.append(date).append("\n");
            }
            return sb.toString();
        }
        StringBuilder income = new StringBuilder();
        StringBuilder pay = new StringBuilder();
        Long incomeAmtAll = 0L;
        Long payAmtAll = 0L;
        if(!CollectionUtils.isEmpty(all)){
            for (SpendInfo spendInfo : all) {
                Long amt = spendInfo.getAmt();
                Double amtDou = BigDecimalUtil.getAmtDou(amt);
                String channel = spendInfo.getChannel();
                Integer id = spendInfo.getId();
                if(SpendMsgTypeEnum.INCOME.getCode().equals(spendInfo.getSpendType())){
                    incomeAmtAll += amt;
                    income.append("<").append(id).append(">").append(channel).append(amtDou).append("元。\n");
                }else{
                    payAmtAll +=amt;
                    pay.append("<").append(id).append(">").append(channel).append(amtDou).append("元。\n");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if(!SpendMsgTypeEnum.PAY.getCode().equals(msgType)){
            sb.append("收入==>\n").append(income);
        }
        if(!SpendMsgTypeEnum.INCOME.getCode().equals(msgType)){
            sb.append("支出==>\n").append(pay);
        }
        if(SpendMsgTypeEnum.QUERY.getCode().equals(msgType) || SpendMsgTypeEnum.COUNT.getCode().equals(msgType)){
            sb.append("总收入：").append(BigDecimalUtil.getAmtDou(incomeAmtAll)).append("元。\n");
            sb.append("总支出：").append(BigDecimalUtil.getAmtDou(payAmtAll)).append("元。\n");
        }
        if (SpendMsgTypeEnum.COUNT.getCode().equals(msgType)) {
            Long amtAll = incomeAmtAll-payAmtAll;
            if(amtAll>=0){
                sb.append("余额：");
            }else{
                sb.append("负债：");
            }
            sb.append(BigDecimalUtil.getAmtDou(Math.abs(amtAll))).append("元。\n");
        }
        return sb.toString();
    }

}
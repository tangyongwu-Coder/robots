package com.sirius.robots.comm.util;

import com.sirius.robots.comm.bo.BatchQueryBO;
import com.sirius.robots.comm.bo.BatchQueryResultBO;
import com.sirius.robots.comm.bo.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 批次查询工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/8/17
 */
@Slf4j
public class BatchUtil {

    /**
     * 批次大小
     */
    private final static Integer BATCH_NUM = 1000;

    /**
     * 批次查询(简单-单结果)
     *
     * @param function      分页查询方法
     * @param batchQueryBO  批次查询对象
     * @param <R>           返回对象
     * @return              返回
     */
    public static <R> List<R> batchQuery(BiFunction<PageDTO,BatchQueryBO,PageDTO<R>> function, BatchQueryBO batchQueryBO) {
        BiFunction<PageDTO,BatchQueryBO,BatchQueryResultBO<R,Object>> function1 = (pageDTO, batchQueryBO1) -> {
            PageDTO<R> apply = function.apply(pageDTO, batchQueryBO1);
            BatchQueryResultBO<R,Object> result = new BatchQueryResultBO<>();
            result.setPage(apply);
            return result;
        };
        List<BatchQueryResultBO<R, Object>> batchQueryResultBOs = batchQuery(function1, batchQueryBO, BATCH_NUM);
        List<R> allList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(batchQueryResultBOs)){
            for (BatchQueryResultBO<R, Object> batchQueryResultBO : batchQueryResultBOs) {
                PageDTO<R> page = batchQueryResultBO.getPage();
                List<R> result = page.getResult();
                allList.addAll(result);
            }
        }
        return allList;
    }

    /**
     * 批次查询(复杂-组合结果)
     *
     * @param function      分页查询方法
     * @param batchQueryBO  批次查询对象
     * @param <R>           返回对象
     * @return              返回
     */
    public static <R,U> List<BatchQueryResultBO<R,U>> batchResultQuery(BiFunction<PageDTO,BatchQueryBO,BatchQueryResultBO<R,U>> function, BatchQueryBO batchQueryBO) {
        return batchQuery(function,batchQueryBO,BATCH_NUM);
    }
    /**
     * 批次查询
     *
     * @param function      分页查询方法
     * @param batchQueryBO  批次查询对象
     * @param size          批次大小
     * @param <R>           返回对象
     * @return              返回
     */
    public static <R,U> List<BatchQueryResultBO<R,U>> batchQuery(BiFunction<PageDTO,BatchQueryBO,BatchQueryResultBO<R,U>> function,
                                                           BatchQueryBO batchQueryBO,
                                                           Integer size) {
        long start = System.currentTimeMillis();
        List<BatchQueryResultBO<R,U>> allList = new ArrayList<>();
        try {
            PageDTO pageDTO = new PageDTO<>();
            pageDTO.setPageSize(size);
            pageDTO.setCount(null);
            int page = 1;
            while (true) {
                pageDTO.setPage(page);
                BatchQueryResultBO<R,U> result = function.apply(pageDTO,batchQueryBO);
                if (Objects.isNull(result)) {
                    log.info("统计无结果");
                    break;
                }
                PageDTO<R> pageResult = result.getPage();
                if (Objects.isNull(pageResult)) {
                    log.info("统计无结果");
                    break;
                }
                List<R> list = pageResult.getResult();
                Long count = pageResult.getCount();
                if (page == 1) {
                    log.info("统计获得总记录数:{}", count);
                }
                if (!CollectionUtils.isEmpty(list)) {
                    log.info("第{}次统计获得记录数:{}", page, list.size());
                    allList.add(result);
                } else {
                    log.info("第{}次统计获得记录数:{}", 0);
                }
                if (Objects.isNull(count) || count == 0) {
                    break;
                }
                if (count < (pageResult.getPageSize() * page)) {
                    break;
                }
                page++;
            }
            log.info("批次查询处理完成,耗时:{}ms", System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error("批次查询处理失败,原因:", e);
        }
        return allList;
    }
}

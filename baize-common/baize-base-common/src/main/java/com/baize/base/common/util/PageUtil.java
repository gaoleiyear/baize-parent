package com.baize.base.common.util;


public class PageUtil {

    private static final int PAGESIZE_1 =1;

    private static final int PAGESIZE_5 =5;

    private static final int PAGESIZE_10 =10;

    private static final int PAGENO_1 =1;

    public static Integer getPageNo_1(Integer pageNo) {
        return pageNo == null || pageNo < PAGENO_1 ? pageNo = PAGENO_1 : pageNo;
    }

    /**
     * 处理分页开始页数据
     *
     * @param pageSize
     * @return
     */
    public static Integer getPageSize_1(Integer pageSize) {
        return pageSize == null || pageSize < PAGESIZE_1 ? pageSize = PAGESIZE_1 : pageSize;
    }

    /**
     * 处理每页显示数据5
     *
     * @param pageSize
     * @return
     */
    public static Integer getPageSize_5(Integer pageSize) {
        return pageSize == null || pageSize < PAGESIZE_1 ? pageSize = PAGESIZE_5 : pageSize;
    }

    /**
     * 处理每页显示数据10
     *
     * @param pageSize
     * @return
     */
    public static Integer getPageSize_10(Integer pageSize) {
        return pageSize == null || pageSize < PAGESIZE_1 ? pageSize = PAGESIZE_10 : pageSize;
    }

    /**
     * 根据总条数以及每页行数计算总页数
     *
     * @param totalCount long
     * @param pageSize   int
     * @return
     */
    public static int getTotalPage(long totalCount, int pageSize) {
        return (int) (totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1));
//        return totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize + 1);
    }

}

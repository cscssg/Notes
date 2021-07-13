package top.bluewort.Notes.base_utils.X004_list_page;

import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 自定义 List分页工具
 */
public class PageUtil {


    public static List page(List list,int pageNum,int pageSize){
        //判空
        if(ObjectUtils.isEmpty(list)){
            return null;
        }

        //记录总条
        int count = list.size();
        //记录总页数
        int pageCount = 0;
        if(count % pageSize == 0){
            pageCount = count/pageSize;
        }else {
            pageCount = count/pageSize + 1;
        }

        //计算索引位置
        int fromIndex = 0;
        int toIndex = 0;
        if(pageNum < pageCount){
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        }else if(pageNum == pageCount){
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        //截取数据
        List rtnlist = list.subList(fromIndex,toIndex);
        return rtnlist;
    }
}

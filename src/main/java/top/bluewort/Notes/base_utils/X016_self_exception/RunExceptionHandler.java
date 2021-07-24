package top.bluewort.Notes.base_utils.X016_self_exception;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * 
 * @author csc
 * @date 2021年3月27日 下午10:16:19
 */
@Component
public class RunExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 统一拦截系统异常日志
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		R r = new R();
		try {
			//1. 甄别日志异常类型
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			if(ex instanceof RunException){
				r.put("code",((RunException) ex).getCode());
				r.put("msg",((RunException) ex).getMsg());
			}else if(ex instanceof DuplicateKeyException){
				r = R.error("数据库中已经存在该记录");
			}else {
				r = R.error("服务器内部异常");
			}
			//2. 异常保存
			String es = ex.getMessage();
			logger.error(es,ex);
			logger.error("我被拦截了");
			//3. 定义返回数据
			response.getWriter().print(JSON.toJSONString(r));
		}catch (Exception e){
			e.printStackTrace();
			logger.error("RRExceptionHandler handler fails...");
		}
		return new ModelAndView();
	}
}

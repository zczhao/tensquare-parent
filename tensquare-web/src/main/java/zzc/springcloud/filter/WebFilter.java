package zzc.springcloud.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class WebFilter extends ZuulFilter {

	/**
	 * 过滤器内执行的操作 return 任何object的值都表示继续执行
	 * setSendZullResponse(false)表示不再继续执行
	 */
	@Override
	public Object run() throws ZuulException {
		System.out.println("经过后台过滤器 run()");
		// 得到request上下文
		RequestContext requestContext = RequestContext.getCurrentContext();
		// 得到request域
		HttpServletRequest request = requestContext.getRequest();
		// 得到头信息
		String header = request.getHeader("Authorization");
		// 判断是否有头信息
		if (!StringUtils.isEmpty(header)) {
			// 把头信息继续向下传(*必须小写，不能用Authorization单词，否则接收到微服务获取不到值)
			requestContext.addZuulRequestHeader("authorization-gateway", header);
		}
		return null;
	}

	/**
	 * 当前过滤器是否开启true表示开启
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 多个过滤器执行的顺序，数字越小，表示越先执行
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 在请求前pre或请求后post执行
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}

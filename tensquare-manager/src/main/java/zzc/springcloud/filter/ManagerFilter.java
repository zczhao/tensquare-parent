package zzc.springcloud.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.jsonwebtoken.Claims;
import zzc.springcloud.util.JwtUtil;

@Component
public class ManagerFilter extends ZuulFilter {

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 过滤器内执行的操作 return 任何object的值都表示继续执行
	 * setSendZullResponse(false)表示不再继续执行
	 */
	@Override
	public Object run() throws ZuulException {
		System.out.println("经过后台过滤器 run()");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
			return null;
		}
		
		// 如果是登录就放行
		if(StringUtils.contains(request.getRequestURI(), "login")) {
			return null;
		}
		
		String header = request.getHeader("Authorization");
		if(!StringUtils.isEmpty(header)) {
			if(StringUtils.startsWith(header, "Bearer ")) {
				// 获取token
				String token = header.substring(7);
				try {
					Claims claims = jwtUtil.parseJWT(token);
					String roles = (String) claims.get("roles");
					if (!StringUtils.isEmpty(roles)) {
						if (StringUtils.equalsAny(roles, "admin", "user")) {
							// 把头信息转发下去，并且放行
							requestContext.addZuulRequestHeader("authorization-gateway", header);
							return null;
						}
					}
				} catch (Exception e) {
					
				}
			}
		}
		requestContext.setSendZuulResponse(false); // 终止运行
		requestContext.setResponseStatusCode(403); // http状态码
		requestContext.setResponseBody("无权访问");
		requestContext.getResponse().setContentType("text/html;charset=UTF-8");
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

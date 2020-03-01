package zzc.springcloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import zzc.springcloud.util.JwtUtil;

@Component
public class JWTInterceptor implements HandlerInterceptor{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("JWTInterceptor.preHandle()");
		// 无论如何都放行，具体能不能操作还是在具体的操作中去判断
		// 拦截器只是负责把请求头中包含token的令牌解析
		String header = request.getHeader("Authorization");
		if (StringUtils.isEmpty(header)) {
			// 获取通过网关转发过来的请求头信息(*必须小写，不能用Authorization单词)
			header = request.getHeader("authorization-gateway");
		}
		// 如果有包含有Authorization头信息，就对其进行解析		
		if(!StringUtils.isEmpty(header)) {
			// 前后端约定：前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token
			if(header.startsWith("Bearer ")) {
				// 获取token
				String token = header.substring(7);
				try {
					Claims claims = jwtUtil.parseJWT(token);
					String roles = (String) claims.get("roles");
					if (!StringUtils.isEmpty(roles)) {
						if (StringUtils.equals("admin", roles)) {
							request.setAttribute("claims_admin", claims);
						}
						if (StringUtils.equals("user", roles)) {
							request.setAttribute("claims_user", claims);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("令牌不正确！");
				}
			}
		}
		return true;
	}

	
}

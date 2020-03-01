package zzc.springcloud;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JJWTTest {

	/**
	 * 创建JWT
	 */
	@Test
	public void testCreateJWT() {
		System.out.println(2 * 60 * 60 * 1000);
		long expMillis = System.currentTimeMillis() + 60000; // 设置一分钟过期
		Date expDate = new Date(expMillis);
		JwtBuilder jwtBuilder = Jwts.builder()
			.setId("666")
			.setSubject("志成")
			.setIssuedAt(new Date()) // 设置签发时间
			.signWith(SignatureAlgorithm.HS256, "zczhao") // 设置签名密钥
			.setExpiration(expDate) // 设置过期时间
			.claim("role", "admin"); // 设置自定义属性
		System.out.println(jwtBuilder.compact());
	}
	
	/**
	 * 解析JWT
	 */
	@Test
	public void testPraseJWT() {
		Claims claims = Jwts.parser()
			.setSigningKey("zczhao")
			.parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlv5fmiJAiLCJpYXQiOjE1NzIzNjg1MDUsImV4cCI6MTU3MjM3NTcwNSwicm9sZSI6ImFkbWluIn0.EPlOuaYK7wskt2oLacQuQTW_AupHXHFC-qd3gS85GGM")
			.getBody();
		System.out.println("用户id：" + claims.getId());
		System.out.println("用户名：" + claims.getSubject());
		System.out.println("登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
		System.out.println("过期时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
		System.out.println("自定义属性：" + claims.get("role"));
		
	}
}

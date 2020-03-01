package zzc.springcloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import zzc.springcloud.client.imp.BaseClientImpl;
import zzc.springcloud.entity.Result;

@FeignClient(value = "tensquare-base", fallback = BaseClientImpl.class)
public interface BaseClient {

	@GetMapping("/label/{labelId}")
	public Result findById(@PathVariable("labelId") String labelId);
}

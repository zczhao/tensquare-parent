package zzc.springcloud.client.imp;

import org.springframework.stereotype.Component;

import zzc.springcloud.client.BaseClient;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;

@Component
public class BaseClientImpl implements BaseClient{

	@Override
	public Result findById(String labelId) {
		return new Result(false, StatusCode.ERROR, "熔断器触发了！");
	}

}

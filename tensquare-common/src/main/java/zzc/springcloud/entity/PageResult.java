package zzc.springcloud.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页结果类
 */
@Getter
@Setter
public class PageResult<T> {
	private long total;
	private List<T> rows;

	public PageResult() {
	}

	public PageResult(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

}

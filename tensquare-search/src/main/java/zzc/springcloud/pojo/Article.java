package zzc.springcloud.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;

@Data
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	// 是否索引，就是看该域是否能被搜索
	// 是否分词，就表示搜索的时候是整体匹配还是单词匹配
	// 是否存储，就是是否在页面上显示
	@Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String title; // 标题

	@Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String content; // 文章正文

	private String state; // 审核状态
}

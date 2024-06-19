package com.elastictest.elastictest;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "auctionpost")
@TypeAlias("auctionpost")
@Setting(settingPath = "elasticsearch/settings/settings.json")
public class PostDocument {

	@Id
	@Field(type = FieldType.Long)
	private Long id;

//	@MultiField(
//		mainField = @Field(type = FieldType.Text, copyTo = "keywords"),
//		otherFields = {
//			@InnerField(suffix = "nori", type = FieldType.Text, analyzer = "my_nori_analyzer", searchAnalyzer = "my_nori_analyzer"),
//			@InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "my_ngram_analyzer", searchAnalyzer = "my_ngram_analyzer")
//		}
//	)
	@Field(type = FieldType.Text, analyzer = "my_nori_analyzer", searchAnalyzer = "my_nori_analyzer")
//	@Field(type = FieldType.Text, analyzer = "my_ngram_analyzer", searchAnalyzer = "my_ngram_analyzer")
	private String title;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String content;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime updatedAt;

	public static PostDocument from(Post post) {
		return PostDocument.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}

	public Post toPost() {
		return Post.builder()
			.id(id)
			.title(title)
			.content(content)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}

}

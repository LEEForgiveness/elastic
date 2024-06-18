package com.elastictest.elastictest;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "auctionpost")
@TypeAlias("auctionpost")
//@Setting(settingPath = "elasticsearch/settings/settings.json")
//@Mapping(mappingPath = "elasticsearch/settings/mappings.json")
public class PostDocument {

	@Id
	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String title;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String content;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime updatedAt;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String keywords;

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

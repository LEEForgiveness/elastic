package com.elastictest.elastictest;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "post")
public class PostDocument {

	@Id
	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String title;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String content;

	private List<String> keywords;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime updatedAt;

	@CompletionField
	private Completion suggest;

	public static PostDocument from(Post post) {
		return PostDocument.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.keywords(post.getKeywords())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())

			.suggest(new Completion(new String[]{post.getTitle(), post.getContent()}))
			.build();
	}

	public Post toPost() {
		return Post.builder()
			.id(id)
			.title(title)
			.content(content)
			.keywords(keywords)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}

}

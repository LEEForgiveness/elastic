package com.elastictest.elastictest;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final PostDocumentRepository postDocumentRepository;

	ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public PostService(PostRepository postRepository,
		PostDocumentRepository postDocumentRepository) {
		this.postRepository = postRepository;
		this.postDocumentRepository = postDocumentRepository;
	}

	public void createPost(String title, String content) {
		Post post = Post.builder()
			.title(title)
			.content(content)
			.createdAt(LocalDateTime.now())
			.build();

		postRepository.save(post);
		postDocumentRepository.save(PostDocument.from(post));

	}

	public List<PostDocument> searchPost(String keyword) {
		Criteria criteria = new Criteria("title").is(keyword)
			.or(new Criteria("content").is(keyword));
		Query searchQuery = new CriteriaQuery(criteria);
		SearchHits<Post> searchHits = elasticsearchOperations.search(searchQuery, Post.class);
		return searchHits.getSearchHits().stream()
			.map(hit -> PostDocument.from(hit.getContent()))
			.toList();
	}
}

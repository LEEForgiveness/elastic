package com.elastictest.elastictest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final PostDocumentRepository postDocumentRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public PostService(PostRepository postRepository,
		PostDocumentRepository postDocumentRepository,
		ElasticsearchOperations elasticsearchOperations) {
		this.postRepository = postRepository;
		this.postDocumentRepository = postDocumentRepository;
		this.elasticsearchOperations = elasticsearchOperations;

	}

	public void createPost(String title, String content) {

		Post post = Post.builder()
			.title(title)
			.content(content)
			.createdAt(LocalDateTime.now())
			.keywords(List.of(title, content))
			.build();

		postRepository.save(post);
		postDocumentRepository.save(PostDocument.from(post));

	}

	public List<Post> searchPost(String keyword) {

		return postDocumentRepository.findByKeywords(keyword)
			.stream()
			.map(PostDocument::toPost)
			.collect(Collectors.toList());
	}

	public List<Post> searchPostJpa(String keyword) {
		return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
	}
}

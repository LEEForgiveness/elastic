package com.elastictest.elastictest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

	private final PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity createPost(
		@RequestParam(value = "title") String title,
		@RequestParam(value = "content") String content
	) {

		postService.createPost(title, content);

		return new ResponseEntity("", HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity searchPost(
		@RequestParam(value = "keyword") String keyword) {
		List<Post> posts = postService.searchPost(keyword);

		return new ResponseEntity(posts, HttpStatus.OK);
	}

	@GetMapping("/searchjpa")
	public ResponseEntity searchPostJpa(
		@RequestParam(value = "keyword") String keyword) {
		List<Post> posts = postService.searchPostJpa(keyword);

		return new ResponseEntity(posts, HttpStatus.OK);
	}
}
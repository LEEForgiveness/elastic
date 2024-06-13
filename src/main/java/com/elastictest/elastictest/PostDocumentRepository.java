package com.elastictest.elastictest;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {

	List<PostDocument> findByTitleOrContent(String title, String content);

}

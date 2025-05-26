package egovframework.ragchat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RagConfig {

	@Value("${rag.index.name}")
	private String indexName;

	@Value("${rag.redis.host}")
	private String redisHost;

	@Value("${rag.redis.port}")
	private Integer redisPort;

	// Redis 비밀번호는 사용하지 않음 (Docker에서 비밀번호 없이 설정함)

	@Value("${rag.embedding.size}")
	private Integer embeddingSize;

	/**
	 * 임베딩 모델 빈 생성
	 */
	@Bean
	public EmbeddingModel embeddingModel() {
		log.info("BgeSmallEnV15QuantizedEmbeddingModel 초기화");
		return new BgeSmallEnV15QuantizedEmbeddingModel();
	}

	/**
	 * 임베딩 저장소 빈 생성
	 */
	@Bean
	public EmbeddingStore<TextSegment> embeddingStore() {
		log.info("Redis 임베딩 저장소 초기화 - 인덱스: {}, 차원: {}", indexName, embeddingSize);

		// Redis 연결 설정 - 인덱스 이름 명시적 설정
		return RedisEmbeddingStore.builder()
				.host(redisHost)
				.port(redisPort)
				.dimension(embeddingSize)
				.indexName(indexName)
				.build();
	}

	/**
	 * 컨텐츠 검색기 빈 생성
	 */
	@Bean
	public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore,
			EmbeddingModel embeddingModel) {
		log.info("컨텐츠 검색기 초기화");
		return EmbeddingStoreContentRetriever.builder().embeddingStore(embeddingStore).embeddingModel(embeddingModel)
				.maxResults(3).minScore(0.6).build();
	}

	/**
	 * Redis 인덱스 관리
	 * 
	 * Redis는 인덱스가 없으면 자동으로 생성하므로 별도의 인덱스 생성 과정이 필요 없음
	 */
	public void checkRedisIndex() {
		log.info("Redis 임베딩 저장소 설정 완료 - 자동 인덱스 생성 지원");
		log.info("인덱스 이름: {}, 차원: {}", indexName, embeddingSize);
	}

}

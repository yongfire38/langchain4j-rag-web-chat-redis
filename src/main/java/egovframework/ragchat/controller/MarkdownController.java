package egovframework.ragchat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.ragchat.dto.MarkdownRequest;
import egovframework.ragchat.util.MarkdownConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 마크다운 변환 API를 제공하는 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/markdown")
@RequiredArgsConstructor
public class MarkdownController {
    
    private final MarkdownConverter markdownConverter;
    
    /**
     * 마크다운 텍스트를 HTML로 변환하는 엔드포인트
     * 
     * @param request 마크다운 텍스트가 포함된 요청 객체
     * @return HTML로 변환된 텍스트
     */
    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> convertMarkdown(@RequestBody MarkdownRequest request) {
        String markdown = request.getMarkdown();
        log.debug("마크다운 변환 요청 수신: {} 글자", markdown != null ? markdown.length() : 0);
        
        if (markdown == null || markdown.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "마크다운 텍스트가 비어있습니다");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        String html = markdownConverter.convertToHtml(markdown);
        
        Map<String, String> response = new HashMap<>();
        response.put("html", html);
        
        return ResponseEntity.ok(response);
    }
}

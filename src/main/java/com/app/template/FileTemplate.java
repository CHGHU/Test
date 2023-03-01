package com.app.template;

import com.app.util.JsonUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Data
@Service
public class FileTemplate {

    @Autowired
    private RestTemplate restTemplate;

    public String uploadFile(String filePath, String data) {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", new FileSystemResource(Paths.get(filePath)));
        multipartBodyBuilder.part("data", data);

        MultiValueMap<String, HttpEntity<?>> requestEntity = multipartBodyBuilder.build();
        String result1 = restTemplate.postForObject("/file/upload", requestEntity, String.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/file/upload", requestEntity, String.class);
        String result2 = responseEntity.getBody();

        return result1;
    }

    public File downloadFile(String filePath, String data) {
        RequestCallback requestCallback = request -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            request.getBody().write(JsonUtil.object2Json(data).getBytes(StandardCharsets.UTF_8));
        };

        ResponseExtractor<Void> responseExtractor = response -> {
            Files.copy(response.getBody(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return null;
        };

        restTemplate.execute("/file/download", HttpMethod.POST, requestCallback, responseExtractor);
        return new File(filePath);
    }

}
package com.elasticsearch.jobportal.service;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface JobService {
    void insertData(MultipartFile multipartFile) throws IOException;

    JsonNode getEntities(String fieldName, int from, int size);

}

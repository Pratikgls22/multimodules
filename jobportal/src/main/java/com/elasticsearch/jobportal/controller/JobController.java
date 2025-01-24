package com.elasticsearch.jobportal.controller;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.elasticRepository.JobElasticRepository;
import com.elasticsearch.jobportal.service.JobService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobElasticRepository jobElasticRepository;


    @PostMapping(value = "insertCsv")
    public String insertData(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        this.jobService.insertData(multipartFile);
        return "Data Insert Successfully";
    }

    @GetMapping(value = "/getFunctionalArea/{fieldName}")
    public JsonNode getEntities(@PathVariable String fieldName) {
        System.out.println("fieldName = " + fieldName);
        return jobService.getEntities(fieldName);
    }

    @GetMapping(value = "/findAllData")
    public Iterable<JobElasticEntity> findAll() {
        return jobElasticRepository.findAll();
    }
}

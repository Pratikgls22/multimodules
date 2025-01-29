package com.elasticsearch.jobportal.controller;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.elasticRepository.JobElasticRepository;
import com.elasticsearch.jobportal.service.JobService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobElasticRepository jobElasticRepository;
    private final JobLauncher jobLauncher;
    private final Job job;


    @PostMapping(value = "/insertExcel")
    public String insertData(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        this.jobService.insertData(multipartFile);
        return "Data Insert Successfully";
    }

    @GetMapping(value = "/getFunctionalArea/{fieldName}")
    public JsonNode getEntities(@PathVariable String fieldName,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "1000") int size) {
        System.out.println("fieldName = " + fieldName);
        return jobService.getEntities(fieldName, from, size);
    }

    @GetMapping(value = "/findAllData")
    public Iterable<JobElasticEntity> findAll() {
        return jobElasticRepository.findAll();
    }

    @PostMapping(value = "/insertCsv")
    public void importCsvWithSb(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
    }
}

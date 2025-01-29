package com.elasticsearch.jobportal.service.impl;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.elasticRepository.JobElasticRepository;
import com.elasticsearch.jobportal.entity.JobEntity;
import com.elasticsearch.jobportal.repository.JobEntityRepository;
import com.elasticsearch.jobportal.service.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobEntityRepository jobEntityRepository;
    private final JobElasticRepository jobElasticRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String ELASTICSEARCH_URL = "http://localhost:9200/job_elastic_master/_search";

    @Override
    public void insertData(MultipartFile multipartFile){
        try {
            Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("sheet = " + sheet);


            for (Row row : sheet) {
                List<JobEntity> jobEntities = new ArrayList<>();
                List<JobElasticEntity> jobElasticEntities = new ArrayList<>();

                if (row.getRowNum() != 0) {
                    String jobSalary = row.getCell(1).getStringCellValue();
                    String jobExperienceRequired = row.getCell(2).getStringCellValue();
                    String keySkills = row.getCell(3).getStringCellValue();
                    String roleCategory = row.getCell(4).getStringCellValue();
                    String functionalArea = row.getCell(5).getStringCellValue();
                    String industry = row.getCell(6).getStringCellValue();
                    String jobTitle = row.getCell(7).getStringCellValue();

                    JobEntity jobEntity = new JobEntity(jobSalary, jobExperienceRequired, keySkills, roleCategory, functionalArea, industry, jobTitle);
                    jobEntity.setJobSalary(jobSalary);
                    jobEntity.setJobExperienceRequired(jobExperienceRequired);
                    jobEntity.setKeySkills(keySkills);
                    jobEntity.setRoleCategory(roleCategory);
                    jobEntity.setFunctionalArea(functionalArea);
                    jobEntity.setIndustry(industry);
                    jobEntity.setJobTitle(jobTitle);
                    jobEntities.add(jobEntity);

                    JobElasticEntity jobElasticEntity = new JobElasticEntity();
                    jobElasticEntity.setJobSalary(jobSalary);
                    jobElasticEntity.setJobExperienceRequired(jobExperienceRequired);
                    jobElasticEntity.setKeySkills(keySkills);
                    jobElasticEntity.setRoleCategory(roleCategory);
                    jobElasticEntity.setFunctionalArea(functionalArea);
                    jobElasticEntity.setIndustry(industry);
                    jobElasticEntity.setJobTitle(jobTitle);
                    jobElasticEntities.add(jobElasticEntity);
                }
                // Save JobEntities and JobElasticEntities
                if (!jobEntities.isEmpty()) {
    //                jobRepository.saveAll(jobEntities); // Save the list of JobEntity objects to JPA
                }
                if (!jobElasticEntities.isEmpty()) {
                    jobElasticRepository.saveAll(jobElasticEntities); // Save the list of JobElasticEntity objects to Elasticsearch
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode getEntities(String name, int from, int size) {
        String url = UriComponentsBuilder.fromUriString(ELASTICSEARCH_URL)
                .queryParam("q", name)
                .queryParam("from", from)
                .queryParam("size", size)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        JsonNode jobData;
        try {
            jobData = objectMapper.readTree(response);

            System.out.println("jobData = " + jobData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return jobData;
    }


}


































// For all types files (.csv, .xls, .xlsx)

//public void processFile(MultipartFile multipartFile) throws IOException, InvalidFormatException {
//    // Detect the file type (you can use Tika or file extension for this)
//    Tika tika = new Tika();
//    String mimeType = tika.detect(multipartFile.getInputStream());
//
//    // If the file is an Excel file (.xlsx or .xls)
//    if (mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        // Process the sheet as needed
//    } else if (mimeType.equals("application/vnd.ms-excel")) {
//        HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        // Process the sheet as needed
//    }
//    // If the file is a CSV
//    else if (mimeType.equals("text/csv")) {
//        InputStream inputStream = multipartFile.getInputStream();
//        Reader reader = new InputStreamReader(inputStream);
//        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
//        for (CSVRecord record : csvParser) {
//            // Process the CSV record
//            // record.get(0) gives you the first column, record.get(1) gives you the second column, etc.
//        }
//        csvParser.close();
//    }
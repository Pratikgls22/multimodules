package com.elasticsearch.jobportal.config;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.elasticRepository.JobElasticRepository;
import com.elasticsearch.jobportal.entity.JobEntity;
import com.elasticsearch.jobportal.repository.JobEntityRepository;
import com.elasticsearch.jobportal.requestDto.CompositeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final JobEntityRepository jobEntityRepository;
    private final JobElasticRepository jobElasticRepository;

    @Bean
    public FlatFileItemReader<JobEntity> reader() {
        try {
            FlatFileItemReader<JobEntity> reader = new FlatFileItemReader<>();
            Resource resource = new ClassPathResource("jobs.csv");
            System.out.println("resource = " + resource.exists());

            reader.setResource(new ClassPathResource("jobs.csv"));
            reader.setLinesToSkip(1);
            reader.setLineMapper(new DefaultLineMapper<>() {{
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setDelimiter(",");
                    setStrict(false);
                    setNames("id", "jobSalary", "jobExperienceRequired", "keySkills", "roleCategory", "functionalArea", "industry", "jobTitle");
                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(JobEntity.class);
                }});
            }});
            return reader;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ItemProcessor<JobEntity, JobEntity> databaseProcessor() {
        return new ItemProcessor<JobEntity, JobEntity>() {
            @Override
            public JobEntity process(JobEntity jobEntity) throws Exception {
                jobEntity.setId(null);  // Clear the ID for the database write
                return jobEntity;
            }
        };
    }

    @Bean
    public ItemProcessor<JobEntity, JobElasticEntity> elasticsearchProcessor() {
        return new ItemProcessor<JobEntity, JobElasticEntity>() {
            @Override
            public JobElasticEntity process(JobEntity jobEntity) throws Exception {
                // Convert JobEntity to JobElasticEntity for Elasticsearch
                JobElasticEntity jobElasticEntity = new JobElasticEntity();
                jobElasticEntity.setId(jobEntity.getId());
                jobElasticEntity.setJobSalary(jobEntity.getJobSalary());
                jobElasticEntity.setJobExperienceRequired(jobEntity.getJobExperienceRequired());
                jobElasticEntity.setKeySkills(jobEntity.getKeySkills());
                jobElasticEntity.setRoleCategory(jobEntity.getRoleCategory());
                jobElasticEntity.setFunctionalArea(jobEntity.getFunctionalArea());
                jobElasticEntity.setIndustry(jobEntity.getIndustry());
                jobElasticEntity.setJobTitle(jobEntity.getJobTitle());

                return jobElasticEntity;  // Return the converted JobElasticEntity
            }
        };
    }

    // For JpaRepository :
    @Bean
    public RepositoryItemWriter<JobEntity> databaseWriter() {
        try {
            RepositoryItemWriter<JobEntity> writer = new RepositoryItemWriter<>();
            writer.setRepository(jobEntityRepository);
            writer.setMethodName("save");
            return writer;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error configuring Database writer",e);
        }
    }

    // For Elasticsearch :
    @Bean
    public RepositoryItemWriter<JobElasticEntity> elasticsearchWriter() {
        try {
            RepositoryItemWriter<JobElasticEntity> writer = new RepositoryItemWriter<>();
            writer.setRepository(jobElasticRepository);
            writer.setMethodName("save");
            return writer;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error configuring Elasticsearch writer", e);
        }
    }

//    @Bean
//    public CompositeItemWriter<CompositeEntity> compositeWriter() {
//        CompositeItemWriter<CompositeEntity> writer = new CompositeItemWriter<>();
//        writer.setDelegates(List.of(databaseWriter(),elasticsearchWriter()));
//        return writer;
//    }

    @Bean
    public Step step1() {
        try {
            return new StepBuilder("csv-step1", jobRepository)
                    .<JobEntity, JobEntity>chunk(10, platformTransactionManager)
                    .reader(reader())
                    .processor(databaseProcessor())
                    .writer(databaseWriter())
                    .transactionManager(platformTransactionManager)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Step step2() {
        try {
            return new StepBuilder("csv-step1", jobRepository)
                    .<JobEntity, JobElasticEntity>chunk(10, platformTransactionManager)
                    .reader(reader())
                    .processor(elasticsearchProcessor())
                    .writer(elasticsearchWriter())
                    .transactionManager(platformTransactionManager)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Job runJob() {
        try {
            return new JobBuilder("importUserJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(step1())
                    .next(step2())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

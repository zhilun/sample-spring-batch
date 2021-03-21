package com.example.demo.batchprocessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLaunchService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @PostMapping("/launch-job")
    public String lauchJob()
    {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong(
                    "time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }

    }
}

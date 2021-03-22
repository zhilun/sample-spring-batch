package com.example.demo.batchprocessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JobLaunchService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;
    
    @Autowired
    JobOperator jobOperator;

    @Autowired
    JobExplorer jobExplorer;
    
    @GetMapping("/get-job-status")
    public Map<String, String> getStatus(@RequestParam Long instanceId) throws NoSuchJobInstanceException {
        List<Long> executions = jobOperator.getExecutions(instanceId);
        Long executionId = executions.get(0);
        String exitCode = jobExplorer.getJobExecution(executionId).getExitStatus().getExitCode();
        String name = jobExplorer.getJobExecution(executionId).getStatus().name();

        HashMap<String, String> map = new HashMap<>();
        map.put("status", name);
        map.put("exitCode", exitCode);

        return map;
    }

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

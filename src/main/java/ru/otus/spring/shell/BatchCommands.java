package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job postgresToMongoDbMigrationJob;
    private final JobLauncher jobLauncher;

    //http://localhost:8080/h2-console/

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(postgresToMongoDbMigrationJob,
                new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }

//    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
//    public void startMigrationJobWithJobOperator() throws Exception {
//        Long executionId = jobOperator.start(IMPORT_USER_JOB_NAME,
//                INPUT_FILE_NAME + "=" + appProps.getInputFile() + "\n" +
//                        OUTPUT_FILE_NAME + "=" + appProps.getOutputFile()
//        );
//        System.out.println(jobOperator.getSummary(executionId));
//    }
//
//    @ShellMethod(value = "showInfo", key = "i")
//    public void showInfo() {
//        System.out.println(jobExplorer.getJobNames());
//        System.out.println(jobExplorer.getLastJobInstance(IMPORT_USER_JOB_NAME));
//    }
}

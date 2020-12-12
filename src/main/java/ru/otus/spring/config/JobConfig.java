package ru.otus.spring.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.entity.nosql.MongoBook;
import ru.otus.spring.entity.sql.Book;
import ru.otus.spring.service.ConvertEntityServiceImpl;


@SuppressWarnings("all")
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Job postgresToMongoDbmigrationJob(Step migrateRowsFromPostgresStep) {
        return jobBuilderFactory.get("postgresToMongoDbMigrationJob")
                .start(migrateRowsFromPostgresStep)
                .build();
    }

    @Bean
    public Step migrateRowsFromPostgresStep(HibernateCursorItemReader sqlBookReader, ItemProcessor convertBookToMongoBookProcessor, MongoItemWriter<MongoBook> noSqlBookWriter) {
        return stepBuilderFactory.get("migrateRowsFromPostgresStep")
                .chunk(CHUNK_SIZE)
                .reader(sqlBookReader)
                .processor(convertBookToMongoBookProcessor)
                .writer(noSqlBookWriter)
                .build();
    }

    @Bean
    public ItemProcessor convertBookToMongoBookProcessor(ConvertEntityServiceImpl convertEntityService) {
        return (ItemProcessor<Book, MongoBook>) convertEntityService::convertSqlBookEntityToMongoBook;
    }

    @Bean
    public HibernateCursorItemReader sqlBookReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<Book>()
                .name("sqlBookReader")
                .sessionFactory(sessionFactory)
                .queryString("from Book")
                .build();
    }

    @Bean
    public MongoItemWriter<MongoBook> noSqlBookWriter() {
        MongoItemWriter<MongoBook> writer = new MongoItemWriter<MongoBook>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("mongoBooks");
        return writer;
    }

//    @StepScope
//    @Bean
//    public FlatFileItemReader<Person> reader(@Value("#{jobParameters['" + INPUT_FILE_NAME + "']}") String inputFileName) {
//        return new FlatFileItemReaderBuilder<Person>()
//                .name("personItemReader")
//                .resource(new FileSystemResource(inputFileName))
//
//                // Работа через lineMapper
//                .lineMapper((s, i) -> {
//                    String[] fieldsValues = s.split(",");
//                    return new Person(fieldsValues[0], Integer.parseInt(fieldsValues[1]));
//                })
///*
//
//                // Работа через fieldSetMapper
//                .delimited()
//                .names("name", "age")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                    setTargetType(Person.class);
//                }})
//*/
//
//
//                .build();
//    }
//
//    @StepScope
//    @Bean
//    public ItemProcessor processor(HappyBirthdayService happyBirthdayService) {
//        return (ItemProcessor<Person, Person>) happyBirthdayService::doHappyBirthday;
//    }
//
//    @StepScope
//    @Bean
//    public FlatFileItemWriter writer(@Value("#{jobParameters['" + OUTPUT_FILE_NAME + "']}") String outputFileName) {
//        return new FlatFileItemWriterBuilder<>()
//                .name("personItemWriter")
//                .resource(new FileSystemResource(outputFileName))
//                .lineAggregator(new DelimitedLineAggregator<>())
//                .build();
//    }
//
//    @Bean
//    public Job importUserJob(Step step1) {
//        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
//                .incrementer(new RunIdIncrementer())
//                .flow(step1)
//                .end()
//                .listener(new JobExecutionListener() {
//                    @Override
//                    public void beforeJob(JobExecution jobExecution) {
//                        logger.info("Начало job");
//                    }
//
//                    @Override
//                    public void afterJob(JobExecution jobExecution) {
//                        logger.info("Конец job");
//                    }
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step1(FlatFileItemWriter writer, ItemReader reader, ItemProcessor itemProcessor) {
//        return stepBuilderFactory.get("step1")
//                .chunk(CHUNK_SIZE)
//                .reader(reader)
//                .processor(itemProcessor)
//                .writer(writer)
//                .listener(new ItemReadListener() {
//                    public void beforeRead() {
//                        logger.info("Начало чтения");
//                    }
//
//                    public void afterRead(Object o) {
//                        logger.info("Конец чтения");
//                    }
//
//                    public void onReadError(Exception e) {
//                        logger.info("Ошибка чтения");
//                    }
//                })
//                .listener(new ItemWriteListener() {
//                    public void beforeWrite(List list) {
//                        logger.info("Начало записи");
//                    }
//
//                    public void afterWrite(List list) {
//                        logger.info("Конец записи");
//                    }
//
//                    public void onWriteError(Exception e, List list) {
//                        logger.info("Ошибка записи");
//                    }
//                })
//                .listener(new ItemProcessListener() {
//                    public void beforeProcess(Object o) {
//                        logger.info("Начало обработки");
//                    }
//
//                    public void afterProcess(Object o, Object o2) {
//                        logger.info("Конец обработки");
//                    }
//
//                    public void onProcessError(Object o, Exception e) {
//                        logger.info("Ошбка обработки");
//                    }
//                })
//                .listener(new ChunkListener() {
//                    public void beforeChunk(ChunkContext chunkContext) {
//                        logger.info("Начало пачки");
//                    }
//
//                    public void afterChunk(ChunkContext chunkContext) {
//                        logger.info("Конец пачки");
//                    }
//
//                    public void afterChunkError(ChunkContext chunkContext) {
//                        logger.info("Ошибка пачки");
//                    }
//                })
////                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .build();
//    }
}

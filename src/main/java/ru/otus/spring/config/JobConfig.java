package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.entity.nosql.MongoBook;
import ru.otus.spring.entity.sql.Book;
import ru.otus.spring.service.ConvertEntityServiceImpl;

import java.util.List;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job postgresToMongoDbMigrationJob(Step migrateRowsFromPostgresStep) {
        return jobBuilderFactory.get("postgresToMongoDbMigrationJob")
                .start(migrateRowsFromPostgresStep)
                .build();
    }

//    @Bean
//    public Step migrateRowsFromPostgresStep(HibernateCursorItemReader<Book> sqlBookReader, ItemProcessor<Book, MongoBook> convertBookToMongoBookProcessor,
//                                            MongoItemWriter<MongoBook> noSqlBookWriter) {
//        return stepBuilderFactory.get("migrateRowsFromPostgresStep")
//                .<Book, Book>chunk(CHUNK_SIZE)
//                .reader(sqlBookReader)
//                .processor(convertBookToMongoBookProcessor)  // Вот этот метод имеет неверную сигнатуру. Почему-то требуется обязательное наследование от класса Book. Как выполнить приобразование одного объекта в другой я так и не понял =(
//                .writer(noSqlBookWriter)
//                .build();
//    }

    //    Закоментированный метод выше - это моя попытка сделать ItemProcessor типизированным, но тогда проект не собирается
    @Bean
    public Step migrateRowsFromPostgresStep(HibernateCursorItemReader<Book> sqlBookReader, ItemProcessor convertBookToMongoBookProcessor,
                                            MongoItemWriter<MongoBook> noSqlBookWriter) {
        return stepBuilderFactory.get("migrateRowsFromPostgresStep")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(sqlBookReader)
                .processor(convertBookToMongoBookProcessor)// виной всему, вот эта строчка
                .writer(noSqlBookWriter)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() {
                        log.info("Read start");
                    }

                    public void afterRead(Book o) {
                        log.info("Read end");
                    }

                    public void onReadError(Exception e) {
                        log.info("Read error " + e);
                    }
                })
                .listener(new ItemWriteListener<MongoBook>() {
                    public void beforeWrite(List<? extends MongoBook> list) {
                        log.info("Write start");
                    }

                    public void afterWrite(List<? extends MongoBook> list) {
                        log.info("Write end");
                    }

                    public void onWriteError(Exception e, List<? extends MongoBook> list) {
                        log.info("Write error " + e);
                    }
                })
                .listener(new ItemProcessListener<Book, MongoBook>() {
                    public void beforeProcess(Book o) {
                        log.info("Start process");
                    }

                    public void afterProcess(Book o, MongoBook o2) {
                        log.info("End process");
                    }

                    @Override
                    public void onProcessError(Book book, Exception e) {
                        log.info("Process error " + e);
                    }

                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        log.info("Start chunk");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        log.info("End chunk");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        log.info("Chunk error " + chunkContext.toString());
                    }
                })
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> convertBookToMongoBookProcessor(ConvertEntityServiceImpl convertEntityService) {
        return convertEntityService::convertSqlBookEntityToMongoBook;
    }

    @Bean
    public HibernateCursorItemReader<Book> sqlBookReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<Book>()
                .name("sqlBookReader")
                .sessionFactory(sessionFactory)
                .queryString("from Book")
                .build();
    }

    @Bean
    public MongoItemWriter<MongoBook> noSqlBookWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<MongoBook> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("mongoBooks");
        return writer;
    }
}

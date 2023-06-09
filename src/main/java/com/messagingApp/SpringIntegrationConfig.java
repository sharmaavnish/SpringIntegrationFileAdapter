package com.messagingApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration
@EnableIntegration
public class SpringIntegrationConfig {
@Bean
@InboundChannelAdapter(value = "fileInputChannel",poller = @Poller( fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource(){
    CompositeFileListFilter<File> filter=new CompositeFileListFilter<>();
    filter.addFilter(new SimplePatternFileListFilter("*.txt"));
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("C:\\Users\\User\\Downloads\\messagingPart"));
        reader.setFilter(filter);
        return reader;
    }
  @Bean
  @ServiceActivator(inputChannel = "fileInputChannel")
    public FileWritingMessageHandler fileWritingMessageHandler(){
    FileWritingMessageHandler writter = new FileWritingMessageHandler(new File("C:\\Users\\User\\Downloads\\messagingCopiedPart"));
    writter.setAutoCreateDirectory(true);
    writter.setExpectReply(false);
    return writter;
    }
}

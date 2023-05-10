package com.elsprage.learning.external.api.words;

import com.elsprage.learning.common.constant.Constants;
import com.elsprage.learning.external.api.WebClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = WordsApiProperties.PREFIX)
public class WordsApiProperties extends WebClientProperties {
    public static final String PREFIX = Constants.ELSPRAGE_PREFIX + ".words.api";
}

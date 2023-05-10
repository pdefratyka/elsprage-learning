package com.elsprage.learning.external.api;

import lombok.Data;


@Data
public abstract class WebClientProperties {
    private String url;
    private String apiName;
}

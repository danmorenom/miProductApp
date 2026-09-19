package com.mycompany.myproductapp.api.client.config;

import com.mycompany.myproductapp.api.client.generated.api.DefaultApi;
import com.mycompany.myproductapp.api.client.generated.invoker.ApiClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProductApiClientConfig {

    @Bean
    public ApiClient apiClient(@Value("${external-api.base-url}") String baseUrl) {
        return new ApiClient().setBasePath(baseUrl);
    }

    @Bean
    public DefaultApi defaultApi(ApiClient apiClient) {
        return new DefaultApi(apiClient);
    }
}
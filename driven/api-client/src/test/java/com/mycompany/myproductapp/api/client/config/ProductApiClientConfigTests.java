package com.mycompany.myproductapp.api.client.config;

import com.mycompany.myproductapp.api.client.generated.api.DefaultApi;
import com.mycompany.myproductapp.api.client.generated.invoker.ApiClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductApiClientConfigTests {

    private final ProductApiClientConfig config =
            new ProductApiClientConfig();

    @Test
    void apiClientShouldCreateApiClientWithConfiguredBaseUrl() {
        // Given
        String baseUrl = "http://localhost:8080";

        // When
        ApiClient apiClient = config.apiClient(baseUrl);

        // Then
        assertNotNull(apiClient);
        assertEquals(baseUrl, apiClient.getBasePath());
    }

    @Test
    void apiClientShouldSupportNullBaseUrl() {
        // When
        ApiClient apiClient = config.apiClient(null);

        // Then
        assertNotNull(apiClient);
        assertNull(apiClient.getBasePath());
    }

    @Test
    void defaultApiShouldCreateDefaultApiUsingProvidedApiClient() {
        // Given
        ApiClient apiClient = new ApiClient();

        // When
        DefaultApi defaultApi = config.defaultApi(apiClient);

        // Then
        assertNotNull(defaultApi);
    }

    @Test
    void defaultApiShouldCreateDifferentInstancesForDifferentCalls() {
        // Given
        ApiClient apiClient = new ApiClient();

        // When
        DefaultApi defaultApi1 = config.defaultApi(apiClient);
        DefaultApi defaultApi2 = config.defaultApi(apiClient);

        // Then
        assertNotNull(defaultApi1);
        assertNotNull(defaultApi2);
        assertNotSame(defaultApi1, defaultApi2);
    }

}
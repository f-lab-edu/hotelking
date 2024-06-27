package com.hotelking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "minshik kim"
        ),
        description = "호텔킹 프로젝트 OpenApi 문서",
        title = "호텔킹 API 문서"
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "https://localhost:8080"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {


  @Bean
  public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
    return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
      ApiResponses apiResponses = operation.getResponses();
      apiResponses.forEach((statusCode, apiResponse) -> {
        if ("200".equals(statusCode)) {
          Content content = apiResponse.getContent();
          if (content != null) {
            content.forEach((mediaTypeKey, mediaType) -> {
              Schema<?> originalSchema = mediaType.getSchema();
              if (isApiResponseVoidSchema(originalSchema)) {
                mediaType.setSchema(createSuccessSchema());
              } else {
                // 기존 schema 에서 data 필드만 가져옵니다.
                Schema<?> refSchema = getSchemaByRef(openApi, originalSchema.get$ref());
                Schema<?> dataSchema = extractDataSchema(refSchema);
                mediaType.setSchema(createSuccessSchemaWithData(dataSchema));
              }
            });
          }
        }
      });
    }));
  }

  private Schema<?> getSchemaByRef(OpenAPI api, String ref) {
    if (ref.startsWith("#/components/schemas/")) {
      String schemaName = ref.substring("#/components/schemas/".length());
      return api.getComponents().getSchemas().get(schemaName);
    }
    throw new IllegalArgumentException("Invalid reference format: " + ref);
  }

  private Schema<?> extractDataSchema(Schema<?> originalSchema) {
    if (originalSchema.getProperties() != null && originalSchema.getProperties().containsKey("data")) {
      return (Schema<?>) originalSchema.getProperties().get("data");
    }
    return originalSchema;
  }

  private boolean isApiResponseVoidSchema(Schema<?> schema) {
    return schema.get$ref().equals("#/components/schemas/ApiResponseVoid");
  }

  private Schema<?> createSuccessSchema() {
    Schema<?> successSchema = new Schema<>();
    successSchema.addProperty("result", new Schema<>().type("string").example("success"));
    return successSchema;
  }

  private Schema<?> createSuccessSchemaWithData(Schema<?> dataSchema) {
    Schema<?> successSchema = createSuccessSchema();
    successSchema.addProperty("data", dataSchema);
    return successSchema;
  }

}

package dev.greatseo.backbone.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import dev.greatseo.backbone.BackboneApplication;
import dev.greatseo.backbone.test.config.TestProfile;
import dev.greatseo.backbone.test.setup.domain.MemberSetup;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({
        RestDocsConfiguration.class
//        MemberSetup.class
})
@ActiveProfiles(TestProfile.TEST)
@Transactional
public class IntegrationTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected MemberSetup memberSetup;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ResourceLoader resourceLoader;

    protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}/";

    protected RequestSpecification spec;

    @LocalServerPort
    int port;

    protected final String identifier = "{class-name}/{method-name}";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
                .setPort(port)
                .addFilter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .addFilter((Filter) new CharacterEncodingFilter( "UTF-8", true ))
                .build();
    }

    protected <T> T readValue(final String path, Class<T> clazz) throws IOException {
        final InputStream json = resourceLoader.getResource(path).getInputStream();
        return objectMapper.readValue(json, clazz);
    }

    protected String readJson(final String path) throws IOException {
        final InputStream inputStream = resourceLoader.getResource(path).getInputStream();
        final ByteSource byteSource = new ByteSource() {
            @Override
            public InputStream openStream() {
                return inputStream;
            }
        };
        return byteSource.asCharSource(Charsets.UTF_8).read();
    }

}

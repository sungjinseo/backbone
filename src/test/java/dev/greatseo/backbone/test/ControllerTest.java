package dev.greatseo.backbone.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import dev.greatseo.backbone.BackboneApplication;
import dev.greatseo.backbone.test.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BackboneApplication.class)
@ActiveProfiles(TestProfile.TEST)
@Disabled
public class ControllerTest {

    @Autowired
    protected ResourceLoader resourceLoader;

    protected ObjectMapper objectMapper = buildObjectMapper();

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

    private ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    private RestDocumentationResultHandler buildDocument() {
        return document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
    }
}

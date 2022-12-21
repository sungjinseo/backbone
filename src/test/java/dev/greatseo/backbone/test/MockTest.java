package dev.greatseo.backbone.test;

import dev.greatseo.backbone.test.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(TestProfile.TEST)
@Disabled
public class MockTest {

}

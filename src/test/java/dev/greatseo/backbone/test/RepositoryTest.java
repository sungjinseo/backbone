package dev.greatseo.backbone.test;

import dev.greatseo.backbone.test.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
@Disabled
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
}

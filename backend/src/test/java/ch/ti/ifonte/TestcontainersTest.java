package ch.ti.ifonte;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.*;

@Testcontainers
class TestcontainersTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

}
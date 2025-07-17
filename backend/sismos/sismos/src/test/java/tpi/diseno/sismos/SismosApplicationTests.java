package tpi.diseno.sismos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tpi.diseno.sismos.model.GenerarSismograma;

@SpringBootTest
class SismosApplicationTests {
    @MockBean  // Simula la interfaz en el test
    private GenerarSismograma generarSismograma;

	@Test
	void contextLoads() {
	}

}
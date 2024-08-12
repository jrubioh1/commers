package com.springboot.commers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.commers.entities.Rol;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class GenericCommersApplicationTests {

	@Autowired
	MockMvc mvc;

	@BeforeAll
	static void setUpOnce(@Autowired MockMvc mvc) throws Exception {
		ensureRoleExists(mvc, "ROLE_ADMIN");
		ensureRoleExists(mvc, "ROLE_CLIENT");
		ensureRoleExists(mvc, "ROLE_CLIENT_PREMIUM");
		ensureRoleExists(mvc, "ROLE_TO_DELETE");
	}

	static void ensureRoleExists(MockMvc mvc, String roleName) throws Exception {
		String roleJson = String.format("""
				{
				    "name": "%s"
				}
				""", roleName);

		mvc.perform(post("/api/rol").contentType(APPLICATION_JSON).content(roleJson))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (status != 201 && status != 409) {
						throw new AssertionError("Unexpected status: " + status);
					}
				});
	}

	@Test
	void testListRol() throws Exception {
		mvc.perform(get("/api/rol"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testNoDuplicateRol() throws Exception {
		String rolAdmin = """
				{
				    "name":"ROLE_ADMIN"
				}
				""";

		mvc.perform(post("/api/rol").contentType(APPLICATION_JSON).content(rolAdmin))
				.andExpect(status().isConflict());
	}

	@Test
	void testDeleteRol() throws Exception {
		// Eliminar un rol existente
		String rolesResponse = mvc.perform(get("/api/rol"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		ObjectMapper objectMapper = new ObjectMapper();
		List<Rol> roles = objectMapper.readValue(rolesResponse, new TypeReference<List<Rol>>() {
		});
		Rol rolToDelete = roles.stream()
				.filter(rol -> "ROLE_TO_DELETE".equals(rol.getName()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("Rol no encontrado"));

		// Eliminar el rol usando el ID encontrado
		mvc.perform(delete("/api/rol/" + rolToDelete.getId()))
				.andExpect(status().isOk());

		// Verificar que el rol ha sido eliminado
		mvc.perform(get("/api/rol/" + rolToDelete.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	void testNotFoundRol() throws Exception {
		mvc.perform(get("/api/rol/999")) // ID 999 para asegurarnos que no existe
				.andExpect(status().isNotFound());
	}

	@Test
	void testCreateClient() throws Exception {
		String clientNew = """
				{
				    "name": "Michael Brown Client",
				    "email": "michael.brown@example.com",
				    "password": "Password123."
				}
				""";

		mvc.perform(post("/api/clients").contentType(APPLICATION_JSON).content(clientNew))
				.andExpect(status().isCreated());
	}

	@Test
	void testDeleteClient() throws Exception {
		// Crear un cliente
		String client = """
				{
				    "name": "Michael Brown Client",
				    "email": "michael.brown@example.com",
				    "password": "Password123."
				}
				""";

		// Enviar la solicitud para crear el cliente y obtener el ID del cliente creado
		String response = mvc.perform(post("/api/clients")
				.contentType(APPLICATION_JSON)
				.content(client))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> clientResponse = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
		});
		Long clientId = ((Number) clientResponse.get("id")).longValue();

		// Eliminar el cliente usando el ID obtenido
		mvc.perform(delete("/api/clients/" + clientId))
				.andExpect(status().isOk());

		// Verificar que el cliente eliminado ya no existe
		mvc.perform(get("/api/clients/" + clientId))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetClients() throws Exception {
		mvc.perform(get("/api/clients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testViewClientsNotFound() throws Exception {
		mvc.perform(get("/api/clients/999")) // ID 999 para asegurarnos que no existe
				.andExpect(status().isNotFound());
	}
}

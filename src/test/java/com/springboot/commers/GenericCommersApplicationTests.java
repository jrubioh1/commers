package com.springboot.commers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Rol;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
		ensureRoleExists(mvc, "ROLE_EMPLOYEE");
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
	void testClient() throws Exception {
		String clientNew = """
				{
				    "name": "Michael Brown Client",
				    "email": "michael.brown@example.com",
				    "password": "Password123."
				}
				""";

		mvc.perform(post("/api/clients").contentType(APPLICATION_JSON).content(clientNew))
				.andExpect(status().isCreated());
		
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


				mvc.perform(get("/api/clients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());

		mvc.perform(get("/api/clients/999")) // ID 999 para asegurarnos que no existe
				.andExpect(status().isNotFound());

	}


    @Test
    void testCrudOperationsForEmployee() throws Exception {
        // Create a new Employee
        String newEmployeeJson = """
                {
                    "name": "John Doe",
                    "email": "johndoe@example.com",
                    "password": "securePassword123."
                }
                """;

        String createResponse = mvc.perform(post("/api/employees")
                .contentType(APPLICATION_JSON)
                .content(newEmployeeJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> employeeResponse = objectMapper.readValue(createResponse, new TypeReference<Map<String, Object>>() {
        });
        Long employeeId = ((Number) employeeResponse.get("id")).longValue();

        // Read the created Employee
        mvc.perform(get("/api/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        // Update the Employee's name
        String updatedEmployeeJson = """
                {
                    "name": "Jane Doe",
                    "email": "johndoe@example.com",
                    "password": "securePassword123."
                }
                """;

        mvc.perform(put("/api/employees/" + employeeId)
                .contentType(APPLICATION_JSON)
                .content(updatedEmployeeJson))
                .andExpect(status().isOk());

        // Verify the update
        mvc.perform(get("/api/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        // Delete the Employee
        mvc.perform(delete("/api/employees/" + employeeId))
                .andExpect(status().isOk());

        // Verify deletion
        mvc.perform(get("/api/employees/" + employeeId))
                .andExpect(status().isNotFound());
    }


	@Test
    void testCrudOperationsForProduct() throws Exception {




        // Create a new Product
        String newProductJson = """
             {
                    "name": "Laptop",
                    "price": 1200.00,
                    "description": "High-end gaming laptop",
                    "serial":"dsfg785", 
                    "stock": 10
                }
                """;

        String createResponse = mvc.perform(post("/api/product")
                .contentType(APPLICATION_JSON)
                .content(newProductJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> productResponse = objectMapper.readValue(createResponse, new TypeReference<Map<String, Object>>() {
        });
        Long productId = ((Number) productResponse.get("id")).longValue();

        // Read the created Product
        mvc.perform(get("/api/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1200.00))
                .andExpect(jsonPath("$.description").value("High-end gaming laptop"))
				.andExpect(jsonPath("$.serial").value("dsfg78"))
				.andExpect(jsonPath("$.stock").value(10));

        // Update the Product's name and price
        String updatedProductJson = """
             {
                    "name": "Gaming Laptop",
                    "price": 1300.00,
                    "description": "High-end gaming laptop",
                    "serial":"dsfg78", 
                    "stock": 10
                }
                """;

        mvc.perform(put("/api/product/" + productId)
                .contentType(APPLICATION_JSON)
                .content(updatedProductJson))
                .andExpect(status().isOk());

        // Verify the update
        mvc.perform(get("/api/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaming Laptop"))
                .andExpect(jsonPath("$.price").value(1300.00))
                .andExpect(jsonPath("$.description").value("High-end gaming laptop"))
				.andExpect(jsonPath("$.serial").value("dsfg78"))
				.andExpect(jsonPath("$.stock").value(10));


        // Delete the Product
        mvc.perform(delete("/api/product/" + productId))
                .andExpect(status().isOk());

        // Verify deletion
        mvc.perform(get("/api/product/" + productId))
                .andExpect(status().isNotFound());
    }
}

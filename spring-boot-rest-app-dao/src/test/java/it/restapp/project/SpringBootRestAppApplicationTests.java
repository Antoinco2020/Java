package it.restapp.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.restapp.project.dtos.AuthorDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SpringBootRestAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private static final String name = "Test";
	private final ObjectMapper mapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	@BeforeEach
	public void insert(){
		try {
			AuthorDto authorDto = new AuthorDto();
			authorDto.setName(name);
			authorDto.setSurname("SurnameTest");
			authorDto.setDateOfBirth(LocalDate.of(1970, 12, 30));
			authorDto.setNationality("USA");

			mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/author/insert")
							.contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(authorDto)))
					.andExpect(status().isCreated())
					.andReturn();
		}
		catch (Exception e){
			fail(e);
		}
	}

	@Test
	@Order(3)
	public void getAllAuthors() {
		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/author/all"))
					.andExpect(status().isOk())
					.andReturn();

			String responseContent = result.getResponse().getContentAsString();
			assertNotNull(responseContent);
			assertFalse(responseContent.isEmpty());
		}
		catch (Exception e){
			fail(e);
		}
	}
	@Test
	@Order(2)
	public void getAuthorByName() {
		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/author/get")
							.param("name", name))
					.andExpect(status().isOk())
					.andReturn();
			String responseContent = result.getResponse().getContentAsString();
			assertNotNull(responseContent);
			assertFalse(responseContent.isEmpty());
		}
		catch (Exception e){
			fail(e);
		}
	}

	@Test
	@Order(1)
	public void insertAuthor() {
		try {
			AuthorDto authorDto = new AuthorDto();
			authorDto.setName(name);
			authorDto.setSurname("SurnameTest");
			authorDto.setDateOfBirth(LocalDate.of(1970, 12, 30));
			authorDto.setNationality("USA");

			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/author/insert")
							.contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(authorDto)))
					.andExpect(status().isCreated())
					.andReturn();

			String responseContent = result.getResponse().getContentAsString();

			assertNotNull(responseContent);
			assertFalse(responseContent.isEmpty());
		}
		catch (Exception e){
			fail(e);
		}
	}
}

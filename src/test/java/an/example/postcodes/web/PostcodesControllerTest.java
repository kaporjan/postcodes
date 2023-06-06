package an.example.postcodes.web;

import an.example.postcodes.IntegrationTest;
import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.repository.PostCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostcodesControllerTest extends IntegrationTest {
    @Autowired
    private PostCodeRepository repository;
    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    void getPostcode() throws Exception {
        mvc.perform((get("/postcodes")
                        .queryParam("postcode","AB10 1XG")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postcode", equalTo("AB10 1XG")))
                .andExpect(jsonPath("$.coordinate.lat", equalTo(57.1441560)))
                .andExpect(jsonPath("$.coordinate.lon", equalTo(-2.1148640)));
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    void updatePostcode() throws Exception {
        mvc.perform((post("/postcodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postcode\":\"AB10 1XG\",\"coordinate\":{\"lat\":77.144156,\"lon\":2.114864}}")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postcode", equalTo("AB10 1XG")))
                .andExpect(jsonPath("$.coordinate.lat", equalTo(77.1441560)))
                .andExpect(jsonPath("$.coordinate.lon", equalTo(2.1148640)));
        Optional<PostCodeEntity> actual = repository.findByPostcode("AB10 1XG");
        PostCodeEntity expected = new PostCodeEntity("AB10 1XG", 77.1441560, 2.1148640);
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(username = "username", roles={"NONE"})
    void updatePostcode_withoutAuthorizationReturns403() throws Exception {
        mvc.perform((post("/postcodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postcode\":\"AB10 1XG\",\"coordinate\":{\"lat\":77.144156,\"lon\":2.114864}}")))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "username", roles={"NONE"})
    void getPostcode_withoutAuthorizationReturns403() throws Exception {
        mvc.perform((get("/postcodes")
                        .queryParam("postcode","AB10 1XG")))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    void unknownPostcode_ReturnsNotFound() throws Exception {
        mvc.perform((get("/postcodes")
                        .queryParam("postcode","AB00 AA")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    void missingPostcode_ReturnsBadRequest() throws Exception {
        mvc.perform((get("/postcodes")))
                .andExpect(status().isBadRequest());
    }
}
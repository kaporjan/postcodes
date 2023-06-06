package an.example.postcodes.web;

import an.example.postcodes.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DistanceControllerTest  extends IntegrationTest {
    @Test
    void getDistance() throws Exception {
        mvc.perform((get("/postcodes/distance")
                        .queryParam("from","AB10 1XG")
                        .queryParam("to","AB12 9SP")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromPostCode", equalTo("AB10 1XG")))
                .andExpect(jsonPath("$.from.lat", equalTo(57.1441560)))
                .andExpect(jsonPath("$.from.lon", equalTo(-2.1148640)))
                .andExpect(jsonPath("$.toPostCode", equalTo("AB12 9SP")))
                .andExpect(jsonPath("$.to.lat", equalTo(57.1487070)))
                .andExpect(jsonPath("$.to.lon", equalTo(-2.0978060)))
                .andExpect(jsonPath("$.distance", equalTo(1.1466862148498147)))
                .andExpect(jsonPath("$.unit", equalTo("Km")));
    }

    @Test
    void unknownPostcode_ReturnsNotFound() throws Exception {
        mvc.perform((get("/postcodes/distance")
                        .queryParam("from","AB00 AA")
                        .queryParam("to","AB00 BB")))
                .andExpect(status().isNotFound());
    }

    @Test
    void missingPostcode_ReturnsBadRequest() throws Exception {
        mvc.perform((get("/postcodes/distance")
                        .queryParam("to","AB00 BB")))
                .andExpect(status().isBadRequest());
    }
}
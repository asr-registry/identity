package af.asr.identity.integrationTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GroupApiResourceTests {

    @Autowired
    private MockMvc mockMvc;



    @WithMockUser("ADMIN")
    @Test
    public void testApiAvailability() throws Exception {
        this.mockMvc.perform(get("/api/groups")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/api/groups")).andDo(print()).andExpect(status().isOk());
    }
}

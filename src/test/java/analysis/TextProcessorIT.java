package analysis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TextProcessorIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void getHello() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertThat(response.getBody(), equalTo("Welcome to textual analysis! Enter your text file."));
    }

    @Test
    public void shouldUploadFile() {
        ClassPathResource resource = new ClassPathResource("firstmost-example.txt", getClass());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/", map, String.class);

        assertThat(response.getBody(), equalTo(
                "Word count = 9\n" +
                        "Average word length = 4.556\n" +
                        "Number of words of length 1 is 1\n" +
                        "Number of words of length 2 is 1\n" +
                        "Number of words of length 3 is 1\n" +
                        "Number of words of length 4 is 2\n" +
                        "Number of words of length 5 is 2\n" +
                        "Number of words of length 7 is 1\n" +
                        "Number of words of length 10 is 1\n" +
                        "The most frequently occurring word length is 2, for word lengths of 4 & 5\n"));
    }

    @Test
    public void shouldRejectIncorrectFileTypes() throws Exception {
        ClassPathResource resource = new ClassPathResource("testinput.json", getClass());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/", map, String.class);

        assertThat(response.getBody(), equalTo("Sorry - only text/plain filetypes are allowed in this version."));
    }

}

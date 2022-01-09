package analysis;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class StringChomperTest {

    StringChomper underTest;

    @Test
    void outputsCorrectNumberOfWords() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-regularspacing.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7"));
    }

    @Test
    void handlesIrregularSpacing() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-irregularspacing.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7"));
    }

    @Test
    void handlesSpecialCharacters() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-specialcharacters.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7"));
    }

    @Test
    void generatesCorrectAverageWordLength() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-specialcharacters.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getAverageWordLength(), equalTo("Average word length = 5.429"));
    }

    @Test
    void generatesAnArrayOfCountPerWordLength() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-regularspacing.txt");
        underTest = new StringChomper(inputFile);

        String[] result = underTest.numberOfWordsPerWordLength();

        assertThat(result[0], equalTo("Number of words of length 1 is 1"));
    }

    private MultipartFile createMultipartFileForTest(String filePath) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filePath));
        return new MockMultipartFile(filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length() - 4),
                filePath.substring(filePath.lastIndexOf('/') + 1),
                MediaType.TEXT_PLAIN_VALUE,
                content);
    }
}
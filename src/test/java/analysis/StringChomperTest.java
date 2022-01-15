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
    void outputsCorrectWordCount() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-regularspacing.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7\n"));
    }

    @Test
    void handlesIrregularSpacingInWordCount() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-irregularspacing.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7\n"));
    }

    @Test
    void handlesSpecialCharactersInWordCount() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/testinput-specialcharacters.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getWordCountFromFile(), equalTo("Word count = 7\n"));
    }

    @Test
    void generatesCorrectAverageWordLengthNotIncludingPeriods() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example.txt");
        underTest = new StringChomper(inputFile);

        assertThat(underTest.getAverageWordLength(), equalTo("Average word length = 4.556\n"));
    }

    @Test
    void generatesAnArrayOfCountPerWordLength() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example.txt");
        underTest = new StringChomper(inputFile);

        String[] result = underTest.numberOfWordsPerWordLength();

        assertThat(result[0], equalTo("Number of words of length 1 is 1\n"));
        assertThat(result[1], equalTo("Number of words of length 2 is 1\n"));
        assertThat(result[2], equalTo("Number of words of length 3 is 1\n"));
        assertThat(result[3], equalTo("Number of words of length 4 is 2\n"));
        assertThat(result[4], equalTo("Number of words of length 5 is 2\n"));
        assertThat(result[5], equalTo("Number of words of length 7 is 1\n"));
        assertThat(result[6], equalTo("Number of words of length 10 is 1\n"));
    }

    @Test
    void generatesMostFrequentWordLengths() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example.txt");
        underTest = new StringChomper(inputFile);

        String result = underTest.mostFrequentWordLengthsGiven();

        assertThat(result, equalTo("The most frequently occurring word length is 2, for word lengths of 4 & 5\n"));
    }

    @Test
    void generatesMostFrequentWordLengthsForTriadOfEquallyFrequentLengths() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example-three-words.txt");
        underTest = new StringChomper(inputFile);

        String result = underTest.mostFrequentWordLengthsGiven();

        assertThat(result, equalTo("The most frequently occurring word length is 1, for word lengths of 2, 5 & 9\n"));
    }

    @Test
    void generatesMostFrequentWordLengthsForQuintupleOfEquallyFrequentLengths() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example-five-words.txt");
        underTest = new StringChomper(inputFile);

        String result = underTest.mostFrequentWordLengthsGiven();

        assertThat(result, equalTo("The most frequently occurring word length is 1, for word lengths of 1, 2, 3, 5 & 9\n"));
    }

    @Test
    void generatesMostFrequentWordLengthsForOneWordFile() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example-one-word.txt");
        underTest = new StringChomper(inputFile);

        String result = underTest.mostFrequentWordLengthsGiven();

        assertThat(result, equalTo("The most frequently occurring word length is 1, for a word length of 5\n"));
    }

    @Test
    void generateCompleteWordAnalysis() throws IOException {
        MultipartFile inputFile = createMultipartFileForTest("src/main/resources/analysis/firstmost-example.txt");
        underTest = new StringChomper(inputFile);

        String result = underTest.totalWordAnalysis();

        assertThat(result, equalTo("Word count = 9\nAverage word length = 4.556\nNumber of words of length 1 is 1\nNumber of words of length 2 is 1\nNumber of words of length 3 is 1\nNumber of words of length 4 is 2\nNumber of words of length 5 is 2\nNumber of words of length 7 is 1\nNumber of words of length 10 is 1\nThe most frequently occurring word length is 2, for word lengths of 4 & 5\n"));
    }

    private MultipartFile createMultipartFileForTest(String filePath) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filePath));
        return new MockMultipartFile(filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length() - 4),
                filePath.substring(filePath.lastIndexOf('/') + 1),
                MediaType.TEXT_PLAIN_VALUE,
                content);
    }
}
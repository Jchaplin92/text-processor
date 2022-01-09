package analysis;

import javafx.scene.shape.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
public class AnalysisController {

    @GetMapping("/")
    public String index() {
        return "Welcome to textual analysis! Enter your text file.";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());

        if (file.getContentType() == "text/plain") {
            StringChomper chomper = new StringChomper(file);
            return "You successfully uploaded " + file.getOriginalFilename() + "!";
        } else {
            return "Sorry - only text/plain filetypes are allowed in this version.";
        }

    }

}
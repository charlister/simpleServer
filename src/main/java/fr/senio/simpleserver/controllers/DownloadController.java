package fr.senio.simpleserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/download")
public class DownloadController {
    @GetMapping
    @RequestMapping("{fileName}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {
        String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/files/txt/");
        Path file = Paths.get(fileDirectory, fileName);

        if (Files.exists(file)) {
            response.setContentType("application/txt");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

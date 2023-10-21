package com.tutorial.kuboclient.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class IpfsController {
    @Autowired
    IpfsService ipfsService;

    @PostMapping("/contents")
    String[] postContents(@RequestParam("image") MultipartFile file) throws IOException {
        return ipfsService.createContents(file);
    }

    @GetMapping(value = "/contents/{cid}")
    ResponseEntity<?> getContents(@PathVariable("cid") String cid) throws IOException {
        String type = "image/png";
        byte[] contents = ipfsService.getContents(cid);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(contents.length)
                .contentType(MediaType.parseMediaType(type))
                .body(contents);
    }
}

package api.endeavorbackend.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class DeepLinkController {

    @GetMapping("/api/abrir-grupo/{id}")
    public ResponseEntity<Void> abrirGrupo(@PathVariable String id) {
        URI deeplinkUri = URI.create("endeavor://grupos/convite/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(deeplinkUri);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}

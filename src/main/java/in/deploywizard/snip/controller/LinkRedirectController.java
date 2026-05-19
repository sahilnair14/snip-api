package in.deploywizard.snip.controller;

import in.deploywizard.snip.model.Link;
import in.deploywizard.snip.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class LinkRedirectController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/r/{code}")
    public ResponseEntity<Void> redirectLink (@PathVariable String code) {
        Link link = linkService.getLinkByCode(code);
        if (!link.getIsActive()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(link.getOriginalUrl()))
                .build();
    }
}

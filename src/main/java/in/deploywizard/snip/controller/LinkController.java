package in.deploywizard.snip.controller;

import in.deploywizard.snip.dto.GenerateLinkRequest;
import in.deploywizard.snip.dto.UpdateLinkRequest;
import in.deploywizard.snip.model.Link;
import in.deploywizard.snip.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping
    public ResponseEntity<List<Link>> getAllLinks() {
        return ResponseEntity.ok(linkService.getAllLinks());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Link> getLinkByCode(@PathVariable String code) {
        return ResponseEntity.ok(linkService.getLinkByCode(code));
    }

    @PostMapping
    public ResponseEntity<Link> generateLink(@RequestBody GenerateLinkRequest linkRequest) {
        Link link = linkService.generateLink(linkRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(link);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Link> updateLinkById(@RequestBody UpdateLinkRequest linkRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(linkService.updateLink(linkRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLinkById(@PathVariable UUID id) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

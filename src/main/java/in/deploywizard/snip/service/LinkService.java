package in.deploywizard.snip.service;

import in.deploywizard.snip.dto.GenerateLinkRequest;
import in.deploywizard.snip.dto.UpdateLinkRequest;
import in.deploywizard.snip.model.Link;
import in.deploywizard.snip.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom secureRandom = new SecureRandom();

    private String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(BASE62.charAt(secureRandom.nextInt(BASE62.length())));
        }
        return code.toString();
    }

    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    public Link getLinkByCode(String code) {
        return linkRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Link generateLink(GenerateLinkRequest linkRequest) {

        String code;
        do {
            code = generateCode();
        } while (linkRepository.existsByCode(code));

        Link link = new Link();
        link.setActive(true);
        link.setCreatedAt(LocalDateTime.now());
        link.setOriginalUrl(linkRequest.getOriginalUrl());
        link.setLabel(linkRequest.getLabel());
        link.setCode(code);

        return linkRepository.save(link);
    }

    public Link updateLink(UpdateLinkRequest linkRequest, UUID linkId) {
        Link existingLink = linkRepository.findById(linkId)
                .orElseThrow();
        existingLink.setOriginalUrl(linkRequest.getOriginalUrl());
        existingLink.setLabel(linkRequest.getLabel());
        existingLink.setActive(linkRequest.getIsActive());
        return linkRepository.save(existingLink);
    }

    public void deleteLink(UUID linkId) {
        linkRepository.deleteById(linkId);
    }

}

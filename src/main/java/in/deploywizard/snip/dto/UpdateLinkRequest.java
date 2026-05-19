package in.deploywizard.snip.dto;

public class UpdateLinkRequest {

    private String originalUrl;
    private String label;
    private Boolean isActive;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getIsActive() { return isActive; }

}

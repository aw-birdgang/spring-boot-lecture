package birdgang.spring.lecture.common.exception;

public class ApiVersionException extends BusinessException {
    
    private final String requestedVersion;
    private final String[] supportedVersions;
    
    public ApiVersionException(String requestedVersion, String[] supportedVersions) {
        super("API_VERSION_NOT_SUPPORTED", 
              String.format("API 버전 '%s'은 지원되지 않습니다. 지원되는 버전: %s", 
                           requestedVersion, String.join(", ", supportedVersions)));
        this.requestedVersion = requestedVersion;
        this.supportedVersions = supportedVersions;
    }
    
    public ApiVersionException(String message) {
        super("API_VERSION_ERROR", message);
        this.requestedVersion = null;
        this.supportedVersions = null;
    }
    
    public String getRequestedVersion() {
        return requestedVersion;
    }
    
    public String[] getSupportedVersions() {
        return supportedVersions;
    }
}

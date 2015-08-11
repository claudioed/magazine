package infra.web;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public enum MediaType {
    
    APPLICATION_JSON("application/json");
    
    private final String mediaType;

    MediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String mediaType(){
        return this.mediaType;
    }
    
}

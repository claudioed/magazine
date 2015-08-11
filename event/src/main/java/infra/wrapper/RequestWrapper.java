package infra.wrapper;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public interface RequestWrapper<T> {
    
    T toJson();
    
}

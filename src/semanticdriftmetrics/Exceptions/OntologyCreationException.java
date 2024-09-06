/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticdriftmetrics.Exceptions;

/**
 *
 * @author stelios
 */
public class OntologyCreationException extends Exception{
    
    public OntologyCreationException() {
        super();
    }
    
    public OntologyCreationException(String message)
    {
       super(message);
    }
    
    public OntologyCreationException(Throwable cause)
    {
       super(cause);
    }
    
    public OntologyCreationException(String message, Throwable cause)
    {
       super(message, cause);
    }
    
}

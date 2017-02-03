
package com.dassault_systemes.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com._3ds.wserror.ErrorType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dassault_systemes.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PingErrorType_QNAME = new QName("urn:com:dassault_systemes:webservice", "pingErrorType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dassault_systemes.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link Ping }
     * 
     */
    public Ping createPing() {
        return new Ping();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:com:dassault_systemes:webservice", name = "pingErrorType")
    public JAXBElement<ErrorType> createPingErrorType(ErrorType value) {
        return new JAXBElement<ErrorType>(_PingErrorType_QNAME, ErrorType.class, null, value);
    }

}

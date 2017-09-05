package com.sree.restservices;

import com.dassault_systemes.platform.restServices.ModelerBase;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/resources/HondaRestServiceModeler")
public class HondaRestServiceModeler extends ModelerBase
{
  public Class<?>[] getServices()
  {
    return new Class[] { HondaFindObjects.class };
  }
}
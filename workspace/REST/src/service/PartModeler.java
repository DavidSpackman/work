package service;

import javax.ws.rs.ApplicationPath;

import com.dassault_systemes.platform.restServices.ModelerBase;

@ApplicationPath(ModelerBase.REST_BASE_PATH + "/part")
public class PartModeler extends ModelerBase {

    @Override
    public Class<?>[] getServices() {
        return new Class<?>[] { PartRestService.class };
    }

}

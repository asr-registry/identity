package af.asr.identity.resource.resource;

import af.asr.identity.data.model.Privilege;
import af.asr.identity.resource.handler.ResponseHandler;
import af.asr.identity.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/privileges")
public class PrivilegeApiResource extends ResponseHandler {

    @Autowired
    private PrivilegeService privilegeService;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Privilege>> findAll() {
        return ResponseEntity.ok(privilegeService.findAll());
    }
}

package af.asr.identity.resource.resource;


import af.asr.identity.data.model.Tenant;
import af.asr.identity.resource.handler.ResponseHandler;
import af.asr.identity.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/tenants")
public class TenantApiResource extends ResponseHandler {

    @Autowired
    private TenantService tenantService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> save(@Valid @RequestBody(required = true) Tenant tenant)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        tenantService.save(tenant);
        return ResponseEntity.ok(data);
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> update(@Valid @RequestBody(required = true) Tenant tenant, @PathVariable(value = "id", required = true) Long id)
    {
        //set id
        tenant.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        tenantService.save(tenant);
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Tenant>> findAll()
    {
        return ResponseEntity.ok(tenantService.findAll());
    }


    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Tenant> findByCity(@PathVariable(value = "name", required = true) String name)
    {
        return ResponseEntity.ok(tenantService.findByTenantName(name));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Tenant> findById(@PathVariable(value = "id", required = true) Long id)
    {
        return ResponseEntity.ok(tenantService.findById(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> delete(@PathVariable(value = "id", required = true) Long id)
    {
        Map<String, Object> data = new HashMap<>();
        if(tenantService.delete(id))
        {
            data.put("status", HttpStatus.OK);
            data.put("message", "Successfully Deleted");

        }else{
            data.put("status", HttpStatus.BAD_REQUEST);
            data.put("message", "Successfully isn't Deleted");
        }

        return ResponseEntity.ok(data);
    }
}

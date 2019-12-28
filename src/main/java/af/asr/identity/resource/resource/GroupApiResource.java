package af.asr.identity.resource.resource;

import af.asr.identity.data.model.Group;
import af.asr.identity.data.model.Role;
import af.asr.identity.resource.handler.ResponseHandler;
import af.asr.identity.service.GroupService;
import af.asr.identity.service.RoleService;
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
@RequestMapping(value = "/api/groups")
public class GroupApiResource extends ResponseHandler {

    @Autowired
    private GroupService groupService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Group>> findAll()
    {
        return ResponseEntity.ok(groupService.findAll());
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Group> findById(@PathVariable(name = "id", required = true) long id)
    {
        return ResponseEntity.ok(groupService.findById(id));
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Group> findByName(@PathVariable(name = "name", required = true) String name)
    {
        return ResponseEntity.ok(groupService.findByName(name));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> save(@Valid @RequestBody(required = true) Group group)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        groupService.save(group);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> update(@Valid @PathVariable(required = true, name = "id") long id, @RequestBody(required = true) Group group)
    {
        //set id
        group.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        groupService.save(group);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>>  delete(@PathVariable(name = "id", required = true) long id)
    {
        Map<String, Object> data = new HashMap<>();
        if(groupService.delete(id))
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

package ${packageName}.controller;

import ${packageName}.model.${modelName};
import ${packageName}.service.${serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class ${controllerName}{

    @Autowired
    ${serviceName} ${serviceName?uncap_first};

    @GetMapping("/get${modelName}s")
    public List<${modelName}> get${modelName}s(${modelName} ${modelName?uncap_first}){
        return ${serviceName?uncap_first}.get${modelName}s(${modelName?uncap_first});
    }

    @PostMapping("/insert${modelName}")
    public int insert${modelName}(${modelName} ${modelName?uncap_first}){
        return ${serviceName?uncap_first}.insert${modelName}(${modelName?uncap_first});
    }

    @PostMapping("/update${modelName}")
    public int update${modelName}(${modelName} ${modelName?uncap_first}){
        return ${serviceName?uncap_first}.update${modelName}(${modelName?uncap_first});
    }

    @PostMapping("/delete${modelName}")
    public int delete${modelName}(${modelName} ${modelName?uncap_first}){
        return ${serviceName?uncap_first}.delete${modelName}(${modelName?uncap_first});
    }

}
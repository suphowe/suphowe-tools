package ${packageName}.service;

import ${packageName}.model.${modelName};
import ${packageName}.mapper.${mapperName};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ${serviceName}{

    @Autowired
    ${mapperName} ${mapperName?uncap_first};

    public List<${modelName}> get${modelName}s(${modelName} ${modelName?uncap_first}){
        return ${mapperName?uncap_first}.get${modelName}s(${modelName?uncap_first});
    }

    public int insert${modelName}(${modelName} ${modelName?uncap_first}){
        return ${mapperName?uncap_first}.insert${modelName}(${modelName?uncap_first});
    }

    public int update${modelName}(${modelName} ${modelName?uncap_first}){
        return ${mapperName?uncap_first}.update${modelName}(${modelName?uncap_first});
    }

    public int delete${modelName}(${modelName} ${modelName?uncap_first}){
        return ${mapperName?uncap_first}.delete${modelName}(${modelName?uncap_first});
    }
}
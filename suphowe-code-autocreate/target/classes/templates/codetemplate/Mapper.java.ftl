package ${packageName}.mapper;

import ${packageName}.model.${modelName};
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ${mapperName}{

    List<${modelName}> get${modelName}s(${modelName} ${modelName?uncap_first});

    int insert${modelName}(${modelName} ${modelName?uncap_first});

    int update${modelName}(${modelName} ${modelName?uncap_first});

    int delete${modelName}(${modelName} ${modelName?uncap_first});
}
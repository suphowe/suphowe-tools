# spring-boot-demo-properties

> 本 demo 演示如何获取配置文件的自定义配置，以及如何多环境下的配置文件信息的获取

## pom.xml

```
		<!--
		在 META-INF/additional-spring-configuration-metadata.json 中配置
		可以去除 application.yml 中自定义配置的红线警告，并且为自定义配置添加 hint 提醒
		 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
```

## ApplicationProperty.java

```java
/**
 * 项目配置
 * @author suphowe
 */
@Data
@Component
public class ApplicationProperty {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
```

## DeveloperProperty.java

```
/**
 * 开发人员配置信息
 * .@ConfigurationProperties(prefix = "developer")  application.properties 中前缀为 soft 的配置信息
 * @author suphowe
 */
@Data
@ConfigurationProperties(prefix = "soft")
@Component
public class DeveloperProperty {
    private String version;
    private String name;
    private String phone;
}
```

## PropertyController.java

```java
/**
 * 测试Controller
 */
@RestController
public class PropertyController {
	private final ApplicationProperty applicationProperty;
	private final DeveloperProperty developerProperty;

	@Autowired
	public PropertyController(ApplicationProperty applicationProperty, DeveloperProperty developerProperty) {
		this.applicationProperty = applicationProperty;
		this.developerProperty = developerProperty;
	}

	@GetMapping("/property")
	public Dict index() {
		return Dict.create().set("applicationProperty", applicationProperty).set("developerProperty", developerProperty);
	}
}
```

## additional-spring-configuration-metadata.json

> 位置： src/main/resources/META-INF/additional-spring-configuration-metadata.json

```json
{
	"properties": [
		{
			"name": "application.name",
			"description": "Default value is artifactId in pom.xml.",
			"type": "java.lang.String"
		},
		{
			"name": "application.version",
			"description": "Default value is version in pom.xml.",
			"type": "java.lang.String"
		},
		{
			"name": "soft.version",
			"description": "The Developer version.",
			"type": "java.lang.String"
		},
		{
			"name": "soft.name",
			"description": "The Developer name.",
			"type": "java.lang.String"
		},
		{
			"name": "soft.phone",
			"description": "The Developer phone Number.",
			"type": "java.lang.String"
		}
	]
}
```


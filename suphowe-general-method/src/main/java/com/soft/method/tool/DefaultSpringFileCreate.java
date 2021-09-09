package com.soft.method.tool;


import com.soft.method.io.IoByteOperate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建默认的Spring工程文件
 *
 * @author suphowe
 */
public class DefaultSpringFileCreate {

    public static void main(String[] args){
        String rootDir = "C:/test/code/com/soft";
        DefaultSpringFileCreate.createFiles(rootDir);
    }

    public static void createFiles(String rootDir){
        List<String> dirs = new ArrayList<>();
        dirs.add(rootDir + "/sys");
        dirs.add(rootDir + "/controller");
        dirs.add(rootDir + "/service");
        dirs.add(rootDir + "/dao");

        //创建文件夹
        createDirs(dirs);
        //创建系统文件
        createSysFile(rootDir + "/sys");
    }

    /**
     * 创建文件夹
     */
    private static void createDirs(List<String> dirs) {
        for (String dir : dirs) {
            File dirFile = new File(dir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
        }
    }

    /**
     * 创建系统文件
     *
     * @param sysDir 系统文件路径
     */
    private static void createSysFile(String sysDir) {
        String softApplicationFile = sysDir + "/SoftApplication.java";
        String springConfigurationFile = sysDir + "/SpringConfiguration.java";
        String dataSourceConfigFile = sysDir + "/DataSourceConfig.java";

        // 写application启动类
        File softApplication = new File(softApplicationFile);
        if (!softApplication.exists()) {
            IoByteOperate writeApplication = new IoByteOperate(softApplicationFile, createApplication(), false, 1024, "utf-8");
            writeApplication.writeFileByFileOutputStream();
        }
        //写config类
        File springConfiguration = new File(springConfigurationFile);
        if (!springConfiguration.exists()) {
            IoByteOperate writeConfig = new IoByteOperate(springConfigurationFile, createSpringConfiguration(), false, 1024, "utf-8");
            writeConfig.writeFileByFileOutputStream();
        }
        //写数据库source类
        File dataSourceConfig = new File(dataSourceConfigFile);
        if (!dataSourceConfig.exists()) {
            IoByteOperate writeDataSourceConfig = new IoByteOperate(dataSourceConfigFile, createDataSourceConfig(), false, 1024, "utf-8");
            writeDataSourceConfig.writeFileByFileOutputStream();
        }
    }

    /**
     * Application文件
     */
    private static String createApplication() {
        StringBuilder application = new StringBuilder();
        application.append("import com.soft.sys.SpringConfiguration;\n\r");
        application.append("import org.mybatis.spring.annotation.MapperScan\n\r");
        application.append("import org.slf4j.Logger\n\r");
        application.append("import org.slf4j.LoggerFactory\n\r");
        application.append("import org.springframework.boot.SpringApplication\n\r");
        application.append("import org.springframework.boot.autoconfigure.SpringBootApplication\n\r");
        application.append("import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration\n\r");
        application.append("import org.springframework.boot.web.servlet.ServletComponentScan\n\r");
        application.append("import org.springframework.cache.annotation.EnableCaching\n\r");
        application.append("import org.springframework.context.annotation.EnableAspectJAutoProxy\n\r");
        application.append("import org.springframework.context.annotation.Import\n\r");
        application.append("import org.springframework.scheduling.annotation.EnableScheduling\n\r");
        application.append("import org.springframework.transaction.annotation.EnableTransactionManagement\n\r");
        application.append("import org.springframework.web.bind.annotation.RestController\n\r");
        application.append("\n\r");
        application.append("/**\n\r");
        application.append(" * 启动类\n\r");
        application.append(" * @author suphowe\n\r");
        application.append(" */\n\r");
        application.append("//启动类导入主配置类\n\r");
        application.append("@Import(SpringConfiguration.class)\n\r");
        application.append("//排除Mongo自动注入\n\r");
        application.append("@SpringBootApplication(exclude = MongoAutoConfiguration.class)\n\r");
        application.append("@EnableAspectJAutoProxy\n\r");
        application.append("@RestController\n\r");
        application.append("//开启事务\n\r");
        application.append("@EnableTransactionManagement\n\r");
        application.append("//开启定时任务\n\r");
        application.append("@EnableScheduling\n\r");
        application.append("//开启redis\n\r");
        application.append("@EnableCaching\n\r");
        application.append("@MapperScan(\"com.soft.dao\")\n\r");
        application.append("//扫描自定义过滤器\n\r");
        application.append("@ServletComponentScan\n\r");
        application.append("public class SoftApplication{\n\r");
        application.append("\n\r");
        application.append("    private static final Logger logger = LoggerFactory.getLogger(com.soft.SoftApplication.class);\n\r");
        application.append("\n\r");
        application.append("    public static void main(String[] args) {\n\r");
        application.append("        logger.info(\"===>主线程\");\n\r");
        application.append("        SpringApplication.run(com.soft.SoftApplication.class, args);\n\r");
        application.append("    }\n\r");
        application.append("}");
        return application.toString();
    }

    /**
     * config文件
     */
    private static String createSpringConfiguration() {
        StringBuilder springConfiguration = new StringBuilder();
        springConfiguration.append("package com.soft.sys\n\r");
        springConfiguration.append("\n\r");
        springConfiguration.append("import org.springframework.beans.factory.annotation.Configurable\n\r");
        springConfiguration.append("import org.springframework.context.annotation.ComponentScan\n\r");
        springConfiguration.append("import org.springframework.context.annotation.Import\n\r");
        springConfiguration.append("\n\r");
        springConfiguration.append("/**\n\r");
        springConfiguration.append(" * 主配置类\n\r");
        springConfiguration.append(" * @author suphowe\n\r");
        springConfiguration.append(" */\n\r");
        springConfiguration.append("@Configurable\n\r");
        springConfiguration.append("//主配置类导入从配置类,数据库配置\n\r");
        springConfiguration.append("@Import(DataSourceConfig.class)\n\r");
        springConfiguration.append("//指定扫描组件的包路径,可以减少扫描的时间\n\r");
        springConfiguration.append("@ComponentScan({\"com.soft\"})\n\r");
        springConfiguration.append("public class SpringConfiguration {\n\r");
        springConfiguration.append("}");
        return springConfiguration.toString();
    }

    /**
     * 数据库配置文件
     */
    private static String createDataSourceConfig() {
        StringBuilder dataSourceConfig = new StringBuilder();
        dataSourceConfig.append("package com.soft.sys\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("import com.alibaba.druid.pool.DruidDataSource\n\r");
        dataSourceConfig.append("import org.springframework.beans.factory.annotation.Autowired\n\r");
        dataSourceConfig.append("import org.springframework.beans.factory.annotation.Qualifier\n\r");
        dataSourceConfig.append("import org.springframework.context.annotation.Bean\n\r");
        dataSourceConfig.append("import org.springframework.context.annotation.Configuration\n\r");
        dataSourceConfig.append("import org.springframework.context.annotation.Primary\n\r");
        dataSourceConfig.append("import org.springframework.core.env.Environment\n\r");
        dataSourceConfig.append("import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("import javax.sql.DataSource\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("/**\n\r");
        dataSourceConfig.append("* 从配置类:多数据源配置\n\r");
        dataSourceConfig.append("* @author suphowe\n\r");
        dataSourceConfig.append("**/\n\r");
        dataSourceConfig.append("@Configuration\n\r");
        dataSourceConfig.append("public class DataSourceConfig {\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    @Autowired\n\r");
        dataSourceConfig.append("    private Environment env;\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    /**\n\r");
        dataSourceConfig.append("      * mysql主数据源\n\r");
        dataSourceConfig.append("      * @return DataSource\n\r");
        dataSourceConfig.append("      */\n\r");
        dataSourceConfig.append("    @Bean(name = \"primaryDataSource\")\n\r");
        dataSourceConfig.append("    @Qualifier(\"primaryDataSource\")\n\r");
        dataSourceConfig.append("    @Primary\n\r");
        dataSourceConfig.append("    public DataSource getDataSource() {\n\r");
        dataSourceConfig.append("        DruidDataSource dataSource = new DruidDataSource();\n\r");
        dataSourceConfig.append("        dataSource.setDriverClassName(env.getProperty(\"spring.primary.datasource.driver-class-name\"));\n\r");
        dataSourceConfig.append("        dataSource.setUrl(env.getProperty(\"spring.primary.datasource.url\"));\n\r");
        dataSourceConfig.append("        dataSource.setUsername(env.getProperty(\"spring.primary.datasource.username\"));\n\r");
        dataSourceConfig.append("        dataSource.setPassword(env.getProperty(\"spring.primary.datasource.password\"));\n\r");
        dataSourceConfig.append("        return dataSource;\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    /**\n\r");
        dataSourceConfig.append("      * mysql第二数据源\n\r");
        dataSourceConfig.append("      * @return DataSource\n\r");
        dataSourceConfig.append("      */\n\r");
        dataSourceConfig.append("    @Bean(name = \"secondDataSource\")\n\r");
        dataSourceConfig.append("    @Qualifier(\"secondDataSource\")\n\r");
        dataSourceConfig.append("    public DataSource getDataSourceLocal() {\n\r");
        dataSourceConfig.append("        DruidDataSource dataSource = new DruidDataSource();\n\r");
        dataSourceConfig.append("        dataSource.setDriverClassName(env.getProperty(\"spring.local.datasource.driver-class-name\"));\n\r");
        dataSourceConfig.append("        dataSource.setUrl(env.getProperty(\"spring.local.datasource.url\"));\n\r");
        dataSourceConfig.append("        dataSource.setUsername(env.getProperty(\"spring.local.datasource.username\"));\n\r");
        dataSourceConfig.append("        dataSource.setPassword(env.getProperty(\"spring.local.datasource.password\"));\n\r");
        dataSourceConfig.append("        return dataSource;\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    /**\n\r");
        dataSourceConfig.append("      * postgresql数据源\n\r");
        dataSourceConfig.append("      * @return DataSource\n\r");
        dataSourceConfig.append("      */\n\r");
        dataSourceConfig.append("    @Bean(name = \"postgresqlDataSource\")\n\r");
        dataSourceConfig.append("    @Qualifier(\"postgresqlDataSource\")\n\r");
        dataSourceConfig.append("    public DataSource getPostgresqlData() {\n\r");
        dataSourceConfig.append("        DruidDataSource dataSource = new DruidDataSource();\n\r");
        dataSourceConfig.append("        dataSource.setDriverClassName(env.getProperty(\"spring.postgresql.datasource.driver-class-name\"));\n\r");
        dataSourceConfig.append("        dataSource.setUrl(env.getProperty(\"spring.postgresql.datasource.url\"));\n\r");
        dataSourceConfig.append("        dataSource.setUsername(env.getProperty(\"spring.postgresql.datasource.username\"));\n\r");
        dataSourceConfig.append("        dataSource.setPassword(env.getProperty(\"spring.postgresql.datasource.password\"));\n\r");
        dataSourceConfig.append("        return dataSource;\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    @Bean(name = \"primaryNamedParameterJdbcTemplate\")\n\r");
        dataSourceConfig.append("    public NamedParameterJdbcTemplate primaryJdbcTemplate(\n\r");
        dataSourceConfig.append("            @Qualifier(\"primaryDataSource\") DataSource dataSource) {\n\r");
        dataSourceConfig.append("        return new NamedParameterJdbcTemplate(dataSource);\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    @Bean(name = \"secondNamedParameterJdbcTemplate\")\n\r");
        dataSourceConfig.append("    public NamedParameterJdbcTemplate secondaryJdbcTemplate(\n\r");
        dataSourceConfig.append("            @Qualifier(\"secondDataSource\") DataSource dataSource) {\n\r");
        dataSourceConfig.append("        return new NamedParameterJdbcTemplate(dataSource);\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("\n\r");
        dataSourceConfig.append("    @Bean(name = \"postgresqlParameterJdbcTemplate\")\n\r");
        dataSourceConfig.append("    public NamedParameterJdbcTemplate postgresqlJdbcTemplate(\n\r");
        dataSourceConfig.append("            @Qualifier(\"postgresqlDataSource\") DataSource dataSource) {\n\r");
        dataSourceConfig.append("        return new NamedParameterJdbcTemplate(dataSource);\n\r");
        dataSourceConfig.append("    }\n\r");
        dataSourceConfig.append("}");
        return dataSourceConfig.toString();
    }
}

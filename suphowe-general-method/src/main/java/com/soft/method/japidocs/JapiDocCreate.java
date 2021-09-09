package com.soft.method.japidocs;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * JApiDocs 使用
 *
 * @author suphowe
 */
public class JapiDocCreate {

    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        // 项目根目录
        config.setProjectPath("C:/suphowe/gitee/suphowe-tools/suphowe-general-method");
        // 项目名称
        config.setProjectName("suphowe-general-method");
        // 声明该API的版本
        config.setApiVersion("v1.0");
        // 生成API 文档所在目录
        config.setDocsPath("C:/test");
        // 配置自动生成
        config.setAutoGenerate(Boolean.TRUE);
        // 执行生成文档
        Docs.buildHtmlDocs(config);
    }
}

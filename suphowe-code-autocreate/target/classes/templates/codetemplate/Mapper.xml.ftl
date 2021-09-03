<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${mapperName}">

    <resultMap id="BaseResultMap" type="${packageName}.model.${modelName}">
        <#list columns as column>
            <<#if column.primary??>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName?uncap_first}" jdbcType="<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='TEXT'>VARCHAR<#else>${column.type}</#if>" />
        </#list>
    </resultMap>

    <select id="get${modelName}s" resultMap="BaseResultMap">
        select * from ${tableName}
        <#if columns??>
        <where>
            <#list columns as column>
            <if test="${column.propertyName?uncap_first} != null" >
                and ${column.propertyName?lower_case}=<#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
        </#if>
    </select>

    <insert id="insert${modelName}" parameterType="${packageName}.model.${modelName}">
        insert into ${tableName} (
        <#list columns as column>
            <#if column_has_next>
                ${column.propertyName?lower_case} ,
            <#else >
                ${column.propertyName?lower_case}
            </#if>
        </#list>
        ) values (
        <#list columns as column>
            <#if column_has_next>
                <#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>}, </#noparse>
            <#else >
                <#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>} </#noparse>
            </#if>
        </#list>
        )
    </insert>

    <!-- set 元素可以用于动态包含需要更新的列，忽略其它不更新的列 -->
    <update id="update${modelName}" parameterType="${packageName}.model.${modelName}">
        update ${tableName}
        <set>
            <#list columns as column>
                <#if column_has_next>
                    <if test="${column.propertyName?uncap_first} != null" >${column.propertyName?lower_case}=<#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>}, </#noparse></if>
                <#else >
                    <if test="${column.propertyName?uncap_first} != null" >${column.propertyName?lower_case}=<#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>} </#noparse></if>
                </#if>
            </#list>
        </set>
    </update>

    <delete id="delete${modelName}" parameterType="${packageName}.model.${modelName}">
        delete from ${tableName}
        <#if columns??>
        <where>
            <#list columns as column>
                <if test="${column.propertyName?uncap_first} != null" >
                    and ${column.propertyName?lower_case}=<#noparse>#{</#noparse>${column.propertyName?uncap_first}<#noparse>}</#noparse>
                </if>
            </#list>
        </where>
        </#if>
    </delete>

</mapper>
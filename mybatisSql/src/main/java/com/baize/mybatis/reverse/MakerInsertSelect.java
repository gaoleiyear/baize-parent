package com.baize.mybatis.reverse;

import com.baize.mybatis.reverse.bean.Table;
import com.baize.mybatis.reverse.util.CodeUtil;
import com.baize.mybatis.reverse.util.DBUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MakerInsertSelect extends Maker {


    @Override
    public void make(String basePackage, String outputRoot, String modular, Map<String, List<String>> groupMap, String tablePrefix, String tablePostfix) {
        String outputRootApi = outputRoot + modular + File.separator + modular + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java";
//        String outputRootProvider = outputRoot + modular + File.separator + modular + "-service" + File.separator + "src" + File.separator + "main" + File.separator + "java";
        String outputRootProvider = outputRoot + modular + File.separator + modular + "-provider" + File.separator + "src" + File.separator + "main" + File.separator + "java";
        for (Entry<String, List<String>> entry : groupMap.entrySet()) {
            String group = entry.getKey();
            List<String> groupList = entry.getValue();
            for (String tableName : groupList) {
                Table table = DBUtil.getTable(tableName);
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("util", new CodeUtil());
                data.put("table", table);
                data.put("tableName", tableName.substring(0, tableName.length() - tablePostfix.length()));

//				dto
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "dto", "Dto", outputRootApi, "dto.vm");

//				entity
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "entity", "Entity", outputRootProvider, "entity.vm");

//				param
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "param", "QueryParam", outputRootApi, "param.vm");

//				service
//                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "service", "Service", outputRootApi, "service.vm");
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "service", "Service", outputRootProvider, "service.vm");

//				dao
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "dao", "Mapper", outputRootProvider, "dao.vm");

//				ServiceImpl
                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "service.impl", "ServiceImpl", outputRootProvider, "service.impl.vm");

                makeJava(data, basePackage, group, tableName, tablePrefix, tablePostfix, "controller", "Controller", outputRootProvider, "controller.vm");

//				xml
                makeFile(data, "xml-insert-select.vm", outputRootProvider + File.separator + ".." + File.separator + "resources" + File.separator + "mybatis", CodeUtil.convertClassNameToPath((String) data.get("daoName"), "xml"));
            }
        }
    }
}


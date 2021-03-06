/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apdplat.module.module.action;

import com.apdplat.module.module.model.Module;
import com.apdplat.module.module.service.ModuleService;
import com.apdplat.platform.action.ExtJSSimpleAction;
import com.apdplat.platform.util.Struts2Utils;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
* 维护树形模块，对应于module.xml文件
 * 在module.xml中的数据未导入到数据库之前，可以通过修改module.xml文件的形式修改树形模块
 * 在module.xml中的数据导入到数据库之后，就只能在浏览器网页中对树形模块进行修改
 *
 * 修改模块
* @author 杨尚川
*/
@Controller
@Scope("prototype")
@Namespace("/module")
public class EditModuleAction extends ExtJSSimpleAction<Module> {
        @Resource(name="moduleService")
        private ModuleService moduleService;
        private String node;
        @Override
        public String query(){
            if(node==null){
                return super.query();
            }
            if(node.trim().startsWith("root")){
                String json=moduleService.toRootJsonForEdit();
                Struts2Utils.renderJson(json);
                return null;
            }
            
            if(node.contains("-")){
                try{
                    String[] temp=node.split("-");
                    int id=Integer.parseInt(temp[1]);
                    Module module=moduleService.getModule(id);
                    String json=moduleService.toJsonForEdit(module);
                    Struts2Utils.renderJson(json);
                }catch(Exception e){
                    log.error("获取根模块出错",e);
                }
            }
            
            return null;
        }

        public void setNode(String node) {
            this.node = node;
        }
}
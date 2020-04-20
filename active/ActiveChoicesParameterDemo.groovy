    if (type == "sync") {
        properties([
            parameters([
                [$class: 'ChoiceParameter', 
                    choiceType: 'PT_SINGLE_SELECT', 
                    description: '请选择CODO的项目标签',  
                    name: 'PROJECT', 
                    randomName: 'choice-parameter-1', 
                    script: [
                        $class: 'GroovyScript', 
                        fallbackScript: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                'return[\'刷新网页\']'
                        ], 
                        script: [
                            classpath: [], 
                            sandbox: false,
                            script: "return ['${map.PROJECT}']"
                        ]
                    ]
                ], 
                [$class: 'CascadeChoiceParameter', 
                    choiceType: 'PT_SINGLE_SELECT', 
                    description: '请选择CODO的ENV标签',
                    // filterLength: 1, 
                    // filterable: true, 
                    name: 'ENVIRO', 
                    randomName: 'choice-parameter-2', 
                    referencedParameters: 'PROJECT', 
                    script: [
                        $class: 'GroovyScript', 
                        fallbackScript: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                'return[\'没有选择任何的项目\']'
                        ], 
                        script: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                '''import groovy.json.*
                                def Get_env(project_name) {
                                    def token = ".."
                                    def conn ="http://demo.ftsview.com/api/kerrigan/v1/conf/tree/?"
                                    def param ="project_code=" + project_name + "&auth_key=" + token
                                    conn+=param
                                    def parser = new JsonSlurper()
                                    def get = new URL(conn).openConnection();
                                    def getRC = get.getResponseCode();
                                    if(getRC.equals(200)) {
                                        res=get.getInputStream().getText();
                                        def json = parser.parseText(res)
                                        for (project in json.get("data")) {
                                        if (project["title"].contains(project_name)){
                                            return (project["children"]["title"])
                                            }
                                        }
                                    }
                                }
                                Get_env(PROJECT)
                                '''
                        ]
                    ]
                ],
                [$class: 'CascadeChoiceParameter', 
                    choiceType: 'PT_SINGLE_SELECT', 
                    description: '请选择CODO的HOSTNAME标签', 
                    name: 'HOSTNAME',
                    // filterLength: 1, 
                    // filterable: true, 
                    randomName: 'choice-parameter-3', 
                    referencedParameters: 'PROJECT,ENVIRO',
                    script: [
                        $class: 'GroovyScript', 
                        fallbackScript: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                'return[\'没有选择任何的项目\']'
                        ], 
                        script: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                '''import groovy.json.*
                                def GetServer(project_name,env_name){
                                    def token = ".."
                                    def conn ="http://demo.ftsview.com/api/kerrigan/v1/conf/tree/?"
                                    def param ="project_code=" + project_name + "&auth_key=" + token
                                    conn+=param
                                    def parser = new JsonSlurper()
                                    def get = new URL(conn).openConnection();
                                    def getRC = get.getResponseCode();
                                    if(getRC.equals(200)) {
                                        res=get.getInputStream().getText();
                                        def json = parser.parseText(res)
                                        for (project in json.get("data")) {
                                            if (project["title"].contains(project_name)){
                                                def env_json = project["children"]
                                                for (env_ver in env_json){
                                                    if (env_ver["title"].equals(env_name)){
                                                        def hostname_json = env_ver
                                                        return hostname_json["children"]["title"]
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                GetServer(PROJECT,ENVIRO)
                                '''
                        ]
                    ]
                ],
                [$class: 'CascadeChoiceParameter', 
                    choiceType: 'PT_RADIO', 
                    description: '请选择CODO的FILE标签', 
                    name: 'FILENAME', 
                    randomName: 'choice-parameter-4', 
                    referencedParameters: 'PROJECT,ENVIRO,HOSTNAME', 
                    script: [
                        $class: 'GroovyScript', 
                        fallbackScript: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                'return[\'没有选择任何的项目\']'
                        ], 
                        script: [
                            classpath: [], 
                            sandbox: false, 
                            script: 
                                '''import groovy.json.*
                                def Get_File(project_name,env_name,server_name){
                                    def token = ".."
                                    def conn ="http://demo.ftsview.com/api/kerrigan/v1/conf/tree/?"
                                    def param ="project_code=" + project_name + "&auth_key=" + token
                                    conn+=param
                                    def parser = new JsonSlurper()
                                    def get = new URL(conn).openConnection();
                                    def getRC = get.getResponseCode();
                                    if(getRC.equals(200)) {
                                        res=get.getInputStream().getText();
                                        def json = parser.parseText(res)
                                        for (project in json.get("data")) {
                                            if (project["title"].contains(project_name)){
                                                def env_json = project["children"]
                                                for (env_ver in env_json){
                                                    if (env_ver["title"].equals(env_name)) {
                                                        def hostname_json = env_ver["children"]
                                                        for (hostname_ver in hostname_json) {
                                                            if (hostname_ver["title"].equals(server_name)){
                                                                def filename_json = hostname_ver["children"]
                                                                return filename_json["title"]
                                                            }
                                                        }        
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Get_File(PROJECT,ENVIRO,HOSTNAME)
                                '''
                        ]
                    ]
                ]
            ])
        ])
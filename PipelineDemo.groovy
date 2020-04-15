
#!groovy
pipeline{
    // 定义使用构建的jenkins 主机
    agetnt{
        // 在任何可用的节点上执行Pipeline
        // any {}
        // 当在 pipeline 块的顶部没有全局代理， 该参数将会被分配到整个流水线的运行中并且每个 stage 部分都需要包含他自己的 agent 部分
        // none {}
        // 在提供了标签的 Jenkins 环境中可用的代理上执行流水线或阶段
        // label {}
        // 与label相同，不过可以附加而外的参数
        // node {}
        // 使用docker的方式执行pipeline
        // docker {}
        // 使用dockerfile 构建出一个docker 并使用该docker执行pipeline
        // dockerfile {}
    }

    post{
        // 无论流水线或阶段的完成状态如何，都允许在 post 部分运行该步骤
        always{}
        
        // 只有当前流水线或阶段的完成状态与它之前的运行不同时，才允许在 post 部分运行该步骤
        changed {}
        
        // 只有当前流水线或阶段的完成状态为"failure"，才允许在 post 部分运行该步骤, 通常web UI是红色
        failure {}
        
        // 只有当前流水线或阶段的完成状态为"success"，才允许在 post 部分运行该步骤, 通常web UI是蓝色或绿色
        success {}

        // 只有当前流水线或阶段的完成状态为"unstable"，才允许在 post 部分运行该步骤, 通常由于测试失败,代码违规等造成。通常web UI是黄色
        unstable {}

        // 只有当前流水线或阶段的完成状态为"aborted"，才允许在 post 部分运行该步骤, 通常由于流水线被手动的aborted。通常web UI是
        aborted {}
    }

    // 包含一个或多个 stage 指令, stages 部分是流水线描述的大部分"work" 的位置。 建议 stages 至少包含一个 stage 指令用于连续交付过程,比如构建, 测试, 和部署
    stages{
        // 可选，在构建中设置一个对话框进行暂停，退出，审批等操作
        input{}
        // 可选，指令允许流水线根据给定的条件决定是否应该执行阶段
        when{
            // 当正在构建的分支与模式给定的分支匹配时，执行这个阶段, 例如: when { branch 'master' }。注意，这只适用于多分支流水线。
            branch

            // 当指定的环境变量是给定的值时，执行这个步骤, 例如: when { environment name: 'DEPLOY_TO', value: 'production' }
            environment

            // 当指定的Groovy表达式评估为true时，执行这个阶段, 例如: when { expression { return params.DEBUG_BUILD } }
            expression

            // 当嵌套条件是错误时，执行这个阶段,必须包含一个条件，例如: when { not { branch 'master' } }
            not

            // 当所有的嵌套条件都正确时，执行这个阶段,必须包含至少一个条件，例如: when { allOf { branch 'master'; environment name: 'DEPLOY_TO', value: 'production' } }
            allOf

            // 当至少有一个嵌套条件为真时，执行这个阶段,必须包含至少一个条件，例如: when { anyOf { branch 'master'; branch 'staging' } }
            anyOf
        }
        // stage 指令在 stages 中
        stage{
            // 在声明式中使用脚本式的写法
            script {
                //steps 部分在给定的 stage 是组成声明式最小的单元
                steps('Example') {
                    echo 'Hello World'
                }
            }

        }
        //并行
        parallel {
             stage("A"){
                echo "A"
             }
             stage("B"){
                echo "B"
             }
             stage("C"){
                echo "C"
             }
        }
    }
    // kv的方式配置pipeline的环境变量
    environment{
        username: "zhangsan"
    }
    // jenkins的一些系统配置
    options{
        // 可用选项
        // 为最近的流水线运行的特定数量保存组件和控制台输出。例如: options { buildDiscarder(logRotator(numToKeepStr: '1')) }
        buildDiscarder

        // 不允许同时执行流水线。 可被用来防止同时访问共享资源等。 例如: options { disableConcurrentBuilds() }
        disableConcurrentBuilds

        // 允许覆盖分支索引触发器的默认处理。 如果分支索引触发器在多分支或组织标签中禁用, options { overrideIndexTriggers(true) } 将只允许它们用于促工作。否则, options { overrideIndexTriggers(false) } 只会禁用改作业的分支索引触发器。
        overrideIndexTriggers

        // 在`agent` 指令中，跳过从源代码控制中检出代码的默认情况。例如: options { skipDefaultCheckout() }
        skipDefaultCheckout

        // 一旦构建状态变得UNSTABLE，跳过该阶段。例如: options { skipStagesAfterUnstable() }
        skipStagesAfterUnstable

        // 在工作空间的子目录中自动地执行源代码控制检出。例如: options { checkoutToSubdirectory('foo') }
        checkoutToSubdirectory

        // 设置流水线运行的超时时间, 在此之后，Jenkins将中止流水线。例如: options { timeout(time: 1, unit: 'HOURS') }
        timeout

        // 在失败时, 重新尝试整个流水线的指定次数。 For example: options { retry(3) }
        retry

        // 预谋所有由流水线生成的控制台输出，与该流水线发出的时间一致。 例如: options { timestamps() }
        timestamps
    }
    // 把参数化构建封装到pipeline中
    parameters {
        // 具体写法使用jenkins的代码生成器实现就行
    }
    // 定义一些触发器
    triggers {
        // 具体写法使用jenkins的代码生成器实现就行
    }
    // 在jenkins配置的一些工具的写法
    tools {
        // 具体写法使用jenkins的代码生成器实现就行
    }
}
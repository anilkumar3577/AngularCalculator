def call() {
  pipeline {
    agent any
    tools {
        git 'Default'
        dockerTool 'docker'
        maven 'mvn'
        nodejs 'node'
    }
    stages{
        stage(package){
            steps {
               echo 'npm install'
            }
        }

        stage(build){
            steps {
              sh 'docker build -t share .' 
            }
        }
        stage(push){
            steps {
            withCredentials([usernamePassword(credentialsId: 'dock', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
 sh ''' docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
 docker tag share ${DOCKER_USERNAME}/share
        docker push ${DOCKER_USERNAME}/share '''
             }

            }
        }
    }
}
}

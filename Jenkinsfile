  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn clean verify sonar:sonar -Dsonar.host.url=http://127.0.0.1:9000'
            }
        }  
      }
}

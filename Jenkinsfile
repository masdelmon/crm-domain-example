  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sudo su mvn verify sonar:sonar'
            }
        }  
      }
}

  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sudo mvn verify sonar:sonar'
            }
        }  
      }
}

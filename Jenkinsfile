  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn verify sonar:sonar'
            }
        }  
      }
}

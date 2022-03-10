  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar  -Dsonargraph.installationDirectory=/home/vagrant/SonargraphBuild-12.0.5.717_2022-02-25 -Dsonargraph.licenseFile=/home/vagrant/Sonargraph.license -Dsonar.host.url=http://127.0.0.1:9000'
            }
        }  
      }
}

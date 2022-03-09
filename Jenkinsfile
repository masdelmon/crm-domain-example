  pipeline {
    
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:3.0:sonar  -Dsonargraph.installationDirectory=/home/vagrantSonargraphBuild-12.0.5.717_2022-02-25 -Dsonargraph.licenseFile=/home/vagrant\Sonargraph.license -Dsonar.host.url=http://127.0.0.1:9000 -Dsonar.sonargraph.integration:report.path=-Dsonar.sonargraph.integration:report.path=/var/lib/jenkins/workspace/demo_master/target/sonargraph/sonargraph-sonarqube-report.xml'
            }
        }  
      }
}

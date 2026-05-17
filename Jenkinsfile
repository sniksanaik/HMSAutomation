pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/sniksanaik/HMSAutomation.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -f pom.xml'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test -Dsurefire.suiteXmlFiles=testng.xml -f pom.xml'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML(target: [
                    allowMissing         : false,
                    alwaysLinkToLastBuild: true,
                    keepAll              : true,
                    reportDir            : 'target/surefire-reports',
                    reportFiles          : 'index.html',
                    reportName           : 'TestNG Report'
                ])
            }
        }
    }

    post {
        always {
            testNG()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}

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
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -Dsurefire.suiteXmlFiles=testng.xml'
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

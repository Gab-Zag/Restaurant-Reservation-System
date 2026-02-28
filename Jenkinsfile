pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'jdk-21', type: 'jdk'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean install -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd clean test site'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/site/**', fingerprint: true
                }
            }
        }

    }
}
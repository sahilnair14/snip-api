pipeline {

    // Which Jenkins Agent should execute this pipeline?
    agent {
        label 'Tester'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'

                checkout scm
            }
        }

        stage('Environment Check') {
            steps {
                echo 'Checking environment...'

                sh '''
                    echo "Hostname:"
                    hostname

                    echo "Current User:"
                    whoami

                    echo "Java Version:"
                    java -version

                    echo "Node Version:"
                    node --version

                    echo "NPM Version:"
                    npm --version
                '''
            }
        }

        stage('Install Dependencies') {
            steps {
                echo 'Installing dependencies...'

                sh 'npm install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'

                sh 'npm test'
            }
        }

        stage('Build') {
            steps {
                echo 'Building application...'

                sh 'npm run build'
            }
        }
    }

    post {

        success {
            echo '===================================='
            echo 'BUILD SUCCESSFUL!'
            echo '===================================='
        }

        failure {
            echo '===================================='
            echo 'BUILD FAILED!'
            echo 'Check the console output.'
            echo '===================================='
        }

        always {
            echo 'Pipeline execution completed.'
        }
    }
}

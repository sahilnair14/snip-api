pipeline {
    // Which Jenkins Agent should execute this pipeline?
    agent {
        label 'Tester'
    }

    environment {
        // Customize these as per your project
        GIT_REPO      = 'https://github.com/sahilnair14/snip-api.git'
        GIT_BRANCH    = 'main'
        IMAGE_NAME    = 'snip-api'
        IMAGE_TAG     = "${env.BUILD_NUMBER}"
        CONTAINER_NAME = 'snip-demo'
        APP_PORT      = '8080'
        HOST_PORT     = '8082'
        SCANNER_HOME = tool 'sonarqube-scanner'
    }

    stages {
        stage('Git Clone') {
            steps {
                echo 'Cloning source code from Git...'
                git branch: "${GIT_BRANCH}", url: "${GIT_REPO}"
                // If using checkout scm (works when pipeline is loaded from SCM):
                // checkout scm
            }
        }

        stage('Build for Sonar') {
            steps {
                echo 'Compiling project before analysis...'
                sh './mvnw clean compile'
            }
        }

        stage('SonarQube Analysis') {
        steps {
            withSonarQubeEnv('sonarqube') {
                sh '''
                    ${SCANNER_HOME}/bin/sonar-scanner \
                    -Dsonar.projectKey=sonarqube-project \
                    -Dsonar.sources=. \
                    -Dsonar.java.binaries=target/classes
                '''
            }
        }
    }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                sh "docker bully -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:latest"
            }
        }

        stage('Trivy Scan') {
            steps {
                echo 'Scanning Docker image for vulnerabilities using Trivy container...'
                sh """
                    docker run --rm \
                    -v /var/run/docker.sock:/var/run/docker.sock \
                    -v \$(pwd):/report \
                    aquasec/trivy:latest image \
                    --severity HIGH,CRITICAL \
                    --exit-code 0 \
                    --format template \
                    --template "@/contrib/html.tpl" \
                    -o /report/trivy-report.html \
                    ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
            post {
                always {
                    archiveArtifacts artifacts: 'trivy-report.html', allowEmptyArchive: true
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                echo 'Deploying Docker container...'
                sh '''
                    # Stop and remove existing container if running
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                '''
                sh "docker run -d --name ${CONTAINER_NAME} -p ${HOST_PORT}:${APP_PORT} ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo '===================================='
            echo 'BUILD & DEPLOY SUCCESSFUL!'
            echo '===================================='
            echo 'TRIGGERING UI PIPELINE'
            echo '===================================='
            build job: 'snip-ui-pipeline', wait: false
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

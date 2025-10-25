This project is created to demonstrate a CI-CD pipeline using Jenkins . On every commit to the repository, the pipeline runs tests, compiles the code, and packages it.
The packaged code is built into a Docker image and deployed as a container on an AWS EC2 instance. Docker is used here in case if we want to push the built image to docker hub or Amazon ECS .
Demonstrates the full CI/CD workflow, including automated testing, containerization, and continuous deployment.

<img width="1882" height="405" alt="Screenshot 2025-10-25 142644" src="https://github.com/user-attachments/assets/d7f8a486-21ae-46b7-9223-5b479a9c1704" />



Pipeline Syntax:
pipeline {
    agent any

    environment {
        APP_NAME = "calculator"
        PROJECT_DIR = "/home/ubuntu/calculator_ci-cd"
        TARGET_JAR = "calculator-0.0.1-SNAPSHOT.jar"
        IMAGE_NAME = "calculator:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'main', url: 'https://github.com/ironfist-15/calculator_ci-cd'
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                sh 'mvn test'   // shows test output directly in console
            }
        }

        stage('Build Jar') {
            steps {
                echo "Building application..."
                sh 'mvn clean package -DskipTests=false'
            }
        }

        stage('Copy Jar to Project') {
            steps {
                echo "Copying jar to project root..."
                sh 'sudo cp -f target/*.jar ${PROJECT_DIR}/target/'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh '''
                    docker image prune -f
                    docker build -t ${IMAGE_NAME} .
                '''
            }
        }

        stage('Restart Application') {
            steps {
                echo "Restarting application via systemd..."
                sh 'sudo systemctl restart docker_calculator.service'
            }
        }
    }

    post {
        success {
            echo " Deployment successful! App is running on port 8081."
        }
        failure {
            echo " Build or deployment failed!"
        }
    }
}


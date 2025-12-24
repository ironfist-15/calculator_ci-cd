This project is created to demonstrate a CI-CD pipeline using Jenkins . On every commit to the repository, the pipeline runs tests, compiles the code, and packages it.
The packaged code is built into a Docker image and deployed as a container on an AWS EC2 instance. Docker is used here in case if we want to push the built image to docker hub or Amazon ECR .
Demonstrates the full CI/CD workflow, including automated testing, containerization, and continuous deployment.

<img width="1853" height="459" alt="Screenshot 2025-12-25 015522" src="https://github.com/user-attachments/assets/b2d18a7f-f192-4569-8814-a1f7eec14ca8" />



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
                sh 'mvn test'  
            }
        }
        
        stage('Build JAR') {
            steps {
                echo "Building JAR..."
                sh 'mvn clean package -DskipTests'
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

DockerFile:

   
    FROM eclipse-temurin:17-jre-jammy
    
    WORKDIR /app
    
    COPY target/calculator-0.0.1-SNAPSHOT.jar app.jar
    
    EXPOSE 8081
    
    ENTRYPOINT ["java", "-jar", "app.jar"]

Service file:-

    GNU nano 7.2                                                 docker_calculator.service
    [Unit]
    Description=Spring Boot Calculator Docker Service
    After=docker.service
    Requires=docker.service
    
    [Service]
    Type=simple
    User=ubuntu
    Group=docker
    WorkingDirectory=/home/ubuntu/calculator_ci-cd
    
    
    ExecStartPre=/bin/sh -c '/usr/bin/docker stop calculator-app || true'
    ExecStartPre=/bin/sh -c '/usr/bin/docker rm calculator-app || true'
    ExecStart=/usr/bin/docker run --rm -p 8081:8081 --name calculator-app calculator:latest
    ExecStop=/usr/bin/docker stop calculator-app
    
    Restart=always
    RestartSec=5
    
    [Install]
    WantedBy=multi-user.target



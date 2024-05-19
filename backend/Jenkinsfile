pipeline {
    agent any
   
    stages {
        stage('Checkout Branch') {
            steps {
                git branch: 'master', url: 'https://github.com/Subho27/OTMS-Backend.git'
            }
        }
       
        stage('Build Project') {
            steps {
                sh 'mvn clean compile'
            }
        }
       
        stage('Unit Testing') {
            steps {
                sh 'mvn clean test'
            }
        }
       
        stage('Containerize Application') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker build -t $DOCKER_USERNAME/otms-backend .'
                }
            }
        }
       
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    sh 'docker push $DOCKER_USERNAME/otms-backend'
                }
            }
        }
       
        stage('Deployment') {
            steps {
                sh '/usr/bin/ansible-playbook -i inventory/inventory.yml playbooks/deploy_docker.yml'
            }
        }
     }
}

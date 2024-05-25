pipeline {
    agent any
   
    stages {
        stage('Checkout Branch') {
            steps {
                git branch: 'master', url: 'https://github.com/Subho27/OTMS.git'
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'CI=false npm run build'
                }
            }
        }
        
        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean compile'
                }
            }
        }
       
        stage('Unit Testing') {
            steps {
                dir('backend') {
                    sh 'mvn clean test'
                }
            }
        }
       
        stage('Containerize Frontend Application') {
            steps {
                dir('frontend') {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker build -t $DOCKER_USERNAME/ui-otms:0.0.1 . --no-cache'
                    }
                }
            }
        }

        stage('Containerize Backend Application') {
            steps {
                dir('backend') {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker build -t $DOCKER_USERNAME/api-otms:0.0.1 . --no-cache'
                    }
                }
            }
        }
       
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    sh 'docker push $DOCKER_USERNAME/ui-otms:0.0.1'
                    sh 'docker push $DOCKER_USERNAME/api-otms:0.0.1'
                }
            }
        }

        stage('Deployment') {
            steps {
                sh '/usr/bin/ansible-playbook -i inventory/inventory.yml playbooks/deploy_otms_kube.yml'
            }
        }

    }
}

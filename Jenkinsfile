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
       
        // stage('Unit Testing') {
        //     steps {
        //         dir('backend') {
        //             sh 'mvn clean test'
        //         }
        //     }
        // }
       
        stage('Containerize Whole Application') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker-compose -f docker-compose.yml build'
                    sh 'docker tag otms-frontend:latest $DOCKER_USERNAME/otms-all-frontend'
                    sh 'docker tag otms-backend:latest $DOCKER_USERNAME/otms-all-backend'
                }
            }
        }
       
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    sh 'docker push $DOCKER_USERNAME/otms-all-frontend'
                    sh 'docker push $DOCKER_USERNAME/otms-all-backend'
                }
            }
        }
       
        stage('Deployment') {
            steps {
                sh '/usr/bin/ansible-playbook -i inventory/inventory.yml playbooks/deploy_otms.yml'
            }
        }
     }
}

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
       
        // stage('Deployment Configurations Setup') {
        //     steps {
        //         dir('k8s-deploy') {
        //             sh 'kubectl apply -f db-config.yml --validate=false'
        //             sh 'kubectl apply -f backend-config.yml --validate=false'
        //         }
        //     }
        // }

        // stage('Database Deployment') {
        //     steps {
        //         dir('k8s-deploy') {
        //             sh 'kubectl apply -f db-deployment-service.yml'
        //         }
        //     }
        // }

        // stage('Waiting for Database Service to be up & running') {
        //     steps {
        //         script {
        //             kubernetesResource(
        //                 apiVersion: 'v1',
        //                 kind: 'Service',
        //                 name: 'otms-db-service',
        //                 namespace: 'default',
        //                 waitForCondition: 'Ready',
        //                 timeout: 5
        //             )
        //         }
        //     }
        // }

        // stage('Backend Deployment') {
        //     steps {
        //         dir('k8s-deploy') {
        //             sh 'kubectl apply -f backend-deployment-service.yml'
        //         }
        //     }
        // }

        // stage('Waiting for Backend Service to be up & running') {
        //     steps {
        //         script {
        //             kubernetesResource(
        //                 apiVersion: 'v1',
        //                 kind: 'Service',
        //                 name: 'otms-backend-service',
        //                 namespace: 'default',
        //                 waitForCondition: 'Ready',
        //                 timeout: 5
        //             )
        //         }
        //     }
        // }

        // stage('Frontend Deployment') {
        //     steps {
        //         dir('k8s-deploy') {
        //             sh 'kubectl apply -f frontend-deployment-service.yml'
        //         }
        //     }
        // }

    }
}

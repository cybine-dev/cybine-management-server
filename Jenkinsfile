pipeline {
    agent any

    tools {
        gradle 'Default'
    }

    parameters {
        string(name: 'VERSION', defaultValue: '', description: 'application version to build')
        choice(name: 'VERSION_TYPE', choices: ['final', 'snapshot', 'dev'])
        string(name: 'DOCKER_CREDENTAILS', defaultValue: 'cybine-nexus', description: 'credentials-name for nexus')
        string(name: 'DOCKER_REGISTRY', defaultValue: 'docker-registry.cybine.de:443', description: 'docker-registry url')
    }

    environment {
        IMAGE_NAME = "cybine-management-server"
        NEW_VERSION = "v${params.VERSION}-${params.VERSION_TYPE}"
        DOCKERFILE_LOCATION = '-f ./src/main/docker/Dockerfile.jvm .'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh "gradle clean build"
                }
            }
        }

        stage('Dockerize') {
            steps {
                script {
                    docker.build("${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:${env.NEW_VERSION}", env.DOCKERFILE_LOCATION)
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: params.DOCKER_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} ${env.DOCKER_REGISTRY}"
                        sh "docker push ${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:${env.NEW_VERSION}"
                    }
                }
            }
        }
    }
}
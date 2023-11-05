pipeline {
    agent any

    tools {
        gradle 'Default'
    }

    parameters {
        string(name: 'DOCKER_CREDENTIALS', defaultValue: 'cybine-nexus', description: 'credentials-name for nexus')
        string(name: 'DOCKER_REGISTRY', defaultValue: 'docker-registry.cybine.de', description: 'docker-registry url')
    }

    environment {
        IMAGE_NAME = "cybine-management-server"
        DOCKERFILE_LOCATION = '-f ./src/main/docker/Dockerfile.jvm .'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check OWASP') {
            steps {
                dependencyCheck additionalArguments: '''
                    -f 'ALL' 
                    --kevURL 'https://www.cybine.de/security/known_exploited_vulnerabilities.json'
                    --prettyPrint''', odcInstallation: 'Default'

                dependencyCheckPublisher pattern: 'dependency-check-report.xml'
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
                    docker.build("${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:build-${env.BUILD_NUMBER}", env.DOCKERFILE_LOCATION)
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                script {

                    withCredentials([usernamePassword(credentialsId: params.DOCKER_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} ${env.DOCKER_REGISTRY}"
                    }

                    def fileNames = ['build.gradle', 'build.gradle.kts']
                    def extractFileContents = fileNames.collect { "[ -e \"$it\" ] && cat \"$it\"" }.join(' || ')
                    def searchVersionLine = "grep -o 'version = [^,]*'"
                    def extractVersion = "sed -E 's/version[[:blank:]]*=[[:blank:]]*[\"'\\'']//;s/[\"'\\'']\$//;s/-snapshot//I'"

                    def version = sh(returnStdout: true, script: "$extractFileContents | $searchVersionLine | $extractVersion").trim()
                    def tags
                    switch (env.BRANCH_NAME.toLowerCase()) {
                        case 'master':
                        case 'main':
                            tags = ['latest', version]
                            break

                        case 'dev':
                        case 'development':
                            tags = ['beta', "beta-$version"]
                            break

                        default:
                            tags = ["dev-$version"]
                            break
                    }


                    def imageName = "${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}"
                    def image = docker.image("$imageName:build-${env.BUILD_NUMBER}")
                    for (def tag : tags) {
                        image.tag(tag)

                        def taggedImage = docker.image("$imageName:$tag")
                        taggedImage.push()
                    }
                }
            }
        }

        stage('Cleanup docker images') {
            steps {
                script {
                    sh "docker image prune -a -f --filter \"until=1h\""
                }
            }
        }
    }
}
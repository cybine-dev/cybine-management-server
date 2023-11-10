pipeline {
    agent any

    tools {
        gradle 'Default'
    }

    parameters {
        string(name: 'DOCKER_CREDENTIALS', defaultValue: 'cybine-nexus', description: 'credentials-name for nexus')
        string(name: 'DOCKER_REGISTRY', defaultValue: 'docker-registry.cybine.de:443', description: 'docker-registry url')
    }

    environment {
        IMAGE_NAME = "cybine-management-server"
        DOCKERFILE_NAME = 'Dockerfile.jvm'
        DOCKERFILE_LOCATION = './src/main/docker/'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('PreBuild') {
            steps {
                script {
                    echo 'Working directory'
                    sh 'pwd'

                    echo 'Docker version'
                    sh 'docker --version'

                    echo 'Hadolint version'
                    sh 'hadolint --version'

                    echo 'Trivy version'
                    sh 'trivy --version'

                    echo 'Performing security checks...'
                    echo 'Checking dependencies...'
                }

                dependencyCheck additionalArguments: '''
                    -f 'ALL' 
                    --kevURL 'https://www.cybine.de/security/known_exploited_vulnerabilities.json'
                    --prettyPrint''', odcInstallation: 'Default'

                dependencyCheckPublisher pattern: 'dependency-check-report.xml'

                script {

                    echo 'Checking dockerfile...'
                    sh "hadolint ${env.DOCKERFILE_LOCATION}${env.DOCKERFILE_NAME} | tee -a hadolint.report.txt"

                    echo 'Finished security tests'
                }
            }

            post {
                always {
                    archiveArtifacts 'hadolint.report.txt'
                }
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
                    docker.build("${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:build-${env.BUILD_NUMBER}", "-f ${env.DOCKERFILE_LOCATION}${env.DOCKERFILE_NAME} .")
                }
            }
        }

        stage('PostDockerize') {
            steps {
                script {
                    sh "trivy image --ignore-unfixed --format template --template '@/opt/templates/trivy.html.tpl' -o trivy-fixed.html ${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:build-${env.BUILD_NUMBER}"
                    sh "trivy image --format template --template '@/opt/templates/trivy.html.tpl' -o trivy-all.html ${params.DOCKER_REGISTRY}/${env.IMAGE_NAME}:build-${env.BUILD_NUMBER}"
                }

                publishHTML target : [
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: '',
                        reportFiles: 'trivy-fixed.html,trivy-all.html',
                        reportName: 'Trivy Scan',
                        reportTitles: 'Trivy fixed,Trivy all'
                ]
            }
        }

        stage('Deploy') {
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

        stage('Cleanup') {
            steps {
                script {
                    sh "docker image prune -a -f --filter \"until=1h\""
                }
            }
        }
    }
}
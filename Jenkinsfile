pipeline {
    agent any

	environment {
    registry = "piskuntech/online-cinema-microservice-springboot"
    registryCredential = 'DockerCreds'
    dockerImage =''
    AWS_DEFAULT_REGION = "eu-central-1"
  }

    triggers {
            pollSCM '* * * * *'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/YevhenPiskun/online-cinema.git'
            }
        }

        stage('Build') {
            steps {
                powershell 'mvn clean package'
            }

            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

		stage('Build image') {
			steps {
				script {
					dockerImage = docker.build registry
				}
			}
		}

		stage('Deploy Image') {
			steps{
				script {
					docker.withRegistry( '', registryCredential ) {
					dockerImage.push()
			}
        }
      }
    }

	stage('Cleaning up') {
            steps {
                powershell "docker rmi $registry"
            }
		}
    }
}
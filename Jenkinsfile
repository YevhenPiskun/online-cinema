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

//     stage('SSH Connect') {

//     	            steps {
            //         withCredentials(bindings: [sshUserPrivateKey(credentialsId: '354ebd08-ba8c-4f6d-9002-27d2150bc353', keyFileVariable: 'mykeyaws.pem')]) {
    			     //   bat "ssh ec2-user@ec2-3-71-45-70.eu-central-1.compute.amazonaws.com docker-compose up -d"
    			     //   sshScript remote: remote, script: "abc.sh"
            //             sshCommand remote: remote, command: 'docker-compose up -d'
                    // bat 'whoami'
//                     bat 'ssh -tt -i -o StrictHostKeyChecking=no "C:/ProgramData/Jenkins/.jenkins/workspace/my_pipeline/mykeyaws.pem" ec2-user@ec2-3-69-252-223.eu-central-1.compute.amazonaws.com docker -vl'
                    // bat 'docker -v'
                    // withCredentials([aws(accessKeyVariable:'AWS_ACCESS_KEY_ID', credentialsId:'aws-creds', secretKeyVariable:'AWS_SECRET_ACCESS_KEY')]) {
                    //     sshCommand remote: remote, command: 'for i in {1..5}; do echo -n \"Loop \$i \"; date ; sleep 1; done'
//                      }
//                     }
    // 			}

	stage('Cleaning up') {
            steps {
                powershell "docker rmi $registry"
            }
		}
    }
}
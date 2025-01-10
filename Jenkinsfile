pipeline {
	agent any
    environment {
		gradleVersion = 'gradle8'
        gradleHome = "${tool gradleVersion}"
    }

    stages {
		stage('Checkout') {
			steps {
				git url: 'https://github.com/NeutralPlasma/AdvancedCrops.git', branch: 'master'
            }
        }

        stage('Build') {
			steps {
				withCredentials([usernamePassword(credentialsId: 'NEXUS1', usernameVariable: 'NEXUS1_USERNAME', passwordVariable: 'NEXUS1_PASSWORD')]) {
					script {
						echo "Using Nexus username: ${env.NEXUS1_USERNAME}"
						echo "Nexus password is set: ${env.NEXUS1_PASSWORD ? 'Yes' : 'No'}"
					}
					sh 'chmod +x gradlew'
                    sh './gradlew build'
                }
            }
        }
    }

    post {
		always {
			archiveArtifacts artifacts: '**/build/libs/AdvancedCrops-*.jar', allowEmptyArchive: true
        }
    }
}

pipeline {
	def gradleVersion = 'gradle8'
	def gradleHome = tool gradleVersion

	stages {
		stage('Checkout') {
			git url: 'https://github.com/NeutralPlasma/AdvancedCrops.git', branch: 'master'
		}

		stage('Build'){

			steps {
				withCredentials([
					string(credentialsId: 'NEXUS1', usernameVariable: 'NEXUS1_USERNAME', passwordVariable: 'NEXUS1_PASSWORD'),
				]){
					sh 'chmod +x gradlew'
					sh './gradlew build'
				}
			}
		}
	}

	post {
		always {
			archiveArtifacts artifacts: '**/build/libs/AdvancedCrops-*.jar', allowEmtpyArchive: true
		}
	}

}
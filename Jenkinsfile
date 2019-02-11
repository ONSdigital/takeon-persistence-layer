def server = Artifactory.server 'art-p-01'
def buildInfo = Artifactory.newBuildInfo()

pipeline {	
	agent any
	stages {		
		stage('Build') {		
			steps {			
				sh 'mvn -B -V -U package -Dmaven.test.skip'
			}
			post {
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}
		
		// Commented out until the firewall to sonar server issue is resolved by the raised RFC
		//stage('SonarQube analysis') { 
			//steps {
				//withSonarQubeEnv('Sonar') { 
				  //sh 'mvn sonar:sonar ' + 
				  //'-f pom.xml ' +
				  //'-Dsonar.projectKey=uk.gov.ons.collection:TakeOn:PersistenceLayer' +
				  //'-Dsonar.login=$username ' +
				  //'-Dsonar.password=$password ' +
				  //'-Dsonar.language=java ' +
				  //'-Dsonar.sources=. ' +
				  //'-Dsonar.tests=. ' +
				  //'-Dsonar.test.inclusions=**/*test*/** ' +
				  //'-Dsonar.exclusions=**/*test*/**'
				//}
			//}
		//}
	
		stage('Validate') {
			steps {			
				sh 'mvn -B -V -U test'
			}
		  post {
				always {
					script {
						def testResults = findFiles(glob: '**/target/surefire-reports/TEST-*.xml')
						for(xml in testResults) {
							touch xml.getPath()
						}
					}
				
					junit(allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml')
				}
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}
		
		stage('Publish') {
			when { anyOf {
						branch 'master';
						branch 'dev'
					}
			}
			steps {			
				script {                  
					def uploadSpec = """{
					"files": [{
					   "pattern": "target/*.jar",
					   "target": "TakeOn-MVN-Release/${buildInfo.name}/${buildInfo.number}/",
					   "props": "type=jar;status=ready"
					}]
					}"""

					server.upload spec: uploadSpec, buildInfo: buildInfo
				}
			}
			post {
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}
	}
	post {
        failure {
            echo 'Emailing committer!!!'
            emailext (
                to: 'nidal.albeiruti@ons.gov.uk',
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
            )
        }
    }
}
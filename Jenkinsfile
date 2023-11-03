pipeline {

    agent { label 'maven' }


    stages {
        stage ('GIT') {
            steps {
               echo "Getting Project from Git"; 
                git branch: "neyrouz/contrat", url: "https://github.com/Achraf66/5SAE2-G6-Khadem.git";
            }
        }
        stage("Build") {
            steps {
                sh "mvn clean package";
            }
        }
        
        stage("Sonar") {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login="admin" -Dsonar.password="esprit123"'
            }
        }
        
        stage("Build artifact") {
            steps {
                sh "sudo docker build -t contrat .";
            }
        }
        
        stage("docker compose up") {
            steps {
                sh "sudo docker compose up -d";
            }
        }
        stage('Deployment') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage("docker compose down") {
            steps {
                sh "sudo docker compose down";
            }
        }
    }
   
    post {
        always {
            cleanWs()
        }
    }
    
}
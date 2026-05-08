pipeline {
  agent any

  environment {
    APP_DIR = "${WORKSPACE}"
    APP_PORT = "9000"
    APP_USER = "ubuntu"
    SERVICE_NAME = "voltedge"
    RUN_SELENIUM = "false"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/salmankhan-1106/website-test-cases.git']]])
      }
    }

    stage('Setup App') {
      steps {
        sh 'chmod +x scripts/ec2_setup.sh scripts/ec2_service.sh'
        sh 'APP_DIR=${APP_DIR} APP_PORT=${APP_PORT} APP_USER=${APP_USER} SERVICE_NAME=${SERVICE_NAME} scripts/ec2_setup.sh'
      }
    }

    stage('Run Tests (Docker)') {
      when {
        expression { return env.RUN_SELENIUM == 'true' }
      }
      steps {
        script {
          docker.image('markhobson/maven-chrome:latest').inside('--network host') {
            sh 'cd selenium-tests && mvn test -DbaseUrl=http://localhost:${APP_PORT}/'
          }
        }
      }
    }

  }

  post {
    always {
      script {
        def authorEmail = sh(script: "git log -1 --pretty=%ae", returnStdout: true).trim()
        emailext(
          to: authorEmail,
          subject: "Jenkins Build ${env.JOB_NAME} #${env.BUILD_NUMBER}: ${currentBuild.currentResult}",
          body: "Build status: ${currentBuild.currentResult}\n\nJob: ${env.JOB_NAME}\nBuild: #${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}\n"
        )
      }
    }
  }
}

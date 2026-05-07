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

    stage('Run Tests (optional)') {
      when {
        expression { return env.RUN_SELENIUM == 'true' }
      }
      steps {
        sh 'cd selenium-tests && mvn test -DbaseUrl=http://localhost:${APP_PORT}/'
      }
    }

    stage('Deploy Service') {
      steps {
        sh 'APP_DIR=${APP_DIR} APP_PORT=${APP_PORT} APP_USER=${APP_USER} SERVICE_NAME=${SERVICE_NAME} scripts/ec2_service.sh'
      }
    }
  }
}

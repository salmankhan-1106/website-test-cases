# VoltEdge Landing Page

A simple FastAPI + HTML/CSS landing page with Selenium (Maven) tests.

## Run locally

1. Create and activate a virtual environment.
2. Install dependencies:

```bash
pip install -r requirements.txt
```

3. Start the server:

```bash
uvicorn app.main:app --host 0.0.0.0 --port 9000
```

Visit http://localhost:9000

## Selenium tests (Maven)

From the selenium-tests folder:

```bash
mvn test -DbaseUrl=http://localhost:9000/
```

Notes:
- Requires Google Chrome installed.
- You can disable headless mode with `-Dheadless=false`.

## EC2 deployment (git-based)

1. Push code to a Git repo (GitHub, GitLab, etc.).
2. SSH into your EC2 instance.
3. Install dependencies:

```bash
sudo apt update
sudo apt install -y git python3 python3-venv
```

4. Clone your repo and set up the app:

```bash
git clone <YOUR_REPO_URL>
cd <REPO_FOLDER>
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

5. Run the app (test first):

```bash
uvicorn app.main:app --host 0.0.0.0 --port 9000
```

6. Open port 8000 in your EC2 security group to access it via:

```
http://<EC2_PUBLIC_IP>:9000
```

### Optional: systemd service

Create a service file:

```bash
sudo nano /etc/systemd/system/voltedge.service
```

Paste:

```
[Unit]
Description=VoltEdge FastAPI service
After=network.target

[Service]
User=ubuntu
WorkingDirectory=/home/ubuntu/<REPO_FOLDER>
ExecStart=/home/ubuntu/website-test-cases/.venv/bin/uvicorn app.main:app --host 0.0.0.0 --port 9000
Restart=always

[Install]
WantedBy=multi-user.target
```

Enable and start:

```bash
sudo systemctl daemon-reload
sudo systemctl enable voltedge
sudo systemctl start voltedge
sudo systemctl status voltedge

## EC2 automation scripts

On EC2, from the repo root:

```bash
chmod +x scripts/ec2_setup.sh scripts/ec2_service.sh
APP_DIR=/home/ubuntu/website-test-cases APP_PORT=9000 APP_USER=ubuntu SERVICE_NAME=voltedge scripts/ec2_setup.sh
APP_DIR=/home/ubuntu/website-test-cases APP_PORT=9000 APP_USER=ubuntu SERVICE_NAME=voltedge scripts/ec2_service.sh
```

## Jenkins (running on the same EC2 instance)

This repo includes a Jenkinsfile that:
- sets up the venv and installs deps
- optionally runs Selenium tests in Docker (set RUN_SELENIUM=true)

If you do not allow passwordless sudo for Jenkins, install/start the service manually once:

```bash
APP_DIR=/home/ubuntu/website-test-cases APP_PORT=9000 APP_USER=ubuntu SERVICE_NAME=voltedge scripts/ec2_service.sh
```

You can set Jenkins pipeline env vars:
- APP_DIR (default /home/ubuntu/website-test-cases)
- APP_PORT (default 9000)
- APP_USER (default ubuntu)
- SERVICE_NAME (default voltedge)
- RUN_SELENIUM (default false)

Docker-based tests use the image `markhobson/maven-chrome` and run with `--network host`,
so the app must already be running on the host at the configured `APP_PORT`.
```

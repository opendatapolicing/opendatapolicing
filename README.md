# Setup opendatapolicing development environment on MacOSX or Linux (Fedora, RHEL, CentOS)

## Install dependencies on Linux

```bash
sudo yum install -y git python3 python3-pip libselinux-python3 maven
```

## Install dependencies on MacOSX

For MacOS, install gnu-tar and add it to the system PATH 
https://stackoverflow.com/questions/54528115/unable-to-extract-tar-file-though-ansible-unarchive-module-in-macos

```bash
brew install git python maven gnu-tar
```

# Setup Ansible

## Install python3 dependencies for Ansible

```bash
sudo pip3 install psycopg2-binary ansible openshift jmespath
```

## Setup the directory for the project and clone the git repository into it 

```bash
sudo install -d -o $USER -g $USER /usr/local/src/opendatapolicing
git clone git@github.com:opendatapolicing/opendatapolicing.git /usr/local/src/opendatapolicing
```

## Setup the environment using the requirements.yml file

```bash
ansible-galaxy install -r /usr/local/src/opendatapolicing/ansible/roles/requirements.yml
```

## Install the 4 required roles using the main ansible playbook

```bash
cd /usr/local/src/opendatapolicing && ansible-playbook opendatapolicing_install_project.yml -K
 ```


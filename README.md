
### Build the container with podman

```bash
cd ~/.local/src/TLC
podman build -t computateorg/opendatapolicing:latest .
```

### Push the container up to quay.io
```bash
podman login quay.io
podman push computateorg/opendatapolicing:latest quay.io/computateorg/opendatapolicing:latest
```

### Run the container for local development

```bash
podman run --rm -it --entrypoint /bin/bash computateorg/opendatapolicing:latest
java $JAVA_OPTS -cp .:* com.opendatapolicing.enus.vertx.MainVerticle
```

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

# OpenShift Deployment
**Create OpenShift Hosts file**
```
echo "

localhost

[redhat_sso_openshift]
localhost

[postgres_openshift]
localhost

[project_zookeeper_openshift]
localhost

[project_solr_openshift]
localhost

[project_certbot]
localhost

[project_openshift_enUS]
localhost

[project_login]
localhost

[project_refresh]
localhost

[project_backup]
localhost

[project_restore]
localhost
" > /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts
```

**Configure vault file**  
*see [opendatapolicing-ansible](https://github.com/opendatapolicing/opendatapolicing-ansible/tree/main/vaults) for sample*
```
install -d /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/host_vars/localhost/
ansible-vault create /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/host_vars/localhost/vault
ansible-vault edit /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/host_vars/localhost/vault
```

**Create OpenShift Token**
```
export VAULT_ID=opendatapolicing-hackathon/host_vars/localhost
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_token_setup.yml --ask-vault-pass -e "ocp_cluster_user_user=username" -e "ocp_cluster_user_password=changeme" 
```

**Set Environment Variables**

**ODP_ANSIBLE_INVENTORY** Defines location of git repo holding inventories and vaults. Default /usr/local/src/opendatapolicing-ansible
```bash
export ODP_ANSIBLE_INVENTORY=/usr/local/src/opendatapolicing-ansible
```

**ODP_ANSIBLE_STATIC** Defines location of git repo holding inventories and vaults. Default /usr/local/src/opendatapolicing-static
```bash
export ODP_ANSIBLE_INVENTORY=/usr/local/src/opendatapolicing-static
```

**Update vault file with OpenShift Token**
```
ansible-vault edit /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/host_vars/localhost/vault
```


## For End-to-End deployment
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_openshift_complete.yml --ask-vault-pass
```


## To step thru deployment
**Run redhat_sso_openshift.yml**
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/redhat_sso_openshift.yml --ask-vault-pass
```

**Run project_postgres_openshift.yml**
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_postgres_openshift.yml --ask-vault-pass
```

**Run project_zookeeper_openshift.yml**
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_zookeeper_openshift.yml --ask-vault-pass
```

**Run project_solr_openshift.yml**
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_solr_openshift.yml --ask-vault-pass
```

**Run project_openshift.yml**
```
ansible-playbook  -i /usr/local/src/opendatapolicing-ansible/inventories/opendatapolicing-hackathon/hosts  /usr/local/src/opendatapolicing/ansible/project_openshift.yml --ask-vault-pass
```




# Import Data

sed -i 's/\x0//g' ~/backup/opendatapolicing/Stop.txt
dos2unix ~/backup/opendatapolicing/Stop.txt
cut -d $'\t' -f 1-15 Stop.txt > Stop2.txt

psql opendatapolicing -c "copy TrafficStop(inheritPk, agencyTitle, stopDateTime, stopPurposeNum, stopActionNum, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId) from '/home/ctate/backup/opendatapolicing/Stop2.txt' (delimiter E'\t', format csv, null '');"
psql opendatapolicing -c "update TrafficStop set stateAbbreviation='NC', stateName='North Carolina';"
psql opendatapolicing -c "copy TrafficPerson(inheritPk, stopId, personTypeId, personAge, personGenderId, personEthnicityId, personRaceId) from '/home/ctate/backup/opendatapolicing/PERSON.txt' (delimiter E'\t', format csv, null '');"
psql opendatapolicing -c 'copy TrafficSearch(inheritPk, personId, searchTypeNum, searchVehicle, searchDriver, searchPassenger, searchProperty, searchVehicleSiezed, searchPersonalPropertySiezed, searchOtherPropertySiezed) from program '"'"'cut -f 1,2,4,5,6,7,8,9,10,11 ~/backup/opendatapolicing/Search.txt'"'"' (delimiter E'"'"'\t'"'"', format csv, null '"'"''"'"');'
psql opendatapolicing -c 'copy SearchBasis(inheritPk, searchId, searchBasisId) from program '"'"'cut -f 1,2,5 ~/backup/opendatapolicing/SearchBasis.txt'"'"' (delimiter E'"'"'\t'"'"', format csv, null '"'"''"'"');'
psql opendatapolicing -c 'copy TrafficContraband(inheritPk, searchId, contrabandOunces, contrabandPounds, contrabandPints, contrabandGallons, contrabandDosages, contrabandGrams, contrabandKilos, contrabandMoney, contrabandWeapons, contrabandDollarAmount) from program '"'"'cut -f 1,2,5,6,7,8,9,10,11,12,13,14 ~/backup/opendatapolicing/Contraband.txt'"'"' (delimiter E'"'"'\t'"'"', format csv, null '"'"''"'"');'

# Restore Solr Index

http://localhost:8983/solr/opendatapolicing/replication?command=restore&name=shard1&async=request3&location=/run/media/ctate/D8A5-1D9A/solr/opendatapolicing

Check status of restore operation: 
http://localhost:8983/solr/opendatapolicing/replication?command=restorestatus

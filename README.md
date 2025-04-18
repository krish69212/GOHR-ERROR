# GOHR ML Experiments (Simple Version)

This repository contains the updated implementation of RL Agents for learning rule-based games by Pulick et al. The original paper is at https://ieeexplore.ieee.org/document/10510453

## Setup and Installation

#  GETTING STARTED WITH WINDOWS OS 


### create a WSL

1) Install WSL and Virtual Machine Platform:
- Open Windows Features again.

- Check Windows Subsystem for Linux and Virtual Machine Platform.

- Click OK and restart your computer.

2) Install a Linux Distribution:
- Open Microsoft Store.

- Search for your preferred Linux distribution (e.g., Ubuntu, Debian).

- Click Install.


3) select the wsl terminal

### setting environment in wsl2

## get the latest changes

````bash
git pull origin main   

sudo apt-get update
sudo apt-get upgrade -y

sudo apt-get install -y wget curl bzip2 ca-certificates

wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh -O ~/miniconda.sh

bash ~/miniconda.sh -b -p $HOME/miniconda
eval "$($HOME/miniconda/bin/conda shell.bash hook)"

echo 'eval "$($HOME/miniconda/bin/conda shell.bash hook)"' >> ~/.bashrc
source ~/.bashrc
````
## Install Java - add this line
````bash
sudo apt-get install -y openjdk-11-jdk

cd GOHR-ERROR/
conda env create -f environment_simple.yml
pip install torch

````
############Running the code 
````bash
chmod +x dqn1.sh 

./dqn1.sh
````
